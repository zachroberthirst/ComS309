package com.example.frontend.view.registrationScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.frontend.R;
import com.example.frontend.view.registrationScreen.shelterRegistrationScreen.ShelterRegistrationActivity;
import com.example.frontend.view.registrationScreen.userRegistrationScreen.AccountRegistrationPageActivity;

public class TypeOfActorActivity extends AppCompatActivity {
    private TextView userRegistrationText;
    private TextView shelterRegistrationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_actor);

        userRegistrationText = findViewById(R.id.userRegistration);
        shelterRegistrationText = findViewById(R.id.shelterRegistration);

        userRegistrationText.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AccountRegistrationPageActivity.class);
            //finish is used to close current activity
            finish();
            startActivity(intent);
        });

        shelterRegistrationText.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ShelterRegistrationActivity.class);
            //finish is used to close current activity
            finish();
            startActivity(intent);
        });

    }
}