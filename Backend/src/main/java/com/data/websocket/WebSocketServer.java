package com.data.websocket;

import com.data.match.Match;
import com.data.match.MatchRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
@ServerEndpoint("/chat/{username}")
public class WebSocketServer {

    private static Map <Session, String> sessionUsernameMap = new Hashtable< >();
    private static Map <String, Session> usernameSessionMap = new Hashtable<>();

    private static WebSocketRepository msgRepo;
    private static MatchRepository matchRepository;

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    @Autowired
    public void setMessageRepo(WebSocketRepository repo){
        msgRepo = repo;
    }

    @Autowired
    public void setMatchRepository(MatchRepository repo){
        matchRepository = repo;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username){
        logger.info("User "+username+" has entered Open");

        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
    }

    @OnClose
    public void onClose(Session session){
        logger.info("User disconnecting");

        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        logger.info("User " + username + " has disconnected");
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        if(!throwable.getClass().getSimpleName().equals("EOFExceptiomn")){
            logger.debug("An error has occured");
            throwable.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, String message){
        logger.info("Message Recieved: " + message);
        ObjectMapper mapper = new ObjectMapper();
        try{
            WebSocketMessage webMessage = mapper.readValue(message, WebSocketMessage.class);
            if(webMessage.getTag().equals("messageUser")){
                messageUser(session, webMessage.getArgs());

            }else if(webMessage.getTag().equals("getChatHistory")){
                getChatHistory(session, webMessage.getArgs());

            }else if(webMessage.getTag().equals("broadcast")){
                broadcast(webMessage.getArgs());
            }else if(webMessage.getTag().equals("deleteMessage")){
                deleteMessage(webMessage.getArgs());
            }else if(webMessage.getTag().equals("deleteAllMessages")){
                deleteAllMessages(webMessage.getSender(),webMessage.getArgs());
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void deleteAllMessages(String senderUsername, String data) {
        int matchId = Integer.parseInt(data);
        Match m = matchRepository.findById(matchId);
        if(m == null){
            return;
        }

        ObjectMapper webMapper = new ObjectMapper();
        try {
            WebSocketMessage webMessage = new WebSocketMessage("Server", "gotDeleteAllMessages", "");
            String serverResponse = new JSONObject(webMapper.writeValueAsString(webMessage)).toString();
            if(usernameSessionMap.containsKey(m.getUser().getUsername())){
                String username = m.getUser().getUsername();
                usernameSessionMap.get(username).getBasicRemote().sendText(serverResponse);
            }
            if(usernameSessionMap.containsKey(m.getListing().getShelter().getUser().getUsername())){
                String username = m.getListing().getShelter().getUser().getUsername();
                usernameSessionMap.get(username).getBasicRemote().sendText(serverResponse);
            }
            msgRepo.deleteAllByMatchId(matchId);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Session session, String message){
        try {
            session.getBasicRemote().sendText(message);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void directMessage(String receiverUsername, String message){
        try {
            if(usernameSessionMap.containsKey(receiverUsername)) {
                ObjectMapper webMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage("Server", "gotMessageUser", message);
                usernameSessionMap.get(receiverUsername).getBasicRemote().sendText(new JSONObject(webMapper.writeValueAsString(webMessage)).toString());
            }
        } catch (IOException | JSONException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    private void broadcast(String message) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                ObjectMapper webMapper = new ObjectMapper();
                WebSocketMessage webMessage = new WebSocketMessage("Server", "gotBroadcast", message);
                session.getBasicRemote().sendText(new JSONObject(webMapper.writeValueAsString(webMessage)).toString());
            } catch(IOException | JSONException e){
                logger.info("Exception:" + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }

    private void getChatHistory(Session session, String data){
        int matchId = Integer.parseInt(data);
        List<Message> messages = msgRepo.findAllByMatchIdOrderBySentAsc(matchId);
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper webMapper = new ObjectMapper();
        try {
            JSONArray objects = new JSONArray(mapper.writeValueAsString(messages));
            WebSocketMessage webMessage = new WebSocketMessage("Server", "gotChatHistory", objects.toString());
            session.getBasicRemote().sendText(new JSONObject(webMapper.writeValueAsString(webMessage)).toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void messageUser(Session session, String data){
        ObjectMapper mapper = new ObjectMapper();
        ObjectMapper webMapper = new ObjectMapper();
        try{
            Message message = mapper.readValue(data, Message.class);
            if(matchRepository.findById(message.getMatchId()) != null) {
                Message savedMessage = msgRepo.saveAndFlush(message);
                JSONObject jsonMessage = new JSONObject(mapper.writeValueAsString(savedMessage));

                //String senderUsername = sessionUsernameMap.get(session);
                directMessage(message.getSender(), jsonMessage.toString());
                directMessage(message.getReceiver(), jsonMessage.toString());
            }else{
                WebSocketMessage webMessage = new WebSocketMessage("Server", "isUnMatched", "");
                session.getBasicRemote().sendText(new JSONObject(webMapper.writeValueAsString(webMessage)).toString());
            }
        }catch ( JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(String data){
        int messageId = Integer.parseInt(data);
        Message m = msgRepo.findById(messageId);

        ObjectMapper webMapper = new ObjectMapper();
        try {

            WebSocketMessage webMessage = new WebSocketMessage("Server", "gotDeleteMessage", "");
            String serverResponse = new JSONObject(webMapper.writeValueAsString(webMessage)).toString();

            if(usernameSessionMap.containsKey(m.getReceiver())){
                usernameSessionMap.get(m.getReceiver()).getBasicRemote().sendText(serverResponse);
            }
            if(usernameSessionMap.containsKey(m.getSender())){
                usernameSessionMap.get(m.getSender()).getBasicRemote().sendText(serverResponse);
            }
            msgRepo.deleteById(messageId);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

}
