package com.example.androidstudiotesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] count = {0};
        if(getIntent().hasExtra("count")){
            Bundle bundle = getIntent().getExtras();
            count[0] = bundle.getInt("count");
        }

        Button buttonToCounter = findViewById(R.id.activity_main_button_to_counter); //Link button obj with button created in Layout


        buttonToCounter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //Logic for button
                Intent intent = new Intent(v.getContext(), Counter.class);
                intent.putExtra("count", count[0]);
                startActivity(intent);
            }
        });
    }
}