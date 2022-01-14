package com.example.frontend.presenter.userRegistration;

import android.util.Log;

import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserController;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.view.registrationScreen.userRegistrationScreen.IRegistrationView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationPresenter implements IVolleyListener, IRegistrationPresenter {
    IRegistrationView v;
    IServerRequest serverRequest;

    /**
     * New user registration presenter
     * @param v view of registration activity
     * @param serverRequest server requests
     */
    public RegistrationPresenter(IRegistrationView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void hasUser(String username){
        String url = UserController.hasUserUrl(username);
        //ask DB if username exists returns onSuccess() or onError()
        serverRequest.sendStringToServer("hasUser", url, "GET");
    }

    @Override
    public void sendUser(User user){
        String url = UserController.createUserUrl();
        //create user object from json
        ObjectMapper mapper = new ObjectMapper();
        JSONObject postData = null;
        try {
            postData = new JSONObject(mapper.writeValueAsString(user));
            postData.remove("id");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }

        //send DB user returns onSuccess() or onError()
        serverRequest.sendJsonToServer("sendUser", url, postData, "POST");
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("sendUser")){

            //load response into User object
            ObjectMapper mapper = new ObjectMapper();
            User user = null;
            try {
                user = mapper.readValue(response.toString(), User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            Log.d("UserRegistrationPresenter","got : "+user);
            //set active user
            MainApplication.setActiveUser(user);
            v.nextPage();
        }

    }

    @Override
    public void onErrorJson(String tag, String response) {
        System.out.println(response);
        if(tag.equals("sendUser")){

        }
    }

    @Override
    public void onSuccessString(String tag, String response) {
        if(tag.equals("hasUser")) {
            boolean hasUser = Boolean.parseBoolean(response);
            if (hasUser) {
                //username taken
                v.usernameTaken();
            } else {
                //username free
                v.usernameFree();
            }
        }
    }

    @Override
    public void onErrorString(String tag, String response) {
        System.out.println(response);
    }

    @Override
    public void onSuccessArray(String tag, JSONArray response) {

    }

    @Override
    public void onErrorArray(String tag, String response) {
        System.out.println(response);
    }

}
