package com.example.frontend.presenter.shelterListings;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterEditListingView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ShelterEditListingPresenter implements IVolleyListener, IShelterEditListingPresenter {
    private IShelterEditListingView v;
    private IServerRequest serverRequest;

    public ShelterEditListingPresenter(IShelterEditListingView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("editListing")){
            v.success();
        }else if(tag.equals("getListing")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Listing l = mapper.readValue(response.toString(), Listing.class);
                v.gotListing(l);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

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
    public void editListing(Listing l) {
        String url = ListingController.getListingUrl(l.getId());
        ObjectMapper mapper = new ObjectMapper();
        JSONObject postData = null;
        try {
            postData = new JSONObject(mapper.writeValueAsString(l));
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        serverRequest.sendJsonToServer("editListing", url, postData, "PUT");
    }

    @Override
    public void getListing(int listingId) {
        String url = ListingController.getListingUrl(listingId);
        serverRequest.sendJsonToServer("getListing", url, new JSONObject(), "GET");
    }

}
