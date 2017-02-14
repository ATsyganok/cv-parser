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
  public List<UploadedFile> getListFiles(){

    return dao.getListFiles();
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


}
