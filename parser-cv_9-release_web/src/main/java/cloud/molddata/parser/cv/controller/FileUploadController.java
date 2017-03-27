package cloud.molddata.parser.cv.controller;

import cloud.molddata.parser.cv.model.Contact;
import cloud.molddata.parser.cv.model.UploadedFile;
import cloud.molddata.parser.cv.service.CVParserService;
import cloud.molddata.parser.cv.service.FileUploadService;
import cloud.molddata.parser.cv.parser.Paths;

import cloud.molddata.parser.cv.service.UserUploadService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
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
  private static String path=Paths.P_FOLDER_UPLOADES;

  private static List<UploadedFile> activeFilesInSession;

  @PostConstruct
  void initActiveFileSession(){
    List<UploadedFile> activeFileinSession = new ArrayList<>();
  }

  public void setActiveFilesInSession(List<UploadedFile> activeFilesInSession) {
    this.activeFilesInSession = activeFilesInSession;
  }
  public static List<UploadedFile> getActiveFilesInSession() {
    return activeFilesInSession;
  }



  @Autowired
  private FileUploadService fileUploadService;
  @Autowired
  private UserUploadService userUploadService;
  @Autowired
  private CVParserService cvParserService;
  @RequestMapping(value = {"/fileUploader"})
  public String fileUploader(HttpServletRequest request) {
    String sessionID = request.getSession().getId();
    userUploadService.createAnonymousUser(sessionID);
    return "/fileUploader";
  }

  @RequestMapping(value = "/uploaded", method = RequestMethod.POST)
  public @ResponseBody List<UploadedFile> upload(MultipartHttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String sessionID = request.getSession().getId();
    Map<String, MultipartFile> fileMap = request.getFileMap();
    List<UploadedFile> uploadedFiles = new ArrayList<UploadedFile>();

    for (MultipartFile multipartFile : fileMap.values()) {
      if (confirmType((getTypeFile(multipartFile)))) {
        saveFileToLocalDisk(multipartFile);
        UploadedFile fileInfo = getUploadedFileInfo(multipartFile,true,sessionID);
        fileInfo = saveFileToDatabase(fileInfo);
        uploadedFiles.add(fileInfo);
      }else
        uploadedFiles.add(getUploadedFileInfo(multipartFile,false,sessionID));
    }
    setActiveFilesInSession(getUploadedFiles(uploadedFiles));
    return uploadedFiles;
  }

  @RequestMapping(value = "/parse", method = RequestMethod.POST) 
  public @ResponseBody String parse() throws IOException {
    String filesParsed = "";
    for (int i =0; i<getActiveFilesInSession().size();i++){
      filesParsed = filesParsed + "   - "+getActiveFilesInSession().get(i).getName()+
              cvParserService.getParseStatus(getActiveFilesInSession().get(i))+"\n";
    }
    cvParserService.saveListParsedCV(getActiveFilesInSession());
    setActiveFilesInSession(new ArrayList<UploadedFile>());

    return filesParsed;
  }


  @RequestMapping(value = "/contact", method = RequestMethod.POST)
  public @ResponseBody String contact(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Map<String, Object> map) throws IOException {
    String ContactID = request.getParameter(("contID")).toString();
    Contact cont = cvParserService.getContactInfo(ContactID);
    String nameCont = cont.getPhone();
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
    map.put("fileList", fileUploadService.getListFiles());
    map.put("userList", userUploadService.getListUsersByName(nameAuth, sessionID));

    return "/listFiles";
  }



  @RequestMapping(value = {"/analyze"})
  public String listCVes(Map<String, Object> map, HttpServletRequest request) {
    String sessionID = request.getSession().getId();
    Authentication nameAuth = SecurityContextHolder.getContext().getAuthentication();
    map.put("cvList", cvParserService.getListCV());
    map.put("userList", userUploadService.getListUsersByName(nameAuth.getName(), sessionID));

    return "/listCVes";
  }



  @RequestMapping(value = "/get/{fileId}", method = RequestMethod.GET)
  public void getFile(HttpServletResponse response, @PathVariable Long fileId) {
    UploadedFile dataFile = fileUploadService.getFile(fileId);
    File file = new File(dataFile.getLocation(), dataFile.getName());
    try {
      response.setContentType(dataFile.getType());
      response.setHeader("Content-disposition", "attachment; filename=\"" + dataFile.getName()         + "\"");
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

  private void saveFileToLocalDisk(MultipartFile multipartFile) throws IOException {
    String outputFileName = getOutputFilename(multipartFile);
    FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(outputFileName));
  }

  private UploadedFile saveFileToDatabase(UploadedFile uploadedFile) {
    return fileUploadService.saveFile(uploadedFile);
  }

  private String getOutputFilename(MultipartFile multipartFile) {
    return path + multipartFile.getOriginalFilename();
  }

  private UploadedFile getUploadedFileInfo(MultipartFile multipartFile,boolean status,String sessionID) throws IOException {
    UploadedFile fileInfo = new UploadedFile();
    fileInfo.setName(multipartFile.getOriginalFilename());
    fileInfo.setSize(multipartFile.getSize());
    fileInfo.setType(multipartFile.getContentType());
    fileInfo.setLocation(path);
    fileInfo.setSessionID(sessionID);
    fileInfo.setDate(new Date());
    fileInfo.setStatus(status?"uploaded":"not loaded");
    return fileInfo;
  }

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
