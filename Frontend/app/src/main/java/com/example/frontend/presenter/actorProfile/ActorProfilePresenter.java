package com.example.frontend.presenter.actorProfile;

import android.util.Log;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.profile.ProfileController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreens.actorProfile.IActorProfileFragmentView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;


public class ActorProfilePresenter implements IVolleyListener, IActorProfilePresenter{
    IActorProfileFragmentView v;
    IServerRequest serverRequest;

    private User u;

    public ActorProfilePresenter (IActorProfileFragmentView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        u = MainApplication.getActiveUser();
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("getProfile")){
            Log.d("ActorProfilePresenter", "got profile: " + response);
            ObjectMapper mapper = new ObjectMapper();

            try {
                Profile profile = mapper.readValue(response.toString(), Profile.class);
                v.setName(u.getFirstName(),u.getLastName());
                v.setBio(profile.getBiography());
                v.setTagline(profile.getTagline());
                v.setPicture(profile.getImage());
                v.setToolBar(u.getUsername());
                v.progressComplete();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onErrorJson(String tag, String response) {
        v.noProfile();
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
    public void initInfo() {
        String url = ProfileController.getProfileUrl(u.getUsername());
        serverRequest.sendJsonToServer("getProfile", url, new JSONObject(), "GET");
    }
}
