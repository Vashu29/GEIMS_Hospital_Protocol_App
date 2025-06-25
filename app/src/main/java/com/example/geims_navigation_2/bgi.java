package com.example.geims_navigation_2;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class bgi extends AppCompatActivity {
    EditText edtWeight, editHeightFt, editHeightIn;
    Button btnCalculate;
    TextView txtResult;
    LinearLayout bgiMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FrameLayout frameLayout = findViewById(R.id.videoFrame); // use correct id
            frameLayout.setClipToOutline(true);
            frameLayout.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }

        edtWeight=findViewById(R.id.editWeight);
        editHeightFt=findViewById(R.id.editHeightFt);
        editHeightIn=findViewById(R.id.editHeightIn);
        btnCalculate=findViewById(R.id.btnCalculate);
        txtResult=findViewById(R.id.txtResult);
        bgiMain=findViewById(R.id.bgimain);

        VideoView videoView = findViewById(R.id.videoView);

// Set video path from res/raw
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bgi_video);
        videoView.setVideoURI(videoUri);

// Optional: Add media controls
        /*MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);*/

// Make video loop
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // 🔁 This enables infinite repeat
            videoView.start();   // Start playing
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wt=Integer.parseInt(edtWeight.getText().toString());
                int ft=Integer.parseInt(editHeightFt.getText().toString());
                int in=Integer.parseInt(editHeightIn.getText().toString());

                int totalIn=ft*12 + in;
                double totalCm = totalIn*2.53;
                double totalM = totalCm/100;
                double bmi = wt/(totalM*totalM);
                if(bmi>25){
                    txtResult.setText("  \t\tBMI = "+String.format("%.2f", bmi)+"\n\n  \tYou're Overwight");
                    bgiMain.setBackgroundColor(getResources().getColor(R.color.colorOW));
                } else if (bmi<18){
                    txtResult.setText("  \t\tBMI = "+String.format("%.2f", bmi)+"\n\n  \tYou're Underwight");
                    bgiMain.setBackgroundColor(getResources().getColor(R.color.colorUn));
                } else {
                    txtResult.setText("  \t\tBMI = "+String.format("%.2f", bmi)+"\n\n  \tYou're Healthy");
                    bgiMain.setBackgroundColor(getResources().getColor(R.color.colorHe));
                }
            }
        });
    }
}