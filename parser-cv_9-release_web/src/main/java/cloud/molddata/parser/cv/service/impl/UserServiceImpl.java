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
    /*@Autowired
    private UserRepository userRepository;*/
    @Autowired
    //@Qualifier
    private UserDao userDao;
   /* @Autowired
    private RoleRepository roleRepository;*/
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(UserSecurity user, String sessionID) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
        user.setEnabled(true);

        UserSecRole userSecRole	=new UserSecRole();
        userSecRole.setRole(UserRole.ROLE_USER);
        //System.out.println(userSecRole.getuserSecurity().getUsername() + " " + userSecRole.getRole());

        userSecRole.setuserSecurity(user);
        user.getUserSecRole().add(userSecRole);


        //System.out.println(user.getUserSecRole().toString());

        //System.out.println(user.getUsername()+" "+user.getUserSecRole(0).toString());

        //user.setUserSecRole(new HashSet<UserSecRole>());
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        //user.setRoles(new HashSet<>(2,3.0f));
        //userRepository.save(user);


		/*Set<UserSecRole> userSecRole = userForm.getUserSecRole();*/
		/*System.out.println(userForm.getUserSecRole());
		UserSecRole userSecRole	=new UserSecRole(userForm,"Role_USER");
		Set<UserSecRole> setRole = new HashSet<UserSecRole>(0);
		setRole.add(userSecRole);
		userForm.setUserSecRole(setRole);*/
		/*userForm.setUserSecRole(newSet<UserSecRole>(0).add("ADMIN"));
		Set<UserSecRole> set = new HashSet<UserSecRole>(0);
		set.add(new UserSecRole(userForm,"Role_ADMIN"));

		public UserSecRole(UserSecurity user, String role) {
			this.userSecurity = user;
			this.role = role;
		}*/
        userDao.save(user, sessionID);
    }

    @Override
    public void authorization(String nameAuth, String sessionID) {
        userDao.authorization(nameAuth,sessionID);
    }

    @Override
    public UserSecurity findByUsername(String username) {
        return userDao.findByUserName(username);
    }
}
