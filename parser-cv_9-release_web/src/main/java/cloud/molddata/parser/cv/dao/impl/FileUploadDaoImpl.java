package cloud.molddata.parser.cv.dao.impl;


import cloud.molddata.parser.cv.dao.FileUploadDao;
import cloud.molddata.parser.cv.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @SuppressWarnings("unchecked")
    @Transactional
    public List<UploadedFile> getListFiles() {
        return getEntityManager().createQuery("Select uf from UploadedFile uf").getResultList();
    }


    @Transactional
    public UploadedFile getFile(Long id) {
        return getEntityManager().find(UploadedFile.class, id);
    }

    @Transactional
    public UploadedFile saveFile(UploadedFile uploadedFile) {
        return getEntityManager().merge(uploadedFile);
    }

}
