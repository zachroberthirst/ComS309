package com.example.androidstudiotesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class Counter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.counter);

        Button inc_Counter_button = findViewById(R.id.count_Increment); //create inc button connecting to xml button
        Button dec_Counter_button = findViewById(R.id.count_decrement); //create dec button connecting to xml button
        Button back_button = findViewById(R.id.back_button_01);         //create back button connecting to xml button
        TextView count_text = findViewById(R.id.counter_count_text);    //create text obj by referencing xml text obj

        Bundle bundle = getIntent().getExtras();
        int count_saved = bundle.getInt("count");

        Integer[] count = {count_saved};

        count_text.setText(String.format(Locale.getDefault(),"%d", count[0])); //set count text to be 0

        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //Logic for button
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("count", count[0]);
                startActivity(intent); //start MainActivity
            }
        });

        inc_Counter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic for button
                count[0]++;
                count_text.setText(String.format(Locale.getDefault(),"%d", count[0])); //set text to be updated count
            }
        });
        inc_Counter_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                count[0] +=10;
                count_text.setText(String.format(Locale.getDefault(),"%d", count[0])); //set text to be updated count
                inc_Counter_button.cancelLongPress();
                return true;
            }
        });
        dec_Counter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Logic for button
                count[0]--;
                count_text.setText(String.format(Locale.getDefault(),"%d", count[0])); //set text to be updated count
            }
        });
        dec_Counter_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                count[0] -=10;
                count_text.setText(String.format(Locale.getDefault(),"%d", count[0])); //set text to be updated count
                dec_Counter_button.cancelLongPress();
                return true;
            }
        });
    }
}