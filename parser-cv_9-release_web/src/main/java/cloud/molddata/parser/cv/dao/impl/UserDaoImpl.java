package cloud.molddata.parser.cv.dao.impl;

import cloud.molddata.parser.cv.dao.UserDao;
import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

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
	public void save(UserSecurity user, String sessionID) {
		String nameUserAuth = user.getUsername();
		if(user.getUsername() == null)
			getEntityManager().persist(user);
		else {
			getEntityManager().merge(user);
		}
		Query usersBySession = getEntityManager().createQuery("SELECT us FROM Users us where us.sessionID LIKE :sessionID").setParameter("sessionID",sessionID);
		List<Users> usersList =  usersBySession.getResultList();

		Users users = usersList.get(0);
		users.setNameAuth(nameUserAuth);
		getEntityManager().persist(users);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public void authorization(String nameAuth, String sessionID) {
		try {
			Query querySessionID = getEntityManager().createNamedQuery("Users.findBySessionID").setParameter("idReq",sessionID);

			Users userLogin = (Users) querySessionID.getSingleResult();

			System.out.println("insert into USERS nameAuth after LOGIN=" + userLogin.getNameAuth());
			userLogin.setNameAuth(nameAuth);

			getEntityManager().merge(userLogin);
			System.out.println("update into USERS nameAuth after LOGIN=" + userLogin.getNameAuth());

		}catch (NoResultException NRE){
			System.out.println("NULL??");
			Users usersNew = new Users(sessionID,nameAuth);
			getEntityManager().persist(usersNew);
		}
	}

	@SuppressWarnings("unchecked")
	public UserSecurity findByUserName(String username) {
		Query query = getEntityManager().createQuery("Select us from UserSecurity us where us.username LIKE :username").setParameter("username",username);
		List<UserSecurity> users = query.getResultList();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
}