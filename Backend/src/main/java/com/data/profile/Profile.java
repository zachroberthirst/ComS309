package com.data.profile;


import javax.persistence.*;

import com.data.user.User;
import org.hibernate.annotations.*;
import com.data.setting.Setting;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "Profiles_Table")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne
    private User user;

    @NotFound(action = NotFoundAction.IGNORE)
    private String tagline;

    @NotFound(action = NotFoundAction.IGNORE)
    private String biography;

    @NotFound(action = NotFoundAction.IGNORE)
    private String image;

    public Profile(User user, String tagline, String biography, String image){
        this.user = user;
        this.tagline = tagline;
        this.biography = biography;
        this.image = image;
    }
    public Profile() {}

    //Getters
    public int getId() { return id; }
    public User getUser() {return user;}
    public String getTagline() {return tagline;}
    public String getBiography() {return biography;}
    public String getImage() {return image;}

    //Setters
    public void setId(int id) {this.id = id; }
    public void setUser(User user) {this.user = user;}
    public void setTagline(String tagline) {this.tagline = tagline;}
    public void setBiography(String biography) {this.biography = biography;}
    public void setImage(String image) {
        this.image = image;
    }
}
