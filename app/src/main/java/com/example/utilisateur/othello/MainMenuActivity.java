package com.example.utilisateur.othello;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        initView();
    }

    private void initView()
    {
        Button btn = (Button) findViewById(R.id.MainMenu_Button_play);
        btn.setText(R.string.MainMenu_play);

        btn = (Button) findViewById(R.id.MainMenu_Button_highScores);
        btn.setText(R.string.MainMenu_high_scores);

        btn = (Button) findViewById(R.id.MainMenu_Button_exit);
        btn.setText(R.string.MainMenu_exit);

        TextView label = (TextView) findViewById(R.id.MainMenu_TextView_title);
        label.setText(R.string.MainMenu_label);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        initView();
    }

    public void onPlay(View v)
    {
        String dir = getFilesDir().getAbsolutePath();
        final File file = new File(dir, "save.data");

        if (file.exists())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    file.delete();
                    setContentView(R.layout.activity_versus);
                }
            });

            builder.setMessage(R.string.MainMenu_newGame);

            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            setContentView(R.layout.activity_versus);
        }
    }

    public void onPvP(View v)
    {
        Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
        setContentView(R.layout.activity_main_menu);
        intent.putExtra("AI", false);
        startActivity(intent);
    }

    public void onPvM(View v)
    {
        Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
        setContentView(R.layout.activity_main_menu);
        intent.putExtra("AI", true);
        startActivity(intent);
    }

    public void onBack(View v)
    {
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public void onBackPressed()
    {
        if (findViewById(R.id.Versus_mainLayout) == null)
        {
            super.onBackPressed();
        }
        else
        {
            setContentView(R.layout.activity_main_menu);
        }
    }

    public void onHighScores(View v)
    {
        Toast.makeText(getApplicationContext(), "Nope !", Toast.LENGTH_SHORT).show();
    }

    public void onExit(View v)
    {
        // Exit the application.
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void onRules(View v)
    {
        Intent intent = new Intent(MainMenuActivity.this, RulesActivity.class);
        startActivity(intent);
    }

    public void onSettings(View v)
    {
        Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}