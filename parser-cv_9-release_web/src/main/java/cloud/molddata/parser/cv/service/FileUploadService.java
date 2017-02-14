package cloud.molddata.parser.cv.service;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface FileUploadService {

  List<UploadedFile> getListFiles();

  UploadedFile getFile(Long id);

  UploadedFile saveFile(UploadedFile uploadedFile);

}
