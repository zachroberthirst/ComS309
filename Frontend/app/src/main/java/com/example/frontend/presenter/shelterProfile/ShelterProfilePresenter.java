package com.example.frontend.presenter.shelterProfile;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreensShelter.shelterProfile.IShelterProfileFragmentView;

import org.json.JSONArray;
import org.json.JSONObject;


public class ShelterProfilePresenter implements IShelterProfilePresenter, IVolleyListener{
    IShelterProfileFragmentView v;
    IServerRequest serverRequest;

    private Shelter s;

    public ShelterProfilePresenter(IShelterProfileFragmentView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        s = MainApplication.getActiveShelter();
    }

    @Override
    public void initInfo() {
        v.setShelterName(s.getShelterName());
        v.progressComplete();
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {

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
}
