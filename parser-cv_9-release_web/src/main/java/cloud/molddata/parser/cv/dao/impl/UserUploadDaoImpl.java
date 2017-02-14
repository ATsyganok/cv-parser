package cloud.molddata.parser.cv.dao.impl;


import cloud.molddata.parser.cv.dao.UserUploadDao;
import cloud.molddata.parser.cv.model.*;
import cloud.molddata.parser.cv.parser.FileParcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserUploadDaoImpl implements UserUploadDao {

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
    public List<Users> getListUsersByName(String nameAuth, String sessionID) {
        if (("000").equals(sessionID) && ("anonymousUser").equals(nameAuth)) {
            Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth", "anonymousUser");
            return query.getResultList();
        }
        if (("anonymousUser").equals(nameAuth)) {
            Query querySessionID = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq", sessionID);
            return querySessionID.getResultList();
        }
        Query query = getEntityManager().createQuery("Select us from Users us WHERE us.nameAuth like :nameAuth").setParameter("nameAuth", nameAuth);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<Users> getListUsersAll() {
        Query queryUsersAll = getEntityManager().createNamedQuery("Users.findAll");
        return queryUsersAll.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<UserSecurity> getListUsersAuth() {
        Query queryUsersAll = getEntityManager().createNamedQuery("UserSecurity.findAll");
        return queryUsersAll.getResultList();
    }


    @Transactional
    public void createUser(String sessionID) {
        Query usersBySession = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq", sessionID);
        List<Users> usersList = usersBySession.getResultList();
        if (usersList.size() == 0) {
            Users users = new Users(sessionID, "anonymousUser");
            getEntityManager().persist(users);
        }
    }

}
