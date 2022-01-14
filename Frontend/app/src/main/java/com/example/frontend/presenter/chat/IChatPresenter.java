package com.example.frontend.presenter.chat;

public interface IChatPresenter {
    void deleteMatch(int id);
    void openSocket();
    void getMatch(int matchID);
}
