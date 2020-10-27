package is.hi.hbv501GEfnahagsspa.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "User")
@Entity
public class ForeCast {
    @Id
    @GeneratedValue
    private int foreCastID;
    private String foreCastName;
    private String foreCastDesc;
    private int foreCastUserID;

    public ForeCast(){
    }

    public ForeCast(String foreCastName,String foreCastDesc,int foreCastUserID){
        this.foreCastName=foreCastName;
        this.foreCastDesc = foreCastDesc;
        this.foreCastUserID = foreCastUserID;
    }

    public String getForeCastName(){
        return foreCastName;
    }

    public void setForeCastName(String foreCastName){
        this.foreCastName=foreCastName;
    }

    public String getForeCastDesc(){
        return foreCastDesc;
    }

    public void setForeCastDesc(String foreCastDesc){
        this.foreCastDesc=foreCastDesc;
    }

    public int getForeCastUserID(){
        return foreCastUserID;
    }

    public void setForeCastUserID(int foreCastUserID){
        this.foreCastUserID=foreCastUserID;
    }


}
