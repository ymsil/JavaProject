package trabelstesh.javaproject.model.entities;

/**
 * Created by ymsil on 12/8/2016.
 */

public class Business
{
    private long id;
    private String name;
    private String address;
    private String Phone;
    private String email;
    private String website;


    public long getId() {
        return id;
    }
    public long setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public Business(long id, String name, String address, String phone, String email, String website) {
        this.id = id;
        this.name = name;
        this.address = address;
        Phone = phone;
        this.email = email;
        this.website = website;
    }

    @Override
    public String toString() {
        return "Business{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

}
