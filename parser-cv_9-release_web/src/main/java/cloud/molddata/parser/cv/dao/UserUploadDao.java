package cloud.molddata.parser.cv.dao;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface UserUploadDao {

    static final int i = 0;

    List<Users> getListUsersByName(String nameAuth, String sessionID);

    List<Users> getListUsersAll();

    List<UserSecurity> getListUsersAuth();

    void createUser(String sessionID);

}
