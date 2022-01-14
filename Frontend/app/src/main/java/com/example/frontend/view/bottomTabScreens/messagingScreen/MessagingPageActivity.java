package com.example.frontend.view.bottomTabScreens.messagingScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.model.helpers.MessageCardRecycleAdapter;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.presenter.messaging.IMessagingPresenter;
import com.example.frontend.presenter.messaging.MessagingPresenter;
import com.example.frontend.view.chatScreens.ChatPageActivity;
import com.example.frontend.view.bottomTabScreens.*;

import java.util.ArrayList;

public class MessagingPageActivity extends TabSwitchingActivity implements MessageCardRecycleAdapter.OnNoteListener, IMessagingView {
    private ArrayList<UserMessageCard> messageCards;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private IMessagingPresenter presenter;
    private ProgressBar pageProgress;
    private TextView noMatches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_page);

        Log.d("MessagingPageActivity","Create messaging Activity");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        noMatches = findViewById(R.id.no_matches_text);
        noMatches.setVisibility(View.GONE);

        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);

        recyclerView = findViewById(R.id.recycle_view);
        messageCards = new ArrayList<>();

        initPageProgress();


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MessageCardRecycleAdapter(messageCards, getApplicationContext(), this);
        recyclerView.setAdapter(mAdapter);

        ServerRequest serverRequest = new ServerRequest();
        presenter = new MessagingPresenter(this, serverRequest);


        //presenter.getMatches();

    }
    private void initPageProgress(){
        pageProgress = findViewById(R.id.page_progress);
        pageProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMatches();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void OnNoteClick(int position) {
        Log.d("MessagingPageActivity","Clicked Message Card: " + position);
        Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
        intent.putExtra("matchId", messageCards.get(position).getMatch().getId());
        intent.putExtra("matchUsername",messageCards.get(position).getMatch().getListing().getShelter().getUser().getUsername());
        startActivity(intent);
    }

    @Override
    public void updateCards() {
        messageCards.sort(UserMessageCard.userMessageCardComparator);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void addCard(UserMessageCard userMessageCard) {
        messageCards.add(userMessageCard);
    }

    @Override
    public void progressComplete() {
        pageProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void clearCards() {
        messageCards.clear();
    }

    @Override
    public void noMatches() {
        noMatches.setVisibility(View.VISIBLE);
    }

    @Override
    public void hasMatches() {
        noMatches.setVisibility(View.GONE);
    }
}