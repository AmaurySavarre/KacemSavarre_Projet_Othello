package com.example.utilisateur.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen1);
        StartAnimations();

        Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
        startActivity(intent);
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        ImageView l = (ImageView) findViewById(R.id.black_disk);
        l.clearAnimation();
        l.startAnimation(anim);

       /* anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.black_disk);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        setContentView(R.layout.activity_splash_screen1);
        RelativeLayout l2 = (RelativeLayout) findViewById(R.id.splash_layout1);
        l2.setVisibility(View.VISIBLE);
        l2.clearAnimation();
        l2.startAnimation(anim);*/
    }
}
