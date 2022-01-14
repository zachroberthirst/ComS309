package com.example.frontend.model.profile;

import com.example.frontend.model.user.User;

public class Profile {
    private int id;
    private User user;
    private String tagline;
    private String biography;
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
    public void setImage(String image) { this.image = image;}
}
