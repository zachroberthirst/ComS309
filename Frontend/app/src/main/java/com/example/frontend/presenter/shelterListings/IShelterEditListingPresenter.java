package com.example.frontend.presenter.shelterListings;

import com.example.frontend.model.listing.Listing;

public interface IShelterEditListingPresenter {
    void editListing(Listing l);
    void getListing(int listingId);
}
