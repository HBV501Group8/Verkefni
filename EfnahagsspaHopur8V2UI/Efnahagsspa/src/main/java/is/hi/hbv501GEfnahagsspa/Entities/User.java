    package is.hi.hbv501GEfnahagsspa.Entities;


//import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import javax.xml.soap.Name;
import java.util.ArrayList;
import java.util.List;

@Table(name = "User")
@Entity

public class  User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Eigindi fyrir User klasa

    public long id;
    public String userName;
    public String userPassword;
    private String Name;
    private String Email;
    public Boolean isEnabled;
    public Boolean isAdmin;

    // Listi með spám sem notandi hefur gert
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Forecast> forecasts = new ArrayList<Forecast>();

    // Constructor
    public User() { }

    // Setters og getters

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

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public List<Forecast> getForecasts() { return forecasts; }

    public void setForecasts(List<Forecast> forecasts) { this.forecasts = forecasts; }
}
