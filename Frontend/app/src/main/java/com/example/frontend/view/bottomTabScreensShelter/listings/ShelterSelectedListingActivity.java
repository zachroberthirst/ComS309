package com.example.frontend.view.bottomTabScreensShelter.listings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.model.helpers.MessageCardRecycleAdapter;
import com.example.frontend.model.listing.ListingController;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.model.user.UserMessageCard;
import com.example.frontend.presenter.shelterListings.IShelterListingMatchesPresenter;
import com.example.frontend.presenter.shelterListings.ShelterListingMatchesPresenter;

import com.example.frontend.view.chatScreens.ChatPageActivity;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Locale;

public class ShelterSelectedListingActivity extends AppCompatActivity implements  IShelterSelectedListingView, MessageCardRecycleAdapter.OnNoteListener {

    private ArrayList<UserMessageCard> userMessageCards;
    private RecyclerView recyclerViewCards;
    private RecyclerView.Adapter userMessageAdapter;
    private IShelterListingMatchesPresenter presenter;
    private int listingId;
    private TextView matchCount, noMatches;
    private ProgressBar pageProgress;
    private Button editListing;
    private Button deleteListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_selected_listing);
        if(getIntent().hasExtra("listingId")){
            Bundle bundle = getIntent().getExtras();
            listingId =  bundle.getInt("listingId");
        }
        initAdapter();
        initPageProgress();
        matchCount = findViewById(R.id.card_pet_matches_total);
        noMatches = findViewById(R.id.no_matches_text);
        noMatches.setVisibility(View.GONE);

        ServerRequest serverRequest = new ServerRequest();
        presenter = new ShelterListingMatchesPresenter(this, serverRequest);

        //presenter.getListingMatches(listingId);

        editListing = findViewById(R.id.edit_listing);
        editListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShelterEditListingActivity.class);
                intent.putExtra("listingId", listingId);
                startActivity(intent);
            }

        });
        deleteListing = findViewById(R.id.delete_listing);

        deleteListing.setOnClickListener(view -> {
            presenter.deleteListing(listingId);
        });

    }
    private void initPageProgress(){
        pageProgress = findViewById(R.id.page_progress);
        pageProgress.setVisibility(View.VISIBLE);
        recyclerViewCards.setVisibility(View.GONE);
    }
    private void initAdapter(){
        recyclerViewCards = findViewById(R.id.recycle_view);
        userMessageCards = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCards.setLayoutManager(layoutManager);

        userMessageAdapter = new MessageCardRecycleAdapter(userMessageCards, getApplicationContext(), this);
        recyclerViewCards.setAdapter(userMessageAdapter);
    }

    @Override
    public void OnNoteClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ChatPageActivity.class);
        intent.putExtra("matchId", userMessageCards.get(position).getMatch().getId());
        intent.putExtra("matchUsername",userMessageCards.get(position).getMatch().getUser().getUsername());
        startActivity(intent);
    }
    @Override
    public void addCard(UserMessageCard card) {
        userMessageCards.add(card);
    }
    @Override
    public void clearCards() {
        userMessageCards.clear();
    }

    @Override
    public void updateMatchCount() {
        matchCount.setText(String.format(Locale.getDefault(),"%d",userMessageCards.size()));
    }

    @Override
    public void progressComplete() {
        pageProgress.setVisibility(View.GONE);
        recyclerViewCards.setVisibility(View.VISIBLE);
    }

    @Override
    public void noMatches() {

        noMatches.setVisibility(View.VISIBLE);
    }

    @Override
    public void hasMatches() {
        noMatches.setVisibility(View.GONE);
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void updateCards() {
        userMessageCards.sort(UserMessageCard.userMessageCardComparator);
        recyclerViewCards.setAdapter(userMessageAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        presenter.getListingMatches(listingId);
    }
}