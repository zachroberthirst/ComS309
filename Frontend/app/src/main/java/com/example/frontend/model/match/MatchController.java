package com.example.frontend.model.match;

import com.example.frontend.model.MainApplication;

/**
 * Controller for match to hold relevant server mappings
 */
public class MatchController {
    /**
     * Create Match mapping.
     * @return Create match url
     */
    public static String createMatchUrl(){
        return MainApplication.baseUrl +"matches/";
    }

    /**
     * Get matches Mapping from username.
     * @param username username of user, to find its matches
     * @return Get matches user url
     */
    public static String getUserMatchesUrl(String username){
        return MainApplication.baseUrl +"matches/user/" + username;
    }

    /**
     * Get shelter matches from username.
     * @param username username of shelter, to find its matches
     * @return Get matches shelter url
     */
    public static String getShelterMatchesUrl(String username){
        return MainApplication.baseUrl +"matches/shelter/" + username;
    }

    public static String getListingMatchesUrl(int id){
        return MainApplication.baseUrl +"matches/listing/" + id;
    }

    /**
     * Get match Mapping from id.
     * @param Id id of match to be retrieved
     * @return Get matches url
     */
    public static String getMatchUrl(int Id){
        return MainApplication.baseUrl +"matches/" + Id;
    }

    /**
     * Get matches mapping
     * @return get matches url
     */
    public static String getMatchUrl(){
        return MainApplication.baseUrl +"matches/";
    }

    /**
     * Assign actor and listing to match mapping.
     * @param user_id id of user to be linked
     * @param listing_id id of listing to be linked
     * @param match_id id of match to hold actors
     * @return Assign actors to match url
     */
    public static String assignUserAndListingToMatch(int user_id, int listing_id, int match_id){
        return MainApplication.baseUrl + "/matches/" +user_id+ "/" +listing_id+ "/match/" +match_id;
    }
}
