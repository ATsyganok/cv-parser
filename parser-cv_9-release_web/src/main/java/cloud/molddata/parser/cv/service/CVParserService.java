package cloud.molddata.parser.cv.service;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface CVParserService {

  void saveListParsedCV(List<UploadedFile> activeFilesInSession);

  List<CV> getListCV();

  Contact getContactInfo(String id_cont);

  String getParseStatus(UploadedFile activeFileInSession);
}
