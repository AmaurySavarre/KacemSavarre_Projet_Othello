package com.example.utilisateur.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void onPlay(View v)
    {
        /*Intent intent = new Intent (MainMenuActivity.this , VersusActivity.class);
        startActivity (intent);*/
        Toast.makeText(getApplicationContext(), "onPlay()", Toast.LENGTH_SHORT).show();
    }

    public void onHighScores(View v)
    {
        Toast.makeText(getApplicationContext(), "onHighScores()", Toast.LENGTH_SHORT).show();
    }

    public void onExit(View v)
    {
        Toast.makeText(getApplicationContext(), "onExit()", Toast.LENGTH_SHORT).show();
    }

    public void onRules(View v)
    {
        Toast.makeText(getApplicationContext(), "onRules()", Toast.LENGTH_SHORT).show();
    }

    public void onSettings(View v)
    {
        Toast.makeText(getApplicationContext(), "onSettings()", Toast.LENGTH_SHORT).show();
    }
}