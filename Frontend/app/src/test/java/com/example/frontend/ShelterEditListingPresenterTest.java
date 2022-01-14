package com.example.frontend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.presenter.shelterListings.ShelterEditListingPresenter;
import com.example.frontend.view.bottomTabScreens.actorProfile.IActorEditProfileView;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterEditListingView;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ShelterEditListingPresenterTest {

    @Rule
    public MockitoRule mokitoRule = MockitoJUnit.rule();

    @Mock
    IShelterEditListingView view;
    @Mock
    IServerRequest serverRequest;

    @Test
    public void editListingTest(){
        ShelterEditListingPresenter SUT = new ShelterEditListingPresenter(view, serverRequest);
        SUT.editListing(new Listing());
        verify(serverRequest, atMostOnce()).sendJsonToServer(eq("editListing"),anyString(),any(JSONObject.class), eq("PUT"));
    }

    @Test
    public void getListingTest(){
        ShelterEditListingPresenter SUT = new ShelterEditListingPresenter(view, serverRequest);
        SUT.getListing(0);
        verify(serverRequest, atMostOnce()).sendJsonToServer(eq("getListing"),anyString(),any(JSONObject.class), eq("GET"));
    }

    @Test
    public void gotEditListingTest(){
        ShelterEditListingPresenter SUT = new ShelterEditListingPresenter(view, serverRequest);
        SUT.onSuccessJson("editListing", new JSONObject());
        verify(view, atMostOnce()).success();
    }
    @Test
    public void gotListingTest(){
        ShelterEditListingPresenter SUT = new ShelterEditListingPresenter(view, serverRequest);
        SUT.onSuccessJson("getListing", new JSONObject());
        verify(view, atMostOnce()).gotListing(any(Listing.class));
    }
}
