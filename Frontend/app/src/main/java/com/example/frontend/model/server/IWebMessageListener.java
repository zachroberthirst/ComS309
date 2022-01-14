package com.example.frontend.model.server;

public interface IWebMessageListener {
    void getChatHistory(int listingId);
    void broadcast(String msg);
    void messageUser(String username, String msg, int matchId);
    void deleteMessage(int messageId);
    void deleteAllMessages(int matchId);
}
