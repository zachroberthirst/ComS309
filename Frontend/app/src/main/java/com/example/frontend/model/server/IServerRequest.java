package com.example.frontend.model.server;

import com.example.frontend.presenter.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Server request to call to server and respond using the logic class
 */
public interface IServerRequest {
    /**
     * Send Json request to server, calls logic.onSuccessJson(response) or logic.onErrorJson(error) based on server response.
     * @param url Url of request
     * @param jsonObject Json object to be sent
     * @param methodType Type of request
     */
    void sendJsonToServer(String tag, String url, JSONObject jsonObject, String methodType);

    /**
     * Send String request to server, calls logic.onSuccessString(response) or logic.onErrorString(error) based on server response.
     * @param url Url of request
     * @param methodType Type of request
     */
    void sendStringToServer(String tag, String url, String methodType);

    /**
     * Send Json array request to server, calls logic.onSuccessArray(response) or logic.onErrorArray(error) based on server response.
     * @param url Url of request
     * @param jsonArray Json Array to be sent
     * @param methodType Type of request
     */
    void sendJsonArrToServer(String tag, String url, JSONArray jsonArray, String methodType);

    /**
     * Add logic to be called based on successes or errors.
     * @param logic logic to be set
     */
    void addVolleyListener(IVolleyListener logic);
}
