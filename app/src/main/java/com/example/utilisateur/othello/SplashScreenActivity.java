package com.example.utilisateur.othello;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();

        // Create an handler to launch the next activity after some time.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainMenuActivity.class);
                startActivity(intent);
            }
        }, 5000);
    }

    /**
     * Start the animation for the splash screen.
     */
    private void StartAnimations()
    {
        // Create an animation set.
        AnimationSet s = new AnimationSet(false);

        // Load the animation.
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // Set the animation duration.
        anim.setDuration(2000);
        // Reset the animation.
        anim.reset();
        // Get the element on which the animation is launched.
        ImageView l = (ImageView) findViewById(R.id.black_disk);
        // Clear the animation.
        l.clearAnimation();
        // Start the animation.
        l.startAnimation(anim);

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim2.reset();
        anim2.setDuration(2000);
        // Set an offset to wait for it to begin.
        anim2.setStartOffset(1000);

        ImageView iv = (ImageView) findViewById(R.id.white_disk);
        iv.clearAnimation();
        iv.startAnimation(anim2);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim1.reset();
        ImageView l1 = (ImageView) findViewById(R.id.black_disk1);
        l1.clearAnimation();
        l1.startAnimation(anim1);
        anim1.setDuration(2000);
        anim1.setStartOffset(1000);

        s.addAnimation(anim);
        s.addAnimation(anim2);
        s.addAnimation(anim1);
    }
}
