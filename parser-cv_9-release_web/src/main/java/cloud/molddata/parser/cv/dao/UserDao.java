package cloud.molddata.parser.cv.dao;


import cloud.molddata.parser.cv.model.UserSecurity;

public interface UserDao {

	void save(UserSecurity user, String sessionID);

	void authorization(String nameAuth, String sessionID);

	UserSecurity findByUserName(String username);

}