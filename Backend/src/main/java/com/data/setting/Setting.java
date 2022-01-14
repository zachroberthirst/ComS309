package com.data.setting;

import javax.persistence.*;

import com.data.listing.PetType;
import com.data.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Setting_Table")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int searchRange;

    @OneToOne
    private User user;

    //@Column(name = "animal_type")
    //TODO figure out efficient way to store multiple animal types
    //s et the enum type when animal is created and store it to a database
    // based on enum type. then sort by databases rather than
    // within a large database


    @ElementCollection
    @NotFound(action = NotFoundAction.IGNORE)
    private List<PetType> petPreferences;
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

