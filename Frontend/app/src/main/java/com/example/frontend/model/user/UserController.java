package com.example.frontend.model.user;

import com.example.frontend.model.MainApplication;

/**
 * Controller for user to hold relevant server mappings
 */
public class UserController {
    /**
     * Create user mapping.
     * @return url of create user mapping
     */
    public static String createUserUrl(){
        return MainApplication.baseUrl +"users/";
    }

    /**
     * Has user mapping.
     * @param username username of user to be checked
     * @return url of has user mapping
     */
    public static String hasUserUrl(String username){
        return MainApplication.baseUrl +"users/username/has/" + username;
    }

    /**
     * Get user mapping from username.
     * @param username username of user to be retrieved.
     * @return url of Get user mapping
     */
    public static String getUserUrl(String username){
        return MainApplication.baseUrl +"users/username/" + username;
    }

    /**
     * Get user mapping from id.
     * @param Id id of user to be retrieved.
     * @return Get user url
     */
    public static String getUserUrl(int Id){
        return MainApplication.baseUrl +"users/" + Id;
    }

    /**
     * Get all user mapping.
     * @return Get all users url
     */
    public static String getUserUrl(){
        return MainApplication.baseUrl +"users/";
    }
}
