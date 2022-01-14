package com.example.frontend;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.profile.Profile;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.presenter.shelterListings.ShelterAddListingPresenter;
import com.example.frontend.presenter.shelterListings.ShelterEditListingPresenter;
import com.example.frontend.view.bottomTabScreens.actorProfile.IActorEditProfileView;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterEditListingView;
import com.example.frontend.view.bottomTabScreensShelter.listings.IShelterNewListingVIew;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class ShelterAddListingPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    IShelterNewListingVIew view;
    @Mock
    IServerRequest serverRequest;


    @Test
    public void AddListingTest(){
        ShelterAddListingPresenter SUT = new ShelterAddListingPresenter(view, serverRequest);
        SUT.uploadListing(new Listing());
        verify(serverRequest, atMostOnce()).sendJsonToServer(eq("uploadListing"),anyString(),any(JSONObject.class), eq("POST"));
    }

    @Test
    public void gotEditListingTest(){
        ShelterAddListingPresenter SUT = new ShelterAddListingPresenter(view, serverRequest);
        SUT.onSuccessJson("uploadListing", new JSONObject());
        verify(view, atMostOnce()).success();
    }

}
