package com.example.geims_navigation_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    VideoView video;
    Button btn;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        video = findViewById(R.id.video);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, read_more.class);
                startActivity(i);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.home) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                } else if (id == R.id.about) {
                    Intent i = new Intent(MainActivity.this, read_more.class);
                    startActivity(i);
                } else if (id == R.id.image) {
                    Intent i = new Intent(MainActivity.this, images_1.class);
                    startActivity(i);
                } else if (id == R.id.video) {
                    Intent i = new Intent(MainActivity.this, video.class);
                    startActivity(i);
                }
                else if (id == R.id.saved) {
                    Intent i = new Intent(MainActivity.this, Protocol_Done_Patient.class);
                    startActivity(i);
                }
                else if (id == R.id.createAcc) {
                    Toast.makeText(MainActivity.this, "Create Account Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.login) {
                    Intent i = new Intent(MainActivity.this, login.class);
                    startActivity(i);
                } /*else if (id == R.id.logout) {
                    Toast.makeText(MainActivity.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                }*/
                else if (id == R.id.contact) {
                    Toast.makeText(MainActivity.this, "Contact Selected", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.share) {
                    Toast.makeText(MainActivity.this, "Share Selected", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        // video open

        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.comp_latest));

        // Automatically start the video when it's prepared
        video.setOnPreparedListener(mp -> {
            video.start();
            mp.setLooping(true); // Loop the video continuously
        });

        // Optionally handle the completion to restart the video (redundant due to looping)
        video.setOnCompletionListener(mp -> video.start());

        // video close
    }

    @Override
    public void onBackPressed() { // Use "onBackPressed" instead of "OnBackPressed"
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer if open
        } else {
            super.onBackPressed(); // Call the default back button behavior
        }
    }

}