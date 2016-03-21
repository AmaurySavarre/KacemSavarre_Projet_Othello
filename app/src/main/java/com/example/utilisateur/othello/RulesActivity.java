package com.example.utilisateur.othello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView text = (TextView)findViewById(R.id.Rules_Textview_text);
        text.setMovementMethod(new ScrollingMovementMethod());
    }
}

// TODO: 3/21/2016 Mettre des images pour les régles.
