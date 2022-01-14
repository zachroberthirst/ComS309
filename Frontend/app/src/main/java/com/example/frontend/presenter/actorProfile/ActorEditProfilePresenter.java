package com.example.frontend.presenter.actorProfile;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.profile.ProfileController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreens.actorProfile.IActorEditProfileView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActorEditProfilePresenter implements IVolleyListener, IActorEditProfilePresenter {
    private IActorEditProfileView v;
    private IServerRequest serverRequest;

    public ActorEditProfilePresenter(IActorEditProfileView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("getProfile")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Profile p = mapper.readValue(response.toString(), Profile.class);
                v.gotProfile(p);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(tag.equals("updateProfile")){
            v.close();
        }
    }

    @Override
    public void onErrorJson(String tag, String response) {

    }

    @Override
    public void onSuccessString(String tag, String response) {

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

    @Override
    public void getUserProfile() {
        User u = MainApplication.getActiveUser();
        String url = ProfileController.getProfileUrl(u.getUsername());
        serverRequest.sendJsonToServer("getProfile", url, new JSONObject(), "GET");
    }

    @Override
    public void updateProfile(Profile profile) {
        String url = ProfileController.getProfileUrl();
        ObjectMapper mapper = new ObjectMapper();
        try {
            serverRequest.sendJsonToServer("updateProfile", url, new JSONObject(mapper.writeValueAsString(profile)), "PUT");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
