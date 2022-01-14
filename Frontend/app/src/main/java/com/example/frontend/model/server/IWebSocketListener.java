package com.example.frontend.model.server;

import org.java_websocket.handshake.ServerHandshake;

public interface IWebSocketListener {
    void onMessage(String message);
    void onOpen(ServerHandshake handshake);
    void onClose(int code, String reason, boolean remote);
    void onError(Exception e);
    String getTag();
}
