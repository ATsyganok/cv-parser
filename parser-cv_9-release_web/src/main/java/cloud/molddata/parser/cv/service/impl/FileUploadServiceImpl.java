package cloud.molddata.parser.cv.service.impl;

import cloud.molddata.parser.cv.dao.FileUploadDao;
import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService {

  //private SessionFactory sessionFactory;

  @Autowired
  private FileUploadDao dao;

 /* public FileUploadServiceImpl(){}
  public FileUploadServiceImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;

  }*/


  @Override
  @Transactional(readOnly = true)
  public List<UploadedFile> listFiles(){

    return dao.listFiles();
  }
    @Override
    @Transactional(readOnly = true)
    public List<Users> listUsers(String nameAuth, String sessionID) {

        return dao.listUsers(nameAuth, sessionID);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> listUsersAll() {

        return dao.listUsersAll();
    }
    @Override
        @Transactional(readOnly = true)
        public List<UserSecurity> listUsersAuth() {

            return dao.listUsersAuth();
        }

  @Override
  @Transactional(readOnly = true)
  public UploadedFile getFile(Long id) {
    return dao.getFile(id);
  }

  @Override
  @Transactional
  public UploadedFile saveFile(UploadedFile uploadedFile) {
    return dao.saveFile(uploadedFile);

  }

    @Override
    @Transactional
    public void createUser(String sessionID) {
        dao.createUser(sessionID);
    }

    @Override
    @Transactional
    public void saveParsedCV(List<UploadedFile> activeFilesInSession){
        dao.saveParsedCV(activeFilesInSession);
    }
    @Override
    @Transactional
    public Contact saveParsedCVes(List<UploadedFile> activeFilesInSession){
        return dao.saveParsedCVes(activeFilesInSession);
    }

    @Override
    @Transactional
    public String parseStatus(UploadedFile activeFileInSession){
        return dao.parseStatus(activeFileInSession);
    }

    @Override
    @Transactional
    public String getContactForThis(String id_cont){
        return dao.getContactForThis(id_cont);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CV> listCVes() {

        return dao.listCVes();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact contInfo(String id_cont) {

        return dao.contInfo(id_cont);
    }

}
