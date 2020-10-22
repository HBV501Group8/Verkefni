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
    public String Name;
    public String email;
    public boolean isEnabled;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String userEmail;

    public User() {

    }

    public boolean Boolean (String username, String password) {

        return true;
    }

    public User(String userNane, String userPassword, String name) {
        this.userName = userName;
        this.userPassword = userPassword;
        Name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
