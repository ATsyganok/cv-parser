package cloud.molddata.parser.cv.controller;

import cloud.molddata.parser.cv.model.Contact;
import cloud.molddata.parser.cv.model.UploadedFile;
import cloud.molddata.parser.cv.service.FileUploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController {

  private static List<UploadedFile> actFiles;

  private static List<UploadedFile> activeFilesInSession;

  public static List<UploadedFile> getActiveFilesInSession() {
    return activeFilesInSession;
  }

  public static void setActiveFilesInSession(List<UploadedFile> activeFilesInSession) {
    FileUploadController.activeFilesInSession = activeFilesInSession;
  }

  @Autowired
  private FileUploadService uploadService;

  @RequestMapping(value = {"/fileUploader"})
  public String fileUploader(HttpServletRequest request) {
    //System.out.println("/fileUploader being called by");
    String sessionID = request.getSession().getId();
    uploadService.createUser(sessionID);
    //System.out.println("/fileUploader sesID from ="+sessionID);
    return "/fileUploader";
  }

  @RequestMapping(value = "/uploaded", method = RequestMethod.POST) //value = "/upload",
  public @ResponseBody List<UploadedFile> upload(MultipartHttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String sessionID = request.getSession().getId();

    // Getting uploaded files from the request object
    Map<String, MultipartFile> fileMap = request.getFileMap();

    // Maintain a list to send back the files info. to the client side
    List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

    // Iterate through the map
    for (MultipartFile multipartFile : fileMap.values()) {
      if (confirmType((getTypeFile(multipartFile)))) {
        // Save the file to local disk
        saveFileToLocalDisk(multipartFile);

        UploadedFile fileInfo = getUploadedFileInfo(multipartFile,true,sessionID);
        //activeFilesInSession.add(fileInfo);

        // Save the file info to database
        fileInfo = saveFileToDatabase(fileInfo); //load fileINFO to DB!!!!!!!!!!!!

        // adding the file info to the list
        uploadedFiles.add(fileInfo);
      }else
        uploadedFiles.add(getUploadedFileInfo(multipartFile,false,sessionID));
    }
    setActiveFilesInSession(getUploadedFiles(uploadedFiles));
    return uploadedFiles;
  }

  @RequestMapping(value = "/parse", method = RequestMethod.POST) //value = "/upload",
  public @ResponseBody String parse(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
    String filesParsed = "";
    //System.out.println("PARSED "+filesParsed);
    //response.setHeader("HeadSessionID", request.getSession().getId());
    for (int i =0; i<getActiveFilesInSession().size();i++){
      filesParsed = filesParsed + "   - "+getActiveFilesInSession().get(i).getName()+
              uploadService.parseStatus(getActiveFilesInSession().get(i))+"\n";
    }
    //System.out.println("size of CV: " + getActiveFilesInSession().size());
    System.out.println("SEND command to SAVE CV");
    //saveCVToDatabase(getActiveFilesInSession()); //load CV to DB!!!!!!!!!!!!
    uploadService.saveParsedCV(getActiveFilesInSession());
    //-------------- clean List<UploadedFile> activeFilesInSession
    setActiveFilesInSession(new ArrayList<UploadedFile>());
    //----------------------------------------------------
    System.out.println("RECIEVE command to SAVE CV");
    return filesParsed;
  }


  @RequestMapping(value = "/contact", method = RequestMethod.POST)
  public @ResponseBody String contact(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Map<String, Object> map) throws IOException {
    String ContactID = request.getParameter(("contID")).toString();
    Contact cont = uploadService.contInfo(ContactID);
    String nameCont = cont.getPhone(); //+"   ID="+cont.getId()
    response.setHeader("HeadSessionFullName", cont.getFullName());
    response.setHeader("HeadSessionRegion", cont.getLocation());
    response.setHeader("HeadSessionEmail", cont.getEmail());
    return nameCont;
  }


  @RequestMapping(value = {"/list"})
  public String listBooks(Map<String, Object> map, HttpServletRequest request) {
    String sessionID = request.getSession().getId();

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String nameAuth = auth.getName();

    map.put("fileList", uploadService.listFiles());
    map.put("userList", uploadService.listUsers(nameAuth, sessionID));
    return "/listFiles";
  }



  @RequestMapping(value = {"/analyze"})
  public String listCVes(Map<String, Object> map, HttpServletRequest request) {
    String sessionID = request.getSession().getId();
    Authentication nameAuth = SecurityContextHolder.getContext().getAuthentication();
    map.put("cvList", uploadService.listCVes());
    map.put("userList", uploadService.listUsers(nameAuth.getName(), sessionID));
    return "/listCVes";
  }



  @RequestMapping(value = "/get/{fileId}", method = RequestMethod.GET)
  public void getFile(HttpServletResponse response, @PathVariable Long fileId) {

    UploadedFile dataFile = uploadService.getFile(fileId);

    File file = new File(dataFile.getLocation(), dataFile.getName());

    try {
      response.setContentType(dataFile.getType());
      response.setHeader("Content-disposition", "attachment; filename=\"" + dataFile.getName()
          + "\"");

      FileCopyUtils.copy(FileUtils.readFileToByteArray(file), response.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String getTypeFile(MultipartFile multipartFile){
    String fileNameSplit[]=multipartFile.getOriginalFilename().split("\\.");
    return fileNameSplit[fileNameSplit.length-1];
  }

  private Boolean confirmType(String typeFile){
    String[] types=new String[]{"doc","pdf"};
    for(String type:types){
      if(type.equals(typeFile)) return true;
    }
    return false;
  }

  private void saveFileToLocalDisk(MultipartFile multipartFile) throws IOException,
      FileNotFoundException {
    String outputFileName = getOutputFilename(multipartFile);
    FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(outputFileName));
  }

  private UploadedFile saveFileToDatabase(UploadedFile uploadedFile) {
    return uploadService.saveFile(uploadedFile);
  }

  private Contact saveCVToDatabase(List<UploadedFile> uploadedFileINSession) {
    return uploadService.saveParsedCVes(uploadedFileINSession);
  }

  private String getOutputFilename(MultipartFile multipartFile) {
    return getDestinationLocation() + multipartFile.getOriginalFilename();
  }

  private UploadedFile getUploadedFileInfo(MultipartFile multipartFile,boolean status,String sessionID) throws IOException {
    UploadedFile fileInfo = new UploadedFile();
    fileInfo.setName(multipartFile.getOriginalFilename());
    fileInfo.setSize(multipartFile.getSize());
    fileInfo.setType(multipartFile.getContentType());
    fileInfo.setLocation(getDestinationLocation());
    fileInfo.setSessionID(sessionID);
    fileInfo.setDate(new Date());
    fileInfo.setStatus(status?"uploaded":"not loaded");
    return fileInfo;
  }

  public String getDestinationLocation() {
    return "/opt/tomcat/temp/uploaded-files/";
  }

  //show info in dialog after btn-parsed
  private static List<UploadedFile> getUploadedFiles(List<UploadedFile> uploadedFileList){
    List<UploadedFile> finalList = new ArrayList<>();
    for (UploadedFile file:uploadedFileList){
      if (file.getStatus().equals("uploaded"))
        finalList.add(file);
    }
    if (finalList.size()==0) {
      UploadedFile emptyFile = new UploadedFile();
      emptyFile.setName("NOUN");
      finalList.add(emptyFile);
    }
    return finalList;
  }
 }
