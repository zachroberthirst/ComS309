package com.example.frontend.presenter.messaging;
/**
 * Message presenter to connect view to server requests
 */
public interface IMessagingPresenter {
    /**
     * Sends Json Array request to server, to get all matches from active user
     */
    void getMatches();

    /**
     * Sends Json Array request to server, to update matches from active user
     */
    void updateMatches();
}
