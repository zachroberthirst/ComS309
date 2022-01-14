package com.example.frontend.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.frontend.model.match.Match;
import com.example.frontend.model.setting.Setting;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User{

    private int id = -1;

    private String firstName;

    private String lastName;

    private String birthday;

    private String username;

    private String email;

    private String password;

    private String salt;

    private List<Match> matches;
    private Setting setting;
    private UserType type;

    public User(String firstName, String lastName, String birthday, String email, String userName, String password, String salt, UserType type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.username = userName;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.type = type;
        matches = new ArrayList<>();
    }

    public User() {
        matches = new ArrayList<>();
    }


    //Getters
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBirthday() { return birthday; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getSalt() { return salt; }
    public Setting getSetting() { return setting; }
    public UserType getType() { return type; }
    public List<Match> getMatches() { return matches; }

    //Setters
    public void setId(int id) {this.id = id; }
    public void setFirstName(String firstName) {this.firstName = firstName; }
    public void setLastName(String lastName) {this.lastName = lastName; }
    public void setBirthday(String birthday) {this.birthday = birthday; }
    public void setUsername(String username) {this.username = username; }
    public void setEmail(String email) {this.email = email; }
    public void setPassword(String password) {this.password = password; }
    public void setSalt(String salt) {this.salt = salt; }
    public void setSetting(Setting setting) {this.setting = setting; }
    public void setType(UserType type) {this.type = type; }
    public void addMatch(Match match){ matches.add(match); }
    public void clearMatches(){matches.clear();}

    @Override
    public String toString(){
        return ("User: "+id+
                ", Firstname: "+firstName+
                ", Lastname: "+lastName+
                ", Birthday: "+birthday+
                ", Username: "+username+
                ", Email: "+email+
                ", Password: "+password+
                ", Salt: "+ salt+
                ", Type: "+type);
    }
}
