package com.example.frontend.presenter.userRegistration;

import com.example.frontend.model.user.User;

/**
 * User registration presenter to connect view to server requests
 */
public interface IRegistrationPresenter {
    /**
     * Sends String request to server, to find if the database has user
     * from given username
     * @param username username to check
     */
    void hasUser(String username);

    /**
     * Sends Json request to server, to create a new user
     * @param user user to be added to the database
     */
    void sendUser(User user);
}
