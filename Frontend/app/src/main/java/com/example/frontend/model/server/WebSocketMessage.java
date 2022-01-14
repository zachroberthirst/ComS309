package com.example.frontend.model.server;

public class WebSocketMessage {
    private String sender;
    private String tag;
    private String args;

    public WebSocketMessage(){}

    public WebSocketMessage(String sender, String tag, String args){
        this.sender = sender;
        this.tag =tag;
        this.args = args;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getArgs() {
        return args;
    }
    public void setArgs(String args) {
        this.args = args;
    }
}
