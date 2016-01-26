package com.example.utilisateur.othello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class VersusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versus);
    }

    public void onBack(View v)
    {
        finish();
    }
}
