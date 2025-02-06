package com.example.geims_navigation_2;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class video extends AppCompatActivity {
    VideoView video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        video = findViewById(R.id.video);

        video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.front_latest));

        // Automatically start the video when it's prepared
        video.setOnPreparedListener(mp -> {
            video.start();
            mp.setLooping(true); // Loop the video continuously
        });

        // Optionally handle the completion to restart the video (redundant due to looping)
        video.setOnCompletionListener(mp -> video.start());
    }
}