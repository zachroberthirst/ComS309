package com.example.frontend.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontend.model.MainApplication;

public abstract class SafeCloseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if(MainApplication.cc != null){
            MainApplication.cc.close();
        }
        super.onDestroy();
    }
}
