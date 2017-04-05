package cloud.molddata.parser.cv.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_security")
@NamedQueries({
        @NamedQuery(name = "UserSecurity.findAll", query = "SELECT uss FROM UserSecurity uss"),
        @NamedQuery(name = "UserSecurity.findByName", query = "SELECT uss FROM UserSecurity uss where username=:name")
})
public class UserSecurity {

    private String username;
    private String password;
    private boolean enabled;
    private Set<UserSecRole> userSecRole = new HashSet<UserSecRole>(0);

    public UserSecurity() {
    }

    public UserSecurity(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public UserSecurity(String username, String password, boolean enabled, Set<UserSecRole> userSecRole) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userSecRole = userSecRole;
    }

    @Id
    @Column(name = "username", unique = true, nullable = false, length = 45)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userSecurity", cascade = CascadeType.ALL)
    public Set<UserSecRole> getUserSecRole() {
        return this.userSecRole;
    }

    public void setUserSecRole(Set<UserSecRole> userSecRole) {
        this.userSecRole = userSecRole;
    }

}
