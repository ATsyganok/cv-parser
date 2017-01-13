package cloud.molddata.parser.cv.model;

import javax.persistence.*;

/**
 * Created by admin on 13.09.2016.
 */
@Entity
@Table(name = "Contact")
public class Contact {
    private String text = null;
    private int weightContact = 0;


    private long id;
    private String fullName;
    private String phone;
    private String location;
    private CV cv;
    private String email;


    public Contact(){}
    public Contact(String fullName,String phone, String location){
        this.fullName = fullName;
        this.phone = phone;
        this.location = location;
    }
    public Contact(CV cv, String fullName,String phone, String location){
        this.cv = cv;
        this.fullName = fullName;
        this.phone = phone;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Contact_id", unique = true, nullable = false)
    public long getId() {        return id;    }

    @Column(name = "FULL_NAME", nullable = true, length = 100)
    public String getFullName() {        return fullName;    }

    @Column(name = "PHONE", nullable = true)
    public String getPhone() {        return phone;    }

    @Column(name = "LOCATION", nullable = true, length = 5000)
    public String getLocation() {        return location;    }

    @OneToOne(fetch = FetchType.LAZY)//mappedBy = Contact
    @PrimaryKeyJoinColumn
    @JoinColumn(name = "cv_id")
    public CV getcv() {        return cv;    }

    @Column(name ="EMAIL")
    public String getEmail() {
        return email;
    }

    public int getWeightContact() {
        return weightContact;
    }

    public void setId(long id) {        this.id = id;    }
    public void setFullName(String fullName) {        this.fullName = fullName;    }
    public void setPhone(String phone) {        this.phone = phone;    }
    public void setLocation(String location) {        this.location = location;    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setCv(CV cv) {        this.cv = cv;    }
    public void setText(String text) {        this.text = text;    }
    public void setWeightContact(int weightContact) {        this.weightContact = weightContact;    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
