package cloud.molddata.parser.cv.service.impl;


import cloud.molddata.parser.cv.dao.UserDao;
import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.UserRole;
import cloud.molddata.parser.cv.model.UserSecRole;
import cloud.molddata.parser.cv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserSecurity user, String sessionID) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        UserSecRole userSecRole = new UserSecRole();
        userSecRole.setRole(UserRole.ROLE_USER);

        userSecRole.setuserSecurity(user);
        user.getUserSecRole().add(userSecRole);

        userDao.save(user, sessionID);
    }

    @Override
    public void authorization(String nameAuth, String sessionID) {
        userDao.authorization(nameAuth, sessionID);
    }

    @Override
    public UserSecurity findByUsername(String username) {
        return userDao.findByUserName(username);
    }
}
