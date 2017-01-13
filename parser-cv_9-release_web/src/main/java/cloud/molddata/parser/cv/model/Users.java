package cloud.molddata.parser.cv.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "Users.findAll", query = "SELECT us FROM Users us"),
        @NamedQuery(name = "Users.findBySessionID", query = "SELECT us FROM Users us where sessionID=:idReq")
})
public class Users {
    private long id;
    private String sessionID;
    private String nameAuth;
    private Date date = new Date();
    private List<CV> cves = new ArrayList<>();

    public Users(){    }
    public Users(String sessionID, String nameAuth){
        this.sessionID = sessionID;
        this.nameAuth = nameAuth;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", unique = true, nullable = false)
    public long getId() {
        return id;
    }
    @OneToMany(mappedBy = "usersByFK", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<CV> getCves() {        return cves;    }
    public String getSessionID() {
        return sessionID;
    }
    public String getNameAuth() {
        return nameAuth;
    }
    public Date getDate() {
        return this.date;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
    public void setNameAuth(String nameAuth) {
        this.nameAuth = nameAuth;
    }
    public void setCves(List<CV> cves) {
        this.cves = cves;
    }
    public void setId(long id) {
        this.id = id;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE", unique = true, nullable = false, length = 10)
    public void setDate(Date date) {
        this.date = date;
    }

    public void addCV(CV cv){
        cv.setUsersByFK(this);
        cves.add(cv);
    }


    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", name='" + sessionID + '\'' +
                ", nameAuth='" + nameAuth + '\'' +
                ", cves=" + cves +
                '}';
    }
}
