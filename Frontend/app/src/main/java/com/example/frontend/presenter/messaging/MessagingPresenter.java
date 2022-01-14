package com.example.frontend.presenter.messaging;

import android.util.Log;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.match.MatchController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreens.messagingScreen.IMessagingView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessagingPresenter implements IVolleyListener, IMessagingPresenter {

    IMessagingView v;
    IServerRequest serverRequest;
    private User u;

    /**
     * New messaging presenter
     * @param v view of messaging activity
     * @param serverRequest server requests
     */
    public MessagingPresenter(IMessagingView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        u = MainApplication.getActiveUser();
        //System.out.println(u);
    }

    @Override
    public void getMatches() {
        String url = MatchController.getUserMatchesUrl(u.getUsername());
        //Log.d("MessagingPresenter","Getting Matches: "+ u.getUsername());
        serverRequest.sendJsonArrToServer("getMatches", url, new JSONArray(), "GET");
    }
    @Override
    public void updateMatches() {
        //TODO
        String url = MatchController.getUserMatchesUrl(u.getUsername());
        Log.d("MessagingPresenter","Getting Matches: "+ u.getUsername());
        serverRequest.sendJsonArrToServer("updateMatches", url, new JSONArray(), "GET");
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {

    }

    @Override
    public void onErrorJson(String tag, String response) {
        System.out.println(response);
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
        if(tag.equals("getMatches")) {
            u.clearMatches();
            v.clearCards();
            try {
                if(response.length() < 1){
                    v.noMatches();
                }else{
                    v.hasMatches();
                }
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = response.getJSONObject(i);
                    ObjectMapper mapper = new ObjectMapper();
                    Match newMatch = null;
                    try {
                        newMatch = mapper.readValue(jsonObject.toString(), Match.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    //Match newMatch = new Match(u, jsonObject);
                    if (newMatch != null) {
                        if (!u.getMatches().contains(newMatch) && newMatch.getListing() != null) {
                            //Log.d("MessagingPresenter", "Doesn't contain: " + u.getMatches());
                            u.addMatch(newMatch);
                            v.addCard(new UserMessageCard(null, newMatch));
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            v.updateCards();
            MainApplication.setActiveUser(u);
            v.progressComplete();
        }
    }

    @Override
    public void onErrorArray(String tag, String response) {
        System.out.println(response);
    }

}
