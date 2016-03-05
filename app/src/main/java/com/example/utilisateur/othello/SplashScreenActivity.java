package com.example.utilisateur.othello;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
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
        //startActivity(intent);
    }

    /*tAnimation.setDuration(1000);
rAnimation.setDuration(1000);

sAnimation.setDuration(500);
sAnimation.setStartOffset(1000); //offsets at the duration of the first set

tAnimation2.setDuration(500);
tAnimation2.setStartOffset(1000); //offsets at the duration of the first set


//does it matter here who I add first to the animation set?
//because if I change the order in which i add the animations to the animation set, I get different results

s.addAnimation(rAnimation);
s.addAnimation(tAnimation);
s.addAnimation(tAnimation2);
s.addAnimation(sAnimation);*/

    private void StartAnimations() {
        AnimationSet s = new AnimationSet(false);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.setDuration(1000);
        anim.reset();
        ImageView l = (ImageView) findViewById(R.id.black_disk);
        l.clearAnimation();
        l.startAnimation(anim);

      Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim2.reset();
        anim2.setDuration(500);
        anim2.setStartOffset(1000);

        ImageView iv = (ImageView) findViewById(R.id.white_disk);
        iv.clearAnimation();
        iv.startAnimation(anim2);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim1.reset();
        ImageView l1 = (ImageView) findViewById(R.id.black_disk1);
        l1.clearAnimation();
        l1.startAnimation(anim1);
        anim1.setDuration(500);
        anim1.setStartOffset(1000);

        s.addAnimation(anim);
        s.addAnimation(anim2);
        s.addAnimation(anim1);

       /* anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        setContentView(R.layout.activity_splash_screen1);
        RelativeLayout l2 = (RelativeLayout) findViewById(R.id.splash_layout1);
        l2.setVisibility(View.VISIBLE);
        l2.clearAnimation();
        l2.startAnimation(anim);*/
    }
}
