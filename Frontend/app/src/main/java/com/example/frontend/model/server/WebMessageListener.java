package com.example.frontend.model.server;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.user.User;
import com.example.frontend.view.chatScreens.IChatPageView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.chrono.MinguoChronology;

public class WebMessageListener implements IWebSocketListener, IWebMessageListener {

    private IChatPageView v;
    private final String tag = "WebMessageListener";

    public WebMessageListener(IChatPageView v){
        this.v = v;
        MainApplication.addWebListener(this);
    }

    @Override
    public void onMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WebSocketMessage webMessage = mapper.readValue(message, WebSocketMessage.class);
            if(webMessage.getTag().equals("gotChatHistory")){
                gotChatHistory(webMessage.getArgs());
            }else if(webMessage.getTag().equals("gotMessageUser")){
                gotMessageUser(webMessage.getArgs());
            }else if(webMessage.getTag().equals("gotDeleteMessage")){
                gotDeleteMessage();
            }else if(webMessage.getTag().equals("gotDeleteAllMessages")){
                gotDeleteAllMessages();
            }else if(webMessage.getTag().equals("gotBroadcast")){
                gotBroadcast(webMessage.getArgs());
            }else if(webMessage.getTag().equals("isUnMatched")){
                isUnMatched();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void isUnMatched() {
        v.close();
    }

    private void gotMessageUser(String args) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonMessage = new JSONObject(args);
            Message message = mapper.readValue(jsonMessage.toString(), Message.class);
            v.addMessage(message);
            System.out.println("gotMessageUser: "+jsonMessage.toString());
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void gotBroadcast(String data) {
    }

    private void gotChatHistory(String data) {
        try {
            JSONArray array = new JSONArray(data);
            v.clearMessages();
            for(int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                ObjectMapper mapper = new ObjectMapper();
                Message m = mapper.readValue(object.toString(), Message.class);
                v.addMessage(m);
            }

        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void gotDeleteMessage(){
        v.reloadMessages();
    }

    private void gotDeleteAllMessages(){
        Message m = new Message("Server", MainApplication.getActiveUser().getUsername(), "");
        m.setEnd(true);
        v.addMessage(m);
        //v.reloadMessages();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        v.close();
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void getChatHistory(int listingId) {
        if(MainApplication.cc.getReadyState().name().equals("OPEN")) {
            try {
                User u = MainApplication.getActiveUser();

                ObjectMapper webMessageMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage(u.getUsername(), "getChatHistory", String.valueOf(listingId));
                String webData = webMessageMapper.writeValueAsString(webMessage);

                MainApplication.cc.send(webData);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void broadcast(String msg) {
        if(MainApplication.cc.getReadyState().name().equals("OPEN")) {
            try {
                User u = MainApplication.getActiveUser();

                ObjectMapper webMessageMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage(u.getUsername(), "broadcast", msg);
                String webData = webMessageMapper.writeValueAsString(webMessage);

                MainApplication.cc.send(webData);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void messageUser(String username, String msg, int matchId) {
        if(MainApplication.cc.getReadyState().name().equals("OPEN")) {
            try {
                User u = MainApplication.getActiveUser();

                ObjectMapper messageMapper = new ObjectMapper();
                Message m = new Message(u.getUsername(), username, msg);
                m.setMatchId(matchId);

                String messageData = messageMapper.writeValueAsString(m);

                JSONObject jsonMessageData = new JSONObject(messageData);
                jsonMessageData.remove("id");

                ObjectMapper webMessageMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage(u.getUsername(), "messageUser", jsonMessageData.toString());
                String webData = webMessageMapper.writeValueAsString(webMessage);

                MainApplication.cc.send(webData);

            } catch (JsonProcessingException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteMessage(int messageId) {
        if(MainApplication.cc.getReadyState().name().equals("OPEN")) {
            try {
                User u = MainApplication.getActiveUser();

                ObjectMapper webMessageMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage(u.getUsername(), "deleteMessage", String.valueOf(messageId));
                String webData = webMessageMapper.writeValueAsString(webMessage);

                MainApplication.cc.send(webData);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAllMessages(int matchId) {
        if(MainApplication.cc.getReadyState().name().equals("OPEN")) {
            try {
                User u = MainApplication.getActiveUser();

                ObjectMapper webMessageMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage(u.getUsername(), "deleteAllMessages", String.valueOf(matchId));
                String webData = webMessageMapper.writeValueAsString(webMessage);

                MainApplication.cc.send(webData);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
