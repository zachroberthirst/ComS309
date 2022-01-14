package com.example.frontend.view.bottomTabScreensShelter.listings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.helpers.ListingCardRecycleAdapter;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.presenter.shelterListings.IShelterListingsPresenter;
import com.example.frontend.presenter.shelterListings.ShelterListingsPresenter;
import com.example.frontend.view.bottomTabScreensShelter.ShelterTabSwitchingActivity;
import com.example.frontend.view.logInScreen.LogInPageActivity;

import java.util.ArrayList;

public class ShelterListingsActivity extends ShelterTabSwitchingActivity implements IShelterListingsView, ListingCardRecycleAdapter.OnNoteListener{
    private ArrayList<Listing> listings;
    private RecyclerView recyclerViewListing;
    private RecyclerView.Adapter listingAdapter;
    private IShelterListingsPresenter presenter;
    private ProgressBar pageProgress;
    private TextView noListings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_listings_page);
        setupBottomTab();
        initAdapter();

        noListings = findViewById(R.id.no_listings_text);
        noListings.setVisibility(View.GONE);

        ServerRequest serverRequest = new ServerRequest();
        presenter = new ShelterListingsPresenter(this, serverRequest);

        initPageProgress();

        //presenter.getListings();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getListings();
    }

    private void initPageProgress(){
        pageProgress = findViewById(R.id.page_progress);
        pageProgress.setVisibility(View.VISIBLE);
        recyclerViewListing.setVisibility(View.GONE);
    }

    private void initAdapter(){
        recyclerViewListing = findViewById(R.id.recycle_view);
        listings = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewListing.setLayoutManager(layoutManager);

        listingAdapter = new ListingCardRecycleAdapter(listings, getApplicationContext(), this);
        recyclerViewListing.setAdapter(listingAdapter);
    }

    private void setupBottomTab(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);
    }

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(getApplicationContext(), LogInPageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void OnNoteClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ShelterSelectedListingActivity.class);
        intent.putExtra("listingId", listings.get(position).getId());
        startActivity(intent);
        //Log.d("ShelterListingsActivity", ""+position+": "+listings.get(position).getPetName());
    }

    @Override
    public void updateListings() {
        recyclerViewListing.setAdapter(listingAdapter);
    }

    @Override
    public void progressComplete() {
        pageProgress.setVisibility(View.GONE);
        recyclerViewListing.setVisibility(View.VISIBLE);
    }

    @Override
    public void noListings() {
        noListings.setVisibility(View.VISIBLE);
    }

    @Override
    public void hasListings() {
        noListings.setVisibility(View.GONE);
    }

    @Override
    public void clearListings() {
        listings.clear();
        updateListings();
    }

    @Override
    public void addListing(Listing listing) {
        listings.add(listing);
    }
}
