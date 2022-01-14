package com.example.frontend.model.profile;

import com.example.frontend.model.MainApplication;

public class ProfileController {
    public static String getProfileUrl(){ return MainApplication.baseUrl+ "profiles/"; }
    public static String getProfileUrl(String username){ return MainApplication.baseUrl +"profiles/username/"+username; }
    public static String getProfileUrl(int id){ return MainApplication.baseUrl +"profiles/"+id; }
}
