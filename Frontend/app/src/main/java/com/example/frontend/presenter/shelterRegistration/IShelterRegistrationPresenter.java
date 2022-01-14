package com.example.frontend.presenter.shelterRegistration;

import com.example.frontend.model.shelter.Shelter;
import com.example.frontend.model.user.User;
/**
 * Shelter registration presenter to connect view to server requests
 */
public interface IShelterRegistrationPresenter {
    /**
     * Sends two Json request to server, to create a new user and new shelter
     * @param shelter shelter to be added to the database
     */
    void sendShelter(Shelter shelter);

    /**
     * Sends String request to server, to find if the database has user
     * from given username
     * @param username username to check
     */
    void hasShelter(String username);
    //void getShelter(String username);
}
