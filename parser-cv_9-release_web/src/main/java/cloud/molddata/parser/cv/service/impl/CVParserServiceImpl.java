package cloud.molddata.parser.cv.service.impl;

import cloud.molddata.parser.cv.dao.CVParserDao;
import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.service.CVParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CVParserServiceImpl implements CVParserService {

    @Autowired
    private CVParserDao dao;

    @Override
    @Transactional
    public void saveListParsedCV(List<UploadedFile> activeFilesInSession) {
        dao.saveParsedCV(activeFilesInSession);
    }


    @Override
    @Transactional
    public String getParseStatus(UploadedFile activeFileInSession) {
        return dao.getParseStatus(activeFileInSession);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CV> getListCV() {
        return dao.getListCV();
    }

    @Override
    @Transactional(readOnly = true)
    public Contact getContactInfo(String id_cont) {
        return dao.getContactInfo(id_cont);
    }

}
