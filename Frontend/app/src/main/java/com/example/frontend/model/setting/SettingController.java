package com.example.frontend.model.setting;

import com.example.frontend.model.MainApplication;

public class SettingController {
    public static String getSettingUrl(){ return MainApplication.baseUrl+ "setting/"; }
    public static String getSettingUrl(int id){ return MainApplication.baseUrl +"setting/"+id; }
    public static String getSettingUrl(String username){ return MainApplication.baseUrl +"setting/username/"+username; }
}
