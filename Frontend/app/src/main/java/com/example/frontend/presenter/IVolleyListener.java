package com.example.frontend.presenter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * listener to hold logic from server responses
 */
public interface IVolleyListener {
    /**
     * Successful Response from server, when sending or receiving a Json object
     * @param response Json response from server
     */
    void onSuccessJson(String tag, JSONObject response);

    /**
     * Failed response from server, when sending or receiving a Json object
     * @param response Error message
     */
    void onErrorJson(String tag, String response);

    /**
     * Successful Response from server, when sending or receiving a String
     * @param response String response from server
     */
    void onSuccessString(String tag, String response);

    /**
     * Failed response from server, when sending or receiving a String object
     * @param response Error message
     */
    void onErrorString(String tag, String response);

    /**
     * Successful Response from server, when sending or receiving a Json array
     * @param response JSONArray response from server
     */
    void onSuccessArray(String tag, JSONArray response);

    /**
     * Failed response from server, when sending or receiving a Json array
     * @param response Error message
     */
    void onErrorArray(String tag, String response);

}
