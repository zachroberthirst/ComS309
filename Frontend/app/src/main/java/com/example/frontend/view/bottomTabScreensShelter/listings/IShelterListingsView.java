package com.example.frontend.view.bottomTabScreensShelter.listings;

import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.user.UserMessageCard;

public interface IShelterListingsView {
    void addListing(Listing listing);
    void updateListings();
    void progressComplete();
    void noListings();
    void hasListings();
    void clearListings();
}
