package com.example.frontend.view.chatScreens;

import com.example.frontend.model.match.Match;
import com.example.frontend.model.server.Message;

public interface IChatPageView {
    void close();
    void toast(String msg);
    void reloadMessages();
    void addMessage(Message m);
    void clearMessages();
    void gotMatch(Match m);
}
