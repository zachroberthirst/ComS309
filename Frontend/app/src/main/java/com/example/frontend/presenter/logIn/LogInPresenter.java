package com.example.frontend.presenter.logIn;


import android.util.Log;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.helpers.HashPassword;
import com.example.frontend.model.setting.Setting;
import com.example.frontend.model.setting.SettingController;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.shelter.ShelterController;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserController;
import com.example.frontend.model.user.UserType;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.view.logInScreen.ILogInView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.json.*;
import org.json.JSONObject;

public class LogInPresenter implements IVolleyListener, ILogInPresenter{

    ILogInView v;
    IServerRequest serverRequest;

    /**
     * New Log In presenter
     * @param v view of Log In activity
     * @param serverRequest server requests
     */
    public LogInPresenter(ILogInView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }


    @Override
    public void logIn(String username) {
        String url = UserController.getUserUrl(username);
        serverRequest.sendJsonToServer("getUser",url, new JSONObject(), "GET");
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("getUser")) {
            Log.d("LogInPresenter", "got user: " + response);
            //log in user
            getUser(response);

        }else if(tag.equals("getShelter")){
            Log.d("LogInPresenter", "got shelter: " + response);
            //log in shelter
            getShelter(response);
        }else if(tag.equals("getSetting")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Setting s = mapper.readValue(response.toString(), Setting.class);
                User u = MainApplication.getActiveUser();
                u.setSetting(s);
                MainApplication.setActiveUser(u);

                if(u.getType() == UserType.USER){
                    v.nextPage();
                }else if(u.getType() == UserType.SHELTER){
                    v.shelterNextPage();
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorJson(String tag, String response) {
        if(tag.equals("getUser")){
            v.setErrors("Invalid Username or Password");
        }else if(tag.equals("getShelter")){
            v.setErrors("Invalid Username or Password");
        }else if (tag.equals("getSetting")){
            User u = MainApplication.getActiveUser();
            u.setSetting(null);
            MainApplication.setActiveUser(u);

            if(u.getType() == UserType.USER){
                v.nextPage();
            }else if(u.getType() == UserType.SHELTER){
                v.shelterNextPage();
            }
        }
    }

    @Override
    public void onSuccessString(String tag, String response) {

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

    private void getShelter(JSONObject response){
        ObjectMapper mapper = new ObjectMapper();
        //create shelter
        try {
            Shelter shelter = mapper.readValue(response.toString(), Shelter.class);

            Log.d("LogInPresenter", "new shelter: " + shelter);

            if (!HashPassword.verify(v.getPassword(), shelter.getUser().getSalt(), shelter.getUser().getPassword())) {
                v.setErrors("Invalid Username or Password");
                return;
            }
            Log.d("LogInPresenter", "Passwords Match");
            v.setErrors(null);
            //set active user
            MainApplication.setActiveUser(shelter.getUser());
            MainApplication.setActiveShelter(shelter);
            if(shelter.getUser().getSetting() == null) {
                getSettings();
            }else{
                v.shelterNextPage();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void getUser(JSONObject response){
        ObjectMapper mapper = new ObjectMapper();
        //create user
        try {

            User user = mapper.readValue(response.toString(), User.class);
            //if user is a shelter, then log shelter in
            if (user.getType() == UserType.SHELTER) {
                //get shelter object
                String url = ShelterController.getShelterUrl(user.getUsername());
                serverRequest.sendJsonToServer("getShelter", url, new JSONObject(), "GET");

            } else {
                //else log in user
                Log.d("LogInPresenter", "new user: " + user);

                if (!HashPassword.verify(v.getPassword(), user.getSalt(), user.getPassword())) {
                    v.setErrors("Invalid Username or Password");
                    return;
                }
                Log.d("LogInPresenter", "Passwords Match");
                v.setErrors(null);
                //set active user
                if(user.getType() == null) {
                    user.setType(UserType.USER);
                }
                MainApplication.setActiveUser(user);
                if(user.getSetting() == null){
                    getSettings();
                }else{
                    v.nextPage();
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void getSettings(){
        String url = SettingController.getSettingUrl(MainApplication.getActiveUser().getUsername());
        serverRequest.sendJsonToServer("getSetting", url, new JSONObject(), "GET");
    }

}
