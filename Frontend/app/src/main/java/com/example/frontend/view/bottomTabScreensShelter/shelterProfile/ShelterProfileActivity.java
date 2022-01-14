package com.example.frontend.view.bottomTabScreensShelter.shelterProfile;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;

import com.example.frontend.R;
import com.example.frontend.view.bottomTabScreens.actorProfile.ActorProfileFragment;
import com.example.frontend.view.bottomTabScreensShelter.ShelterTabSwitchingActivity;

public class ShelterProfileActivity extends ShelterTabSwitchingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_profile);
        setupBottomTab();
        init();

    }
    private void init(){
        ActorProfileFragment fragment = new ActorProfileFragment();
        FragmentTransaction transaction= ShelterProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.actor_layout, fragment);
        transaction.addToBackStack("ActorProfileFragment");
        transaction.commit();


        ShelterProfileFragment shelterFragment = new ShelterProfileFragment();
        FragmentTransaction s_transaction= ShelterProfileActivity.this.getSupportFragmentManager().beginTransaction();
        s_transaction.replace(R.id.shelter_layout, shelterFragment);
        s_transaction.addToBackStack("ShelterProfileFragment");
        s_transaction.commit();

    }

    private void setupBottomTab(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}