package com.data.websocket;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String sender;
    private String receiver;
    private int matchId;

        @NotNull
    @Lob
    private String content;
        
    private Long sent;
    private boolean isEnd;

    public Message(){}

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
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


