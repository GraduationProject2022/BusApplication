package hai2022.team.busapplication.models;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private int img;
    private String imgPath;
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String type;
    private String created_at;
    private String updated_at;


    public User() {
    }


    public User(String id, int img, String username, String type) {
        this.id = id;
        this.img = img;
        this.username = username;
        this.type = type;
    }

    public User(String id, String username, String fullname, String email, String phone, String address, String type, String created_at, String updated_at) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public User(String id, int img, String username, String fullname, String email, String phone, String address, String type, String created_at, String updated_at) {
        this.id = id;
        this.img = img;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.type = type;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
