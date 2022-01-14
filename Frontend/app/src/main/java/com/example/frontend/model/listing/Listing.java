package com.example.frontend.model.listing;

import com.example.frontend.model.shelter.Shelter;

import java.util.ArrayList;
import java.util.List;

public class Listing {
    private int id;
    private String petName;
    private int petAge;
    private PetType petType;
    private String description;

    private List<String> pictureURLs;
    private Shelter shelter;

    public Listing(String petName, int petAge, PetType petType, String description, List<String> pictureURLs) {
        this.petName = petName;
        this.petAge = petAge;
        this.petType = petType;
        this.description = description;
        this.pictureURLs = pictureURLs;
    }

    public Listing() {this.pictureURLs = new ArrayList<>();}

    //Getters
    public int getId(){return this.id;}
    public String getPetName() {return petName;}
    public int getPetAge() {return petAge;}
    public PetType getPetType() {return petType;}
    public String getDescription() {return description;}
    public List<String> getPictureURL() {return pictureURLs;}
    public Shelter getShelter() {return shelter;}

    //Setters
    public void setId(int id){this.id = id;}
    public void setPetName(String petName) {this.petName = petName;}
    public void setPetAge(int petAge) {this.petAge = petAge;}
    public void setPetType(PetType petType) {this.petType = petType;}
    public void setDescription(String description) {this.description = description;}
    public void setPictureURL(List<String> pictureURLs) {this.pictureURLs = pictureURLs;}
    public void addPictureURL(String url){this.pictureURLs.add(url);}
    public void setShelter(Shelter shelter) {this.shelter = shelter;}

    @Override
    public boolean equals(Object o){
        if (o == this) { return true; }
        if (!(o instanceof Listing)) { return false; }
        return id == ((Listing) o).getId();
    }
}
