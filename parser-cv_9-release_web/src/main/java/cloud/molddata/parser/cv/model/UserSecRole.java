package cloud.molddata.parser.cv.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = { "role", "username" }))
public class UserSecRole {

	private Integer userSecRoleId;
	private UserSecurity userSecurity;
	private UserRole role;

	public UserSecRole() {
	}

	public UserSecRole(UserSecurity user, UserRole role) {
		this.userSecurity = user;
		this.role = role;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_role_id", unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userSecRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userSecRoleId = userRoleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	public UserSecurity getuserSecurity() {
		return this.userSecurity;
	}

	public void setuserSecurity(UserSecurity user) {
		this.userSecurity = user;
	}
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "role", nullable = false, length = 45)
	public UserRole getRole() {
		return this.role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}