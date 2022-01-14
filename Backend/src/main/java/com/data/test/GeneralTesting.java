package com.data.test;

import com.data.websocket.Message;
import com.data.websocket.WebSocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GeneralTesting {
     static class SimpleSocketTesting{

          @Bean
          public WebSocketMessage webSMessage(){
               return new WebSocketMessage();
          }
          @Bean
          public WebSocketMessage getWebSocketMessage () {
               return mock(WebSocketMessage.class);
          }

     }
     @Autowired
     private WebSocketMessage wstest1;

     @Autowired
     private WebSocketMessage wRepo;

     @Test
     public void testWebsocketMessaging(){
          List<Message> l = new ArrayList<Message>();

          when(wRepo.getArgs()).thenReturn("messageUser");

     }

}
