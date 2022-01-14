package com.example.frontend.presenter.logIn;
/**
 * Log in presenter to connect view to server requests
 */
public interface ILogInPresenter {
    /**
     * Sends Json request to server, to get user from database.
     * Ex: logIn(JohnDoe);
     * @param username username of user to be retrieved
     */
    void logIn(String username);
}
