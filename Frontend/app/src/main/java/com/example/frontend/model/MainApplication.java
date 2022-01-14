package com.example.frontend.model;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.frontend.model.server.IWebSocketListener;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.user.User;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * MainApplication to hold active user, shelter, and request queue
 */
public class MainApplication extends Application {
    public final static String baseUrl = "http://coms-309-008.cs.iastate.edu:8080/";
    //public final static String baseUrl = "http://10.65.72.219:8080/";
    public final static String webSocketUrl = "ws://coms-309-008.cs.iastate.edu:8080/chat/";
    public static final String TAG = MainApplication.class.getSimpleName();

    private RequestQueue requestQueue;
    private static MainApplication instance;
    private static User activeUser;
    private static Shelter activeShelter;
    public static WebSocketClient cc;
    private ArrayList<IWebSocketListener> webSocketListeners;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        webSocketListeners = new ArrayList<>();
    }

    public static synchronized MainApplication getInstance() {
        return instance;
    }

    /**
     * Get current server request queue.
     * @return current request queue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Add request to current request queue.
     * @param req request
     * @param tag tag of request
     * @param <T> type of request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if(TextUtils.isEmpty(tag)){
            req.setTag(TAG);
        }else{
            req.setTag(tag);
        }
        getRequestQueue().add(req);
    }

    /**
     * Add request to current request queue.
     * @param req request
     * @param <T> type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancel all request of specific tag.
     * @param tag tag of requests to be canceled
     */
    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    /**
     * Get active user.
     * @return active user
     */
    public static User getActiveUser(){
        return activeUser;
    }


    /**
     * Set active user.
     * @param user user to be set
     */
    public static void setActiveUser(User user){
        activeUser = user;
    }

    /**
     * Get active shelter.
     * @return active shelter
     */
    public static Shelter getActiveShelter(){
        return activeShelter;
    }

    /**
     * Set active shelter.
     * @param shelter shelter to be set
     */
    public static void setActiveShelter(Shelter shelter){
        activeShelter = shelter;
    }

    /**
     * Add match to active user's matches.
     * @param match match to be added
     */
    public static void addMatch(Match match){
        activeUser.addMatch(match);
    }

    public static boolean initWebSocket(){
        Draft[] drafts = {
                new Draft_6455()
        };

        String w = webSocketUrl + MainApplication.getActiveUser().getUsername();

        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    for (IWebSocketListener l: getInstance().webSocketListeners) {
                        l.onMessage(message);
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    for (IWebSocketListener l: getInstance().webSocketListeners) {
                        l.onOpen(handshake);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                    for (IWebSocketListener l: getInstance().webSocketListeners) {
                        l.onClose(code, reason, remote);
                        getInstance().webSocketListeners.remove(l);
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                    for (IWebSocketListener l: getInstance().webSocketListeners) {
                        l.onError(e);
                    }
                }
            };
        } catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
            return false;
        }
        cc.connect();
        return true;
    }

    public static void addWebListener(IWebSocketListener listener) {
        for (IWebSocketListener l :getInstance().webSocketListeners) {
            if(l.getTag().equals(listener.getTag())){
                System.out.println("Replace Web listener");
                getInstance().webSocketListeners.remove(l);
                getInstance().webSocketListeners.add(listener);
                return;
            }
        }
        System.out.println("Add Web listener");
        getInstance().webSocketListeners.add(listener);
    }
    public static void removeWebListener(IWebSocketListener listener){
        getInstance().webSocketListeners.remove(listener);
    }

}
