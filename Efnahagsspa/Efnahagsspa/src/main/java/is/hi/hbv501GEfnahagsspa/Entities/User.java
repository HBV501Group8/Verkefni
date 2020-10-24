    package is.hi.hbv501GEfnahagsspa.Entities;


import javax.persistence.*;
import javax.xml.soap.Name;

@Table(name = "User")
@Entity

public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String userName;
    public String userPassword;
    private String Name;
    private String Email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public User() {

    }
}
