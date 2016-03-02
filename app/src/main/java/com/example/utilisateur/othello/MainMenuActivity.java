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

    /*PopupWindow popUpSettings;
    TextView tv;
    LayoutParams params;

    LinearLayout settingsLayout;
    RelativeLayout mainLayout;
    boolean clickSettings = true;*/

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

        /*popUpSettings = new PopupWindow(this);
        settingsLayout = new LinearLayout(this);
        mainLayout = (RelativeLayout) findViewById(R.id.main_menu_layout);
        params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText("Hi this is a sample text for popup window");
        layout.addView(tv, params);
        popUpSettings.setContentView(layout);
        // popUpSettings.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        mainLayout.addView(but, params);
        setContentView(mainLayout);*/
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

        /*PopupWindow pop = new PopupWindow(findViewById(R.id.splash_layout1));
        pop.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
        pop.update(50, 50, 300, 80);
        if (clickSettings) {
            popUpSettings.showAtLocation(mainLayout, Gravity.CENTER, 10, 10);
            popUpSettings.update(50, 50, 300, 80);
            clickSettings = false;
        } else {
            popUpSettings.dismiss();
            clickSettings = true;
        }*/
        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}