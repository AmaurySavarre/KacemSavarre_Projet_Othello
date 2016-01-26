package com.example.utilisateur.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onPlay(View v)
    {
        Intent intent = new Intent (MainMenuActivity.this , VersusActivity.class);
        startActivity (intent);
    }
}