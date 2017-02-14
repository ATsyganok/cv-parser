package cloud.molddata.parser.cv.service;

import cloud.molddata.parser.cv.model.*;

import java.util.List;

public interface UserUploadService {

    List<Users> getListUsersByName(String nameAuth, String sessionID);

    List<Users> getListUsersAll();

    List<UserSecurity> getListUsersAuth();

    void createAnonymousUser(String sessionID);

}
