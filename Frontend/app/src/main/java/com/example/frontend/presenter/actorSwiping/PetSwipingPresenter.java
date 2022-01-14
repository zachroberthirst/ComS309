package com.example.frontend.presenter.actorSwiping;

import android.view.View;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.listing.PetType;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.match.MatchController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.setting.Setting;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreens.IActorPetSwipingView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;

public class PetSwipingPresenter implements IVolleyListener, IPetSwipingPresenter {
    private IActorPetSwipingView v;
    private IServerRequest serverRequest;
    private User u;

    public PetSwipingPresenter (IActorPetSwipingView v, IServerRequest serverRequest) {
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        u = MainApplication.getActiveUser();
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("createMatch")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Match match = mapper.readValue(response.toString(), Match.class);
                Listing l = match.getListing();
                u.addMatch(match);
                linkUserAndListingToMatch(MainApplication.getActiveUser(), l, match);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            MainApplication.setActiveUser(u);
        }else if(tag.equals("assignMatch")){
            v.toast("Successfully matched");
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
        if(tag.equals("getListings")){
            ArrayDeque<Listing> listings = v.getQueue();
            if(response.length() < 1 && listings.isEmpty()){
                v.noPetsFound();
            }else{
                v.PetsFound();
            }
            for (int i = 0; i < response.length(); i++){
                try {
                    JSONObject jsonObject = response.getJSONObject(i);
                    ObjectMapper objectMapper = new ObjectMapper();
                    Listing l;
                    l = objectMapper.readValue(jsonObject.toString(), Listing.class);
                    if(!v.contains(l) && l != null){
                        v.addListing(l);
                    }
                } catch (JSONException | JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }if(tag == "getMatches"){
            u.clearMatches();
            try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    ObjectMapper mapper = new ObjectMapper();
                    Match newMatch = null;
                    try {
                        newMatch = mapper.readValue(jsonObject.toString(), Match.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if (newMatch != null) {
                        if (!u.getMatches().contains(newMatch) && newMatch.getListing() != null) {
                            u.addMatch(newMatch);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainApplication.setActiveUser(u);
        }

    }

    @Override
    public void onErrorArray(String tag, String response) {

    }

    @Override
    public void newMatch(Listing l, boolean adorable) {
        Match m = new Match();
        m.setAdorableAction(adorable);
        m.setListing(l);
        m.setUser(u);
        if(!u.getMatches().contains(m)) {
            String url = MatchController.createMatchUrl();
            ObjectMapper mapper = new ObjectMapper();
            JSONObject postData = null;
            try {
                postData = new JSONObject(mapper.writeValueAsString(m));
                postData.remove("id");
            } catch (JSONException | JsonProcessingException e) {
                e.printStackTrace();
            }
            serverRequest.sendJsonToServer("createMatch", url, postData, "POST");
        }
    }

    @Override
    public void getListings(int num) {
        Setting s = MainApplication.getActiveUser().getSetting();
        /*
            Setting s = new Setting();
            //s.addPetPreference(PetType.DOG);
            s.addPetPreference(PetType.DOG);
            s.addPetPreference(PetType.FISH);

         */
        if(s == null){
            String url = ListingController.getListingUrl();
            serverRequest.sendJsonArrToServer("getListings", url,  new JSONArray(), "GET");
        }else{
            if(s.getPetPreferences().isEmpty()){
                String url = ListingController.getListingUrl();
                serverRequest.sendJsonArrToServer("getListings", url,  new JSONArray(), "GET");
            }else{
                String url = ListingController.getListingByPreferencesUrl(num);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    JSONArray jsonArray = new JSONArray(mapper.writeValueAsString(s.getPetPreferences()));
                    serverRequest.sendJsonArrToServer("getListings", url,  jsonArray, "PUT");
                } catch (JSONException | JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void getMatches() {
        String url = MatchController.getUserMatchesUrl(u.getUsername());
        serverRequest.sendJsonArrToServer("getMatches", url, new JSONArray(), "GET");
    }

    private void linkUserAndListingToMatch(User u, Listing l, Match m){
        String url = MatchController.assignUserAndListingToMatch(u.getId(), l.getId(), m.getId());
        serverRequest.sendJsonToServer("assignMatch", url,  new JSONObject(), "PUT");
    }
}
