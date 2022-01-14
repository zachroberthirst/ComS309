package com.example.frontend.view.chatScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frontend.R;
import com.example.frontend.model.helpers.ChatMessageRecycleAdapter;
import com.example.frontend.model.helpers.CheckInput;
import com.example.frontend.model.helpers.MessageCardRecycleAdapter;
import com.example.frontend.model.match.Match;
import com.example.frontend.model.server.IWebMessageListener;
import com.example.frontend.model.server.Message;
import com.example.frontend.model.server.WebMessageListener;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.user.User;
import com.example.frontend.presenter.chat.ChatPresenter;
import com.example.frontend.presenter.chat.IChatPresenter;
import com.example.frontend.screens.RecipientProfilePageActivity;
import com.example.frontend.view.SafeCloseActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ChatPageActivity extends AppCompatActivity implements IChatPageView, ChatMessageRecycleAdapter.OnNoteListener{
    private int matchID;
    private String matchUsername;
    private IWebMessageListener listener;
    private IChatPresenter presenter;
    private RecyclerView messagesRecycler;
    private TextInputLayout messageLayout;
    private Button sendButton, unMatchButton, viewProfileButton;
    private ArrayList<Message> messages;
    private TextView matchName;
    private ChatMessageRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        if(getIntent().hasExtra("matchId") && getIntent().hasExtra("matchUsername")){
            Bundle bundle = getIntent().getExtras();
            matchID =  bundle.getInt("matchId");
            matchUsername = bundle.getString("matchUsername");
        }
        ServerRequest serverRequest = new ServerRequest();
        presenter = new ChatPresenter(this, serverRequest);
        listener = new WebMessageListener(this);
        messages = new ArrayList<>();

        messagesRecycler = findViewById(R.id.messages_recycler);
        messageLayout = findViewById(R.id.message_layout);
        sendButton = findViewById(R.id.send_button);
        unMatchButton = findViewById(R.id.delete_match_button);
        viewProfileButton = findViewById(R.id.view_profile_button);
        viewProfileButton.setVisibility(View.GONE);
        matchName = findViewById(R.id.match_name);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        messagesRecycler.setLayoutManager(layoutManager);
        adapter = new ChatMessageRecycleAdapter(messages, getApplicationContext(), this);
        messagesRecycler.setAdapter(adapter);

        sendButton.setOnClickListener(view -> {
            if(checkMessage()){
                String msg = Objects.requireNonNull(messageLayout.getEditText()).getText().toString().trim();
                messageLayout.getEditText().setText(null);
                listener.messageUser(matchUsername, msg, matchID);
            }

        });


        unMatchButton.setOnClickListener(view ->{
            listener.deleteAllMessages(matchID);
            presenter.deleteMatch(matchID);
        });

        viewProfileButton.setOnClickListener(view ->{

        });

        presenter.getMatch(matchID);
        reloadMessages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.openSocket();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void reloadMessages() {
        listener.getChatHistory(matchID);
    }

    @Override
    public void addMessage(Message m) {
        messages.add(m);
        //messagesRecycler.setAdapter(adapter);
        runOnUiThread(() -> {
            messagesRecycler.setAdapter(adapter);
            messagesRecycler.smoothScrollToPosition(messages.size() - 1);
        });
    }


    @Override
    public void clearMessages() {
        messages.clear();
        //messagesRecycler.setAdapter(adapter);
        runOnUiThread(() -> {
            messagesRecycler.setAdapter(adapter);
        });
    }

    @Override
    public void gotMatch(Match m) {
        if(matchUsername.equals(m.getUser().getUsername())){
            matchName.setText(String.format("%s %s",m.getUser().getFirstName(), m.getUser().getLastName()));
        }else if(matchUsername.equals(m.getListing().getShelter().getUser().getUsername())){
            matchName.setText(String.format("%s",m.getListing().getPetName()));
        }
    }

    private boolean checkMessage(){
        String msg = Objects.requireNonNull(messageLayout.getEditText()).getText().toString().trim();
        return CheckInput.setError(CheckInput.message(msg), messageLayout);
    }

    private void goToRecipient(User u){
        Intent intent = new Intent(getApplicationContext(), RecipientProfilePageActivity.class);
        intent.putExtra("recipientUsername",u.getUsername());
        startActivity(intent);
    }

    @Override
    public void OnNoteClick(int position) {
        System.out.println("Timestamp: "+messages.get(position).getSent());
    }
}