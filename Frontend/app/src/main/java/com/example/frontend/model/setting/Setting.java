package com.example.frontend.model.setting;

import com.example.frontend.model.listing.PetType;
import com.example.frontend.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Setting {
    private int id;
    private int searchRange;
    private List<PetType> petPreferences;
    private User user;

    public Setting(int id, int searchRange)
    {
        this.id = id;
        this.searchRange = searchRange;
        petPreferences = new ArrayList<>();
    }

    //Empty Constructor
    public Setting() {
        petPreferences = new ArrayList<>();
    }

    //Getters
    public int getId() {return id;}
    public int getSearchRange() {return searchRange;}
    public List<PetType> getPetPreferences() {
        return petPreferences;
    }
    public User getUser() {
        return user;
    }

    //Setters
    public void setId(int id) {this.id = id;}
    public void setSearchRange(int searchRange) {this.searchRange = searchRange;}
    public void setPetPreferences(List<PetType> petPreferences) {
        this.petPreferences = petPreferences;
    }
    public void addPetPreference(PetType type){
        petPreferences.add(type);
    }
    public void setUser(User user) {
        this.user = user;
    }
}
