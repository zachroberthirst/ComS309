package com.example.frontend.view.logInScreen;

import com.example.frontend.model.user.User;

public interface ILogInView {
    /**
     * Set log in errors.
     * @param error error message to be displayed
     */
    void setErrors(String error);

    /**
     * Get password from input.
     * @return password that the actor entered
     */
    String getPassword();

    /**
     * goto next page if user
     */
    void nextPage();

    /**
     * goto next page if shelter
     */
    void shelterNextPage();
}
