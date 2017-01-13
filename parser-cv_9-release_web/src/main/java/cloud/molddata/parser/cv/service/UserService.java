package cloud.molddata.parser.cv.service;


import cloud.molddata.parser.cv.model.UserSecurity;

public interface UserService {
    void save(UserSecurity user, String sessionID);

    void authorization(String nameAuth, String sessionID);

    UserSecurity findByUsername(String username);
}
