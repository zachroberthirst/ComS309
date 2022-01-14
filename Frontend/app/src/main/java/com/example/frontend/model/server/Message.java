package com.example.frontend.model.server;

import java.util.Calendar;
import java.util.Date;

public class Message {
    private int id;
    private String sender;
    private String receiver;
    private int matchId;
    private String content;

    private Long sent;

    private boolean isEnd;

    public Message(){
        Date date = new Date();
        sent = date.getTime();
    }

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        Date date = new Date();
        sent = date.getTime();
        isEnd = false;
    }

    public int getMatchId() {
        return matchId;
    }
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
    public int getId(){return id;}
    public void setId(int id){this.id = id;}
    public String getSender() {return sender;}
    public void setSender(String sender){this.sender = sender;}
    public String getReceiver() {return receiver;}
    public void setReceiver(String receiver){this.receiver = receiver;}
    public String getContent(){return content;}
    public void setContent(String content){this.content = content;}
    public Long getSent(){return sent;}
    public void setSent(Long sent){this.sent = sent;}
    public boolean isEnd() { return isEnd; }
    public void setEnd(boolean end) { isEnd = end; }
}
