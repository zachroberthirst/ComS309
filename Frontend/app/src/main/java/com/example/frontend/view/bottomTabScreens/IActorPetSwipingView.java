package com.example.frontend.view.bottomTabScreens;

import com.example.frontend.model.listing.Listing;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public interface IActorPetSwipingView {
    void progressStart();
    void progressEnd();
    void addListing(Listing listing);
    Listing getCurrentListing();
    ArrayDeque<Listing> getQueue();
    void noPetsFound();
    void PetsFound();
    boolean contains(Listing listing);
    void toast(String msg);
}
