package cloud.molddata.parser.cv.service.impl;

import cloud.molddata.parser.cv.model.UserSecurity;
import cloud.molddata.parser.cv.model.UserRole;
import cloud.molddata.parser.cv.model.UserSecRole;
import cloud.molddata.parser.cv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		System.out.println("loadbyUSERNAME!!!!!!!!!!!!!!!!!!");
		UserSecurity userSec = userService.findByUsername(username);
		List<GrantedAuthority> authorities = buildUserAuthority(userSec.getUserSecRole());

		return buildUserForAuthentication(userSec, authorities);
		
	}

	// Converts com.parser.cv.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(UserSecurity userSec, List<GrantedAuthority> authorities) {
		return new User(userSec.getUsername(), userSec.getPassword(), userSec.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserSecRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();


		// Build user's authorities
		for (UserSecRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole().name()));
		}
		for (UserSecRole userRole : userRoles) {
			String getRoleStr = null;
			if(userRole.getRole().equals(UserRole.ROLE_USER)) {
				//setAuths.add(new SimpleGrantedAuthority("ROLE_"+UserRole.ROLE_USER.name()));
				getRoleStr = "ROLE_USER";
			}else {
				//setAuths.add(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()));
				getRoleStr="ROLE_ADMIN";
				System.out.println("role is ADMIN!!!");
			}
			setAuths.add(new SimpleGrantedAuthority(getRoleStr));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		for (GrantedAuthority so:Result){
			System.out.println("roles for-each ="+so.getAuthority());
		}

		return Result;
	}
}