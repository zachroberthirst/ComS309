package com.example.frontend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.presenter.actorProfile.ActorEditProfilePresenter;
import com.example.frontend.view.bottomTabScreens.actorProfile.IActorEditProfileView;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ActorEditProfilePresenterTest {
    @Rule
    public MockitoRule mokitoRule = MockitoJUnit.rule();

    @Mock
    IActorEditProfileView view;
    @Mock
    IServerRequest serverRequest;

    @Test
    public void updateProfileTest(){
        ActorEditProfilePresenter SUT = new ActorEditProfilePresenter(view, serverRequest);
        SUT.updateProfile(new Profile());
        verify(serverRequest, atMostOnce()).sendJsonToServer(eq("updateProfile"),anyString(),any(JSONObject.class), eq("PUT"));
    }

    @Test
    public void gotProfileTest(){
        ActorEditProfilePresenter SUT = new ActorEditProfilePresenter(view, serverRequest);
        SUT.onSuccessJson("getProfile", new JSONObject());
        verify(view, atMostOnce()).gotProfile(any(Profile.class));
    }

    @Test
    public void gotUpdatedProfileTest(){
        ActorEditProfilePresenter SUT = new ActorEditProfilePresenter(view, serverRequest);
        SUT.onSuccessArray("updateProfile", null);
        verify(view, atMostOnce()).close();
    }

}
