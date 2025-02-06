package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashGeims extends AppCompatActivity {
    ImageView im, im2;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_geims);

        im = findViewById(R.id.im);
        im2 = findViewById(R.id.im2);
        tv = findViewById(R.id.tv);

        Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        tv.startAnimation(scale);

        im.setImageResource(R.drawable.geu);
        im2.setImageResource(R.drawable.geims);
        Intent iHome = new Intent(SplashGeims.this,MainActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent iHome = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(iHome); // it must to see only first time only when
                finish(); // press back button it should not seenable
            }
        }, 3500);
    }
}