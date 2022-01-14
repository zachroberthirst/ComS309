package com.example.frontend.presenter.actorProfile;

import com.example.frontend.model.profile.Profile;

public interface IActorEditProfilePresenter {
    void getUserProfile();
    void updateProfile(Profile profile);
}
