package com.data.shelter;

import com.data.listing.Listing;
import com.data.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shelter {

    /**
     * name - string
     * list of matches
     * list of listings
     * usernane - string
     * password - string
     * salt - string
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    private String shelterName;

    @OneToMany
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Listing> listings;

    public Shelter() {
        this.listings = new ArrayList<>();
    }

    public int getId() {return id;}
    public List<Listing> getListings() {return listings;}
    public User getUser() {return user;}
    public String getShelterName() {return shelterName;}

    public void setUser(User user) {this.user = user;}
    public void setId(int id) {this.id = id;}
    public void setListings(List<Listing> listings) {this.listings = listings;}
    public void setShelterName(String shelterName) {this.shelterName = shelterName;}
}
