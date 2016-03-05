package com.example.utilisateur.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import controller.OthelloController;
import model.Othello;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        OthelloController oc = new OthelloController();
        Othello o = new Othello(oc);

        Log.e("Othello toString", o.toString());
        Log.d("Othello", "playAt(2, 4)");
        o.playAt(1, 3, 3);
        Log.e("Othello toString", "playAt(2, 4)" + o.toString());

        TextView t = (TextView) findViewById(R.id.MainMenu_TextView_title);
        t.setText(o.toString());
    }

    public void onPlay(View v)
    {
        /*Intent intent = new Intent (MainMenuActivity.this , VersusActivity.class);
        startActivity (intent);*/
        Toast.makeText(getApplicationContext(), "onPlay()", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
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

        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}