package com.example.frontend.model.shelter;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Shelter {

    private int id = -1;
    private User user;
    private String shelterName;
    private List<Listing> listings;

    public Shelter(User user, String shelterName) {
        this.user = user;
        this.shelterName = shelterName;
        listings = new ArrayList<>();
    }

    public Shelter() {
        listings = new ArrayList<>();
    }

    //Getters
    public int getId() { return id; }
    public User getUser() { return user; }
    public String getShelterName() { return shelterName; }
    public List<Listing> getListings() { return listings; }

    //Setters
    public void setId(int id) {this.id = id; }
    public void setUser(User user) {this.user = user; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }
    public void addListing(Listing listing){ listings.add(listing); }
    public void setListings(List<Listing> listings) { this.listings = listings; }
    public void clearListings() { listings.clear(); }
}
