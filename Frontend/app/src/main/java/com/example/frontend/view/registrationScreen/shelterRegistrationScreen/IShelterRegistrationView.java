package com.example.frontend.view.registrationScreen.shelterRegistrationScreen;

public interface IShelterRegistrationView {
    /**
     * Set errors in view
     */
    void usernameTaken();

    /**
     * set errors in view null, continue checking input
     */
    void usernameFree();

    /**
     * goto next activity
     */
    void nextPage();
}
