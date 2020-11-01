package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.*;

@Table(name = "User")
@Entity
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    public String userName;
    public String userPassword;

    public  Auth() {

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
}
