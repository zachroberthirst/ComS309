package com.example.frontend.presenter.shelterRegistration;

import android.util.Log;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.shelter.ShelterController;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserController;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.registrationScreen.shelterRegistrationScreen.IShelterRegistrationView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShelterRegistrationPresenter implements IVolleyListener, IShelterRegistrationPresenter{
    IShelterRegistrationView v;
    IServerRequest serverRequest;
    private Shelter shelter;

    /**
     * New shelter registration presenter
     * @param v view of registration activity
     * @param serverRequest server requests
     */
    public ShelterRegistrationPresenter(IShelterRegistrationView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void sendShelter(Shelter shelter) {
        this.shelter = shelter;
        String url = UserController.createUserUrl();

        ObjectMapper mapper = new ObjectMapper();
        JSONObject postData = null;
        try {
            postData = new JSONObject(mapper.writeValueAsString(shelter.getUser()));
            postData.remove("id");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Send: "+postData);
        serverRequest.sendJsonToServer("createUser", url, postData, "POST");
    }
    private void createShelter(){
        String url = ShelterController.createShelterUrl();
        //create user object from json
        ObjectMapper mapper = new ObjectMapper();
        JSONObject postData = null;
        try {
            postData = new JSONObject(mapper.writeValueAsString(shelter));
            postData.remove("id");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        //send DB user returns onSuccess() or onError()
        serverRequest.sendJsonToServer("createShelter", url, postData, "POST");
    }

    @Override
    public void hasShelter(String username){
        String url = UserController.hasUserUrl(username);
        //ask DB if username exists returns onSuccess() or onError()
        serverRequest.sendStringToServer("hasShelter", url, "GET");
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("createUser")){
            //load response into User object
            ObjectMapper mapper = new ObjectMapper();
            //create user
            try {
                User user = mapper.readValue(response.toString(), User.class);
                this.shelter.setUser(user);
                createShelter();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(tag.equals("createShelter")) {
            //load response into Shelter object
            ObjectMapper mapper = new ObjectMapper();
            //create shelter
            try {
                Shelter shelter = mapper.readValue(response.toString(), Shelter.class);
                Log.d("ShelterRegistrationPresenter","got : "+shelter);
                //set active shelter
                MainApplication.setActiveUser(shelter.getUser());
                MainApplication.setActiveShelter(shelter);
                v.nextPage();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onErrorJson(String tag, String response) {
        if(tag.equals("createUser")){

        }else if(tag.equals("createShelter")){

        }
    }

    @Override
    public void onSuccessString(String tag, String response) {
        if(tag.equals("hasShelter")) {
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

    }

    @Override
    public void onSuccessArray(String tag, JSONArray response) {

    }

    @Override
    public void onErrorArray(String tag, String response) {

    }
}
