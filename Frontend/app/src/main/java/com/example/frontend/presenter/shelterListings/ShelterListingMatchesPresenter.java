package com.example.frontend.presenter.shelterListings;

import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.match.MatchController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterSelectedListingView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ShelterListingMatchesPresenter implements IShelterListingMatchesPresenter, IVolleyListener {


    IShelterSelectedListingView v;
    IServerRequest serverRequest;
    //private ArrayList<UserMessageCard> cards;

    public ShelterListingMatchesPresenter(IShelterSelectedListingView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }


    @Override
    public void getListingMatches(int id){
        String url = MatchController.getListingMatchesUrl(id);
        serverRequest.sendJsonArrToServer("getListingMatches",url, new JSONArray(), "GET");
    }

    @Override
    public void deleteListing(int listingId) {
        String url = ListingController.getListingUrl(listingId);
        serverRequest.sendJsonToServer("deleteListing", url, null, "DELETE");
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("deleteListing")){
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
        if(tag.equals("getListingMatches")){
            v.clearCards();
            if(response.length() < 1){
                v.noMatches();
            }else{
                v.hasMatches();
            }
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Match m;
                    m = objectMapper.readValue(jsonObject.toString(), Match.class);
                    UserMessageCard newCard = new UserMessageCard(null, m);
                    if(m != null){
                        v.addCard(newCard);
                    }
                } catch (JSONException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            v.updateCards();
            v.updateMatchCount();
            v.progressComplete();
        }
    }

    @Override
    public void onErrorArray(String tag, String response) {
        System.out.println(response);
    }
}
