package com.example.utilisateur.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
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

        Intent intent = new Intent(MainMenuActivity.this, VersusActivity.class);
        startActivity(intent);
    }

    public void onHighScores(View v)
    {
        Toast.makeText(getApplicationContext(), "onHighScores()", Toast.LENGTH_SHORT).show();
    }

    public void onExit(View v)
    {
        Toast.makeText(getApplicationContext(), "onExit()", Toast.LENGTH_SHORT).show();

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void onRules(View v)
    {
        Toast.makeText(getApplicationContext(), "onRules()", Toast.LENGTH_SHORT).show();
    }

    public void onSettings(View v)
    {
        Toast.makeText(getApplicationContext(), "onSettings()", Toast.LENGTH_SHORT).show();

        PopupWindow pop = new PopupWindow(findViewById(R.id.splash_layout1));
        pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
        pop.update(50, 50, 300, 80);
    }
}