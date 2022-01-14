package com.example.frontend.presenter.shelterListings;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterNewListingVIew;
import com.example.frontend.view.logInScreen.ILogInView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShelterAddListingPresenter implements IVolleyListener, IShelterAddListingPresenter {

    private IShelterNewListingVIew v;
    private IServerRequest serverRequest;

    public ShelterAddListingPresenter(IShelterNewListingVIew v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("uploadListing")){
            v.success();

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
    public void uploadListing(Listing l) {
        String url = ListingController.getListingUrl();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject postData = null;
        try {
            postData = new JSONObject(mapper.writeValueAsString(l));
            postData.remove("id");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
        serverRequest.sendJsonToServer("uploadListing", url, postData, "POST");


    }
}
