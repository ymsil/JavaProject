package trabelstesh.javaproject.model.entities;

/**
 * Created by ymsil on 12/8/2016.
 */

public class User
{
    private long Id;
    private String Name;
    private String password;

    public User(){}

    public User(long userNum, String name, String password) {
        this.Id = userNum;
        this.Name = name;
        this.password = password;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
