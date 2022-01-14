package com.data.listing;

import com.data.shelter.Shelter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotFound(action = NotFoundAction.IGNORE)
    private String petName;

    @NotFound(action = NotFoundAction.IGNORE)
    private int petAge;

    @NotFound(action = NotFoundAction.IGNORE)
    private PetType petType;

    @NotFound(action = NotFoundAction.IGNORE)
    private String description;

    @ElementCollection
    @NotFound(action = NotFoundAction.IGNORE)
    private List<String> pictureURLs;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
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

}
