package com.example.frontend.presenter.chat;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.match.MatchController;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.UserController;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.chatScreens.ChatPageActivity;
import com.example.frontend.view.chatScreens.IChatPageView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.WebSocket;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChatPresenter implements IVolleyListener, IChatPresenter {

    private IChatPageView v;
    private IServerRequest serverRequest;

    public ChatPresenter(IChatPageView v,  IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("deleteMatch")){
            v.close();
        }else if(tag.equals("getMatch")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Match m = mapper.readValue(response.toString(), Match.class);
                if(m == null){
                    v.close();
                }
                v.gotMatch(m);
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
    public void deleteMatch(int id) {
        String url = MatchController.getMatchUrl(id);
        serverRequest.sendJsonToServer("deleteMatch",url, null, "DELETE");
    }

    @Override
    public void openSocket() {
        //System.out.println(MainApplication.cc.getReadyState());
        if(!MainApplication.cc.getReadyState().name().equals("OPEN")){
            System.out.println("________Try socket______");
            MainApplication.initWebSocket();
            v.toast("Can't connect to Server");
            v.close();
        }
    }

    @Override
    public void getMatch(int matchID) {
        String url = MatchController.getMatchUrl(matchID);
        serverRequest.sendJsonToServer("getMatch",url, new JSONObject(), "GET");
    }
}
