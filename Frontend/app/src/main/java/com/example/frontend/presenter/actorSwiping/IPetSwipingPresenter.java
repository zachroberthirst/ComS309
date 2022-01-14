package com.example.frontend.presenter.actorSwiping;

import com.example.frontend.model.listing.Listing;

public interface IPetSwipingPresenter {
    void newMatch(Listing l, boolean adorable);
    void getListings(int num);
    void getMatches();
}
