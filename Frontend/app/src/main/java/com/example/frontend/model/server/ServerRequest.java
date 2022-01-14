package com.example.frontend.model.server;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.frontend.model.MainApplication;
import com.example.frontend.presenter.IVolleyListener;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServerRequest implements IServerRequest{
    private IVolleyListener l;

    @Override
    public void sendJsonToServer(String tag, String url, JSONObject newUserObj, String methodType) {
        int method = Request.Method.GET;

        if(methodType.equals("DELETE")) {
            method = Request.Method.DELETE;
        }
        if (methodType.equals("POST")) {
            method = Request.Method.POST;
        }else if(methodType.equals("PUT")){
            method = Request.Method.PUT;
        }else if(methodType.equals("DELETE")) {
            method = Request.Method.DELETE;
        }
        JsonObjectRequest registerUserRequest = new JsonObjectRequest(method, url, newUserObj,

                response -> {

                    if (response != null ) {
                        l.onSuccessJson(tag, response);
                        Log.d("ServerRequest","Json Response: "+response.toString());
                    } else {
                        l.onErrorJson(tag, "Null Response object received");
                    }
                },

                error -> l.onErrorJson(tag, error.getMessage())
        );

        MainApplication.getInstance().addToRequestQueue(registerUserRequest, tag);

    }

    @Override
    public void sendStringToServer(String tag, String url, String methodType) {
        int method = Request.Method.GET;

        if (methodType.equals("POST")) {
            method = Request.Method.POST;
        }else if(methodType.equals("PUT")){
            method = Request.Method.PUT;
        }else if(methodType.equals("DELETE")) {
            method = Request.Method.DELETE;
        }
        StringRequest registerUserRequest = new StringRequest(method, url,
                response -> {
                    if (response != null ) {
                        l.onSuccessString(tag, response);
                        Log.d("ServerRequest","String Response: "+response);
                    } else {
                        l.onErrorString(tag, "Null Response object received");
                    }
                },

                error -> l.onErrorString(tag, error.getMessage())
        );

        MainApplication.getInstance().addToRequestQueue(registerUserRequest, tag);
    }

    @Override
    public void sendJsonArrToServer(String tag, String url, JSONArray jsonArray, String methodType) {
        int method = Request.Method.GET;

        if (methodType.equals("POST")) {
            method = Request.Method.POST;
        }else if(methodType.equals("PUT")){
            method = Request.Method.PUT;
        }else if(methodType.equals("DELETE")) {
            method = Request.Method.DELETE;
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url, jsonArray,
            response -> {
                if (response != null ) {
                    l.onSuccessArray(tag, response);
                    Log.d("ServerRequest","Array Response: "+response.toString());
                } else {
                    l.onErrorArray(tag, "Null Response object received");
                }
            },

                    error -> l.onErrorArray(tag, error.getMessage())
        );
        MainApplication.getInstance().addToRequestQueue(jsonArrayRequest, tag);
    }

    @Override
    public void addVolleyListener(IVolleyListener logic) {
        l = logic;
    }
}
