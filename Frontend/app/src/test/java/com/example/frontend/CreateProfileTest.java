package com.example.frontend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.helpers.HashPassword;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.model.user.UserType;
import com.example.frontend.presenter.logIn.LogInPresenter;
import com.example.frontend.screens.CreateProfileActivity;
import com.example.frontend.view.logInScreen.ILogInView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.times;

public class CreateProfileTest {
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    CreateProfileActivity mock;

    @Test
    public void testCreate(){
        String username = "user1";
        String tagline = "tagline";
        String bio = "This is a bio";
        String image = "...";
        User user = new User();
        String postURL = "http://coms-309-008.cs.iastate.edu:8080/profiles";
        Profile p = new Profile(user, tagline, bio, image);
        mock.uploadProfile(p);
        verify(mock, times(1)).uploadProfile(p);

    }
}
