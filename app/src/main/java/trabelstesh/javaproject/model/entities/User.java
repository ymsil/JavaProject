package trabelstesh.javaproject.model.entities;

/**
 * Created by ymsil on 12/8/2016.
 */

public class User
{
    private int Id;
    private String Name;
    private String password;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
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
