package com.example.frontend.view.bottomTabScreens.actorProfile;

public interface IActorProfileFragmentView {
    void setName(String firstName, String lastName);
    void setPicture(String url);
    void setTagline(String tagline);
    void setBio(String bio);
    void setToolBar(String username);
    void noProfile();
    void progressComplete();
}
