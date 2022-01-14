package com.example.frontend.presenter.shelterListings;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterListingsView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShelterListingsPresenter implements IShelterListingsPresenter, IVolleyListener {

    IShelterListingsView v;
    IServerRequest serverRequest;
    private Shelter s;

    public ShelterListingsPresenter(IShelterListingsView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        s = MainApplication.getActiveShelter();
    }


    @Override
    public void getListings(){
        String url = ListingController.getShelterListingUrl(s.getUser().getUsername());
        serverRequest.sendJsonArrToServer("getListings", url, new JSONArray(), "GET");
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
        if(tag.equals("getListings")){
            if(response.length() < 1){
                v.noListings();
            }else {
                v.hasListings();
            }
            v.clearListings();
            s.clearListings();
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Listing l;
                    l = objectMapper.readValue(jsonObject.toString(), Listing.class);
                    if(!s.getListings().contains(l) && l != null){
                        s.addListing(l);
                        v.addListing(l);
                    }
                } catch (JSONException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            v.updateListings();
            MainApplication.setActiveShelter(s);
            v.progressComplete();
        }
    }

    @Override
    public void onErrorArray(String tag, String response) {
        System.out.println(response);
    }
}
