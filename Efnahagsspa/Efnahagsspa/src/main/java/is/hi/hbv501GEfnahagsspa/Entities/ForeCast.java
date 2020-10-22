package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ForeCast")
@Entity
public class ForeCast {
    public String foreCastName;
    public String foreCastDesc;
    public int foreCastUserID;
    private String id;


    public String getForeCastName() {
        return foreCastName;
    }

    public void setForeCastName(String foreCastName) {
        this.foreCastName = foreCastName;
    }

    public String getForeCastDesc() {
        return foreCastDesc;
    }

    public void setForeCastDesc(String foreCastDesc) {
        this.foreCastDesc = foreCastDesc;
    }

    public int getForeCastUserID() {
        return foreCastUserID;
    }

    public void setForeCastUserID(int foreCastUserID) {
        this.foreCastUserID = foreCastUserID;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
