package cloud.molddata.parser.cv.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "uploaded_file")
public class UploadedFile {

  private Long id;
  private String name;
  private String location;
  private Long size;
  private String type;
  private String status;
  private String sessionID;
  private Date date = new Date();


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }
  @Column(nullable = false)
  public String getName() {
    return name;
  }
  @Column(nullable = false)
  public String getLocation() {
    return location;
  }
  @Column()
  public Long getSize() {
    return size;
  }
  @Column(nullable = false)
  public String getType() {
    return type;
  }
  public String getStatus() {
    return status;
  }
  public Date getDate() {
    return this.date;
  }
  @Column
  public String getSessionID() {
    return sessionID;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  @Temporal(TemporalType.DATE)
  @Column(name = "DATE", unique = true, nullable = false, length = 10)
  public void setDate(Date date) {
    this.date = date;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }
}
