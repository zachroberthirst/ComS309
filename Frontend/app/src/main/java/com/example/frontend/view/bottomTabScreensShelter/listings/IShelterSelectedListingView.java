package com.example.frontend.view.bottomTabScreensShelter.listings;

import com.example.frontend.model.user.UserMessageCard;


public interface IShelterSelectedListingView {
    void addCard(UserMessageCard card);
    void updateCards();
    void clearCards();
    void updateMatchCount();
    void progressComplete();
    void noMatches();
    void hasMatches();
    void close();
}
