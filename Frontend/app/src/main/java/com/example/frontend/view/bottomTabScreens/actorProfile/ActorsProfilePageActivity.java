package com.example.frontend.view.bottomTabScreens.actorProfile;


import android.os.Bundle;
import android.view.Menu;

import androidx.fragment.app.FragmentTransaction;

import com.example.frontend.R;
import com.example.frontend.view.bottomTabScreens.TabSwitchingActivity;


public class ActorsProfilePageActivity extends TabSwitchingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors_profile_page);

        System.out.println("Create profile");

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        Menu menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        setNavSwitcher(bottomNavigationView);
        init();

    }
    private void init(){
        ActorProfileFragment fragment = new ActorProfileFragment();
        FragmentTransaction transaction= ActorsProfilePageActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.addToBackStack("ProfileFragment");
        transaction.commit();

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

}