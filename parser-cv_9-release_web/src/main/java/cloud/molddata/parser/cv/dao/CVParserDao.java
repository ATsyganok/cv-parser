package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface CVParserDao {

  static final int i=0;

  void saveParsedCV(List<UploadedFile> activeFilesInSession);

  List<CV> getListCV();

  Contact getContactInfo(String id_cont);

  String getParseStatus(UploadedFile activeFileInSession);

}
