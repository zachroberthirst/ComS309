package com.example.frontend.presenter.settings;

import com.example.frontend.model.MainApplication;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.setting.Setting;
import com.example.frontend.model.setting.SettingController;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserController;
import com.example.frontend.presenter.IVolleyListener;
import com.example.frontend.view.bottomTabScreens.actorProfile.ISettingsPageView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsPresenter implements IVolleyListener, ISettingsPresenter {
    private ISettingsPageView v;
    private IServerRequest serverRequest;

    private User u;

    public SettingsPresenter(ISettingsPageView v, IServerRequest serverRequest){
        this.v = v;
        this.serverRequest = serverRequest;
        serverRequest.addVolleyListener(this);
        u = MainApplication.getActiveUser();
    }

    @Override
    public void onSuccessJson(String tag, JSONObject response) {
        if(tag.equals("getSetting")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Setting s = mapper.readValue(response.toString(), Setting.class);
                u.setSetting(s);
                MainApplication.setActiveUser(u);
                if(s == null){
                    v.noSetting();
                }else{
                    v.gotSetting(s);
                }

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(tag.equals("updateSetting")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Setting s = mapper.readValue(response.toString(), Setting.class);
                u.setSetting(s);
                MainApplication.setActiveUser(u);
                if(s == null){
                    v.noSetting();
                }else{
                    v.gotSetting(s);
                }
                v.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(tag.equals("createSetting")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Setting s = mapper.readValue(response.toString(), Setting.class);
                u.setSetting(s);
                MainApplication.setActiveUser(u);
                if(s == null){
                    v.noSetting();
                }else{
                    v.gotSetting(s);
                }
                v.close();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if (tag.equals("deleteUser")){
            v.deleted();
        }
    }

    @Override
    public void onErrorJson(String tag, String response) {
        v.noSetting();
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
    public void getSetting() {
        String url = SettingController.getSettingUrl(u.getUsername());
        serverRequest.sendJsonToServer("getSetting", url, new JSONObject(), "GET");
    }

    @Override
    public void updateSetting(Setting setting) {
        String url = SettingController.getSettingUrl();
        ObjectMapper mapper = new ObjectMapper();
        try {
            serverRequest.sendJsonToServer("updateSetting", url, new JSONObject(mapper.writeValueAsString(setting)), "PUT");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createSetting(Setting setting) {
        System.out.println(setting.getPetPreferences());
        String url = SettingController.getSettingUrl();
        setting.setUser(u);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject postData = new JSONObject(mapper.writeValueAsString(setting));
            postData.remove("id");
            serverRequest.sendJsonToServer("createSetting", url, postData, "POST");
        } catch (JSONException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser() {
        String url = UserController.getUserUrl(u.getId());
        serverRequest.sendJsonToServer("deleteUser", url, new JSONObject(), "DELETE");
    }

}
