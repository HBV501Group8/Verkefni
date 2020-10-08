package is.hi.hbv501GEfnahagsspa.Entities;


import javax.persistence.*;
import javax.xml.soap.Name;


@Entity

public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String userNane;
    public String userPassword;
    public String Name;
    public User() {

    }

    public boolean Boolean (String username, String password) {

        return true;
    }

    public User(String userNane, String userPassword, String name) {
        this.userNane = userNane;
        this.userPassword = userPassword;
        Name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserNane() {
        return userNane;
    }

    public void setUserNane(String userNane) {
        this.userNane = userNane;
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
}
