package com.example.frontend.view.bottomTabScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.frontend.R;
import com.example.frontend.model.MainApplication;
import com.example.frontend.model.listing.Listing;
import com.example.frontend.model.server.IServerRequest;
import com.example.frontend.model.server.ServerRequest;
import com.example.frontend.presenter.actorSwiping.IPetSwipingPresenter;
import com.example.frontend.presenter.actorSwiping.PetSwipingPresenter;
import com.example.frontend.view.logInScreen.LogInPageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

public class PetSwipingPageActivity extends TabSwitchingActivity implements IActorPetSwipingView {

    private RelativeLayout dislike, adorable, like;
    private FragmentContainerView fragmentContainer;
    private TextView noPetsFound;
    private ProgressBar pageProgress;
    private static ArrayDeque<Listing> listingQueue;
    private IPetSwipingPresenter presenter;
    private Listing currentListing;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private final int queueSize = 12;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_swiping_page);
        initBottomNav();
        initFragments();
        initView();

        listingQueue = new ArrayDeque<>(queueSize);

        IServerRequest serverRequest = new ServerRequest();
        presenter = new PetSwipingPresenter(this, serverRequest);
        presenter.getMatches();
        presenter.getListings(queueSize /2);


    }

    private void initFragments(){
        fragmentManager = PetSwipingPageActivity.this.getSupportFragmentManager();
    }

    private void initView(){
        dislike = findViewById(R.id.dislike_layout);
        adorable = findViewById(R.id.adorable_layout);
        like = findViewById(R.id.like_layout);
        pageProgress = findViewById(R.id.page_progress);
        noPetsFound = findViewById(R.id.no_pets_text);
        fragmentContainer = findViewById(R.id.fragmentContainerView);
        noPetsFound.setVisibility(View.GONE);

        dislike.setOnClickListener(view -> {
            nextListing();
        });

        adorable.setOnClickListener(view -> {
            if(currentListing != null) {
                presenter.newMatch(currentListing, true);
                nextListing();
            }
        });

        like.setOnClickListener(view -> {
            if(currentListing != null) {
                presenter.newMatch(currentListing, false);
                nextListing();
            }
        });
    }
    private void initBottomNav(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);
    }

    private void displayCurrentListing(){
        SwipingListingFragment fragment = new SwipingListingFragment();

        Bundle bundle = new Bundle();
        if(currentListing != null) {
            if(currentListing.getPetName() != null){
                bundle.putString("name", currentListing.getPetName());
            }
            if(currentListing.getDescription() != null){
                bundle.putString("description", currentListing.getDescription());
            }
            bundle.putInt("age", currentListing.getPetAge());
            if(currentListing.getPetType() != null){
                bundle.putString("type", currentListing.getPetType().toString());
            }
            if(currentListing.getPictureURL() != null){
                bundle.putStringArrayList("images", new ArrayList<>(currentListing.getPictureURL()));
            }


            fragment.setArguments(bundle);

            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainerView, fragment, "listingFragment");
            transaction.setReorderingAllowed(true);
            transaction.commit();
            pageProgress.setVisibility(View.GONE);
        }

    }

    private void nextListing(){
        pageProgress.setVisibility(View.VISIBLE);
        if(listingQueue.isEmpty()){
            presenter.getListings(queueSize/2);
        }else {
            currentListing = listingQueue.remove();
            displayCurrentListing();
            if(listingQueue.size() < (queueSize / 2)){
                presenter.getListings(queueSize / 2);
            }
        }
    }

    @Override
    public void onBackPressed ()
    {
        Intent intent = new Intent(PetSwipingPageActivity.this, LogInPageActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void progressStart() {
        pageProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void progressEnd() {
        pageProgress.setVisibility(View.GONE);
    }

    @Override
    public void addListing(Listing listing) {
        listingQueue.add(listing);
        if(listingQueue.isEmpty() || currentListing == null){
            nextListing();
        }
    }


    @Override
    public Listing getCurrentListing() {
        return this.currentListing;
    }

    @Override
    public ArrayDeque<Listing> getQueue() {
        return listingQueue;
    }

    @Override
    public void noPetsFound() {
        noPetsFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void PetsFound() {
        noPetsFound.setVisibility(View.GONE);
    }

    @Override
    public boolean contains(Listing listing) {
        return listingQueue.contains(listing);
    }
    @Override
    public void toast(String msg) {
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }
}