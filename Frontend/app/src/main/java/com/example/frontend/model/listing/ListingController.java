package com.example.frontend.model.listing;

import com.example.frontend.model.MainApplication;

import java.util.List;

public class ListingController {

    public static String getListingUrl(){
        return MainApplication.baseUrl +"listing/";
    }
    public static String getListingUrl(int id){
        return MainApplication.baseUrl +"listing/"+id;
    }
    public static String getShelterListingUrl(String username){
        return MainApplication.baseUrl +"listing/shelter/username/"+username;
    }
    public static String assignListingAndShelterUrl(int listing_id, int shelter_id){
        return MainApplication.baseUrl +"listing/" +listing_id+"/shelter/"+shelter_id;
    }
    public static String getListingByPreferencesUrl(int num) {
        return MainApplication.baseUrl +"listing/shelter/filters/"+num;
    }
}
