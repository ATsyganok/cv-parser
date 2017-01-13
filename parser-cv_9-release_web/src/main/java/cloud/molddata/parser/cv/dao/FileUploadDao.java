package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface FileUploadDao {

  List<UploadedFile> listFiles();

  List<Users> listUsers(String nameAuth, String sessionID);

  List<Users> listUsersAll();

  List<UserSecurity> listUsersAuth();

  UploadedFile getFile(Long id);

  UploadedFile saveFile(UploadedFile uploadedFile);

  Contact saveParsedCVes(List<UploadedFile> activeFilesInSession);

  void createUser(String sessionID);

  void saveParsedCV(List<UploadedFile> activeFilesInSession);

  String getContactForThis(String id_cont);

  Contact contInfo(String id_cont);

  List<CV> listCVes();

  String parseStatus(UploadedFile activeFileInSession);

}
