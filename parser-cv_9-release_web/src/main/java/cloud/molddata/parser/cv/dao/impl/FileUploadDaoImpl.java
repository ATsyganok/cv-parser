package cloud.molddata.parser.cv.dao.impl;


import java.util.List;

import cloud.molddata.parser.cv.parser.SearchBoxParser;
import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.dao.FileUploadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class FileUploadDaoImpl implements FileUploadDao {

  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  public EntityManager getEntityManager(){
    return entityManager;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }


  @SuppressWarnings("unchecked")
  @Transactional
  public List<UploadedFile> listFiles() {
    Query query = getEntityManager().createQuery("Select uf from UploadedFile uf");
    List<UploadedFile> resList = query.getResultList();

    return resList;
  }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Users> listUsers(String nameAuth, String sessionID) {
        List<Users> resList = null;
        if (("000").equals(sessionID)&&("anonymousUser").equals(nameAuth)){
            Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth","anonymousUser");
            resList = query.getResultList();
            return resList;
        }
        if (("anonymousUser").equals(nameAuth)) {
            Query querySessionID = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
            resList = querySessionID.getResultList();
            return resList;
        }
        Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth",nameAuth);
        resList = query.getResultList();
        return resList;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Users> listUsersAll() {
        Query queryUsersAll = getEntityManager().createNamedQuery("Users.findAll");
        List<Users> resList = queryUsersAll.getResultList();
        return resList;
    }
    @SuppressWarnings("unchecked")
        @Transactional
        public List<UserSecurity> listUsersAuth() {
            Query queryUsersAll = getEntityManager().createNamedQuery("UserSecurity.findAll");
            List<UserSecurity> resList = queryUsersAll.getResultList();
            return resList;
        }

    @Transactional
    public void saveParsedCV(List<UploadedFile> ActiveFilesInSession){
        loadParsedCV(ActiveFilesInSession);
    }

  @Transactional
  public UploadedFile getFile(Long id) {
    UploadedFile uploadedFile = getEntityManager().find(UploadedFile.class, id);
    return uploadedFile;
  }

  @Transactional
  public UploadedFile saveFile(UploadedFile uploadedFile) {
    return getEntityManager().merge(uploadedFile);
  }


    @Transactional
    public void createUser(String sessionID){
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
        List<Users> usersList =  usersBySession.getResultList();
        if (usersList.size() == 0){
            Users users = new Users(sessionID, "anonymousUser");
            getEntityManager().persist(users);
            return;
        }
        System.out.println("User DIDN'T create! BEACUSE! user is exist");
    }

    @Transactional
    private Long getIDbySession(String sessionID){
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);
        Users users = (Users) usersBySession.getSingleResult();
        Long idUsersbySess = (Long)users.getId();
        return idUsersbySess;
    }

    @Transactional
    private void loadParsedCV(List<UploadedFile> activeFilesInSession){
        String sessionID = activeFilesInSession.get(0).getSessionID();

        SearchBoxParser.CVparser(activeFilesInSession); //ready CV in CLASS
        List<CV> cvList = SearchBoxParser.getcvList();

        for (CV cv:cvList){
            Contact contact = cv.getContact();

            cv.setContact(contact);
            contact.setCv(cv);

            getEntityManager().persist(contact);
            getEntityManager().persist(cv);

            Long tempID = getIDbySession(sessionID);
            addCV(tempID,cv);
        }
    }

    @Transactional
    private void addCV(Long idUSER, CV cv) {
        Users user = getEntityManager().find(Users.class,idUSER);
        user.addCV(cv);
        getEntityManager().persist(user);
    }

    @Transactional
    public Contact saveParsedCVes(List<UploadedFile> activeFilesInSession){
        String sessionID = activeFilesInSession.get(0).getSessionID();

        SearchBoxParser.CVparser(activeFilesInSession); //ready CV in CLASS
        List<Contact> contactList = SearchBoxParser.getContactList();

        Contact contact1 = new Contact();

        for (Contact contact:contactList){
            Contact contact2 = new Contact(contact.getFullName(),contact.getPhone(),contact.getLocation());

            CV cv = contact.getcv();//cv def in method create

            cv.setContact(contact2);
            contact2.setCv(cv);

            getEntityManager().persist(contact2);
            getEntityManager().persist(cv);//cv def in method create

            Long tempID = getIDbySession(sessionID);
            addCV(tempID,cv);
            contact1 = contact2;
        }
        return getEntityManager().merge(contact1);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<CV> listCVes() {
        Query query = getEntityManager().createQuery("Select cv from CV cv");
        List<CV> resList = query.getResultList();

        return resList;
    }
    @SuppressWarnings("unchecked")
    @Transactional
    public Contact contInfo(String id_cont) {
        Long id_cont_long = Long.parseLong(id_cont);
        Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return contact;
    }
    @Transactional
    public String getContactForThis(String id_cont) {
        Long id_cont_long = Long.parseLong(id_cont);
        Contact contact = getEntityManager().find(Contact.class, id_cont_long);
        return contact.toString();
    }

    @Transactional
    public String parseStatus(UploadedFile activeFileInSession){
        return SearchBoxParser.parseStatus(activeFileInSession);
    }
}
