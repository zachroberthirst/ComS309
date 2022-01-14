package com.example.frontend.view.bottomTabScreens.messagingScreen;

import com.example.frontend.model.user.UserMessageCard;

public interface IMessagingView {
    /**
     * update displayed user message cards
     */
    void updateCards();

    /**
     * Add a new user message card to the list of cards
     * @param userMessageCard message card to be added
     */
    void addCard(UserMessageCard userMessageCard);
    void progressComplete();
    void clearCards();
    void noMatches();
    void hasMatches();
}
