package com.example.frontend.model.shelter;

import com.example.frontend.model.MainApplication;
/**
 * Controller for shelter to hold relevant server mappings
 */
public class ShelterController {
    /**
     * Get shelter mapping from username.
     * @param username username of shelter to be retrieved
     * @return get shelter url
     */
    public static String getShelterUrl(String username){
        return MainApplication.baseUrl +"shelters/username/" + username;
    }

    /**
     * create user mapping.
     * @return url to create a shelter
     */
    public static String createShelterUrl(){
        return MainApplication.baseUrl +"shelters/";
    }
}
