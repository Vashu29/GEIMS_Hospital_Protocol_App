package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Protocol_4 extends AppCompatActivity {
    CheckBox checkBox;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol4);

        checkBox = findViewById(R.id.idCheckBox);
        nextButton = findViewById(R.id.btn);

        // Set a listener on the checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Enable the button if checkbox is checked, otherwise disable it
                nextButton.setEnabled(isChecked);
            }
        });

        // Set an initial disabled state for the button
        nextButton.setEnabled(false);

        // Set an OnClickListener for the Next button
        nextButton.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                // Navigate to the next activity
                Intent intent = new Intent(Protocol_4.this, Protocol_5.class);
                startActivity(intent);
            } else {
                // Show a toast message
                Toast.makeText(Protocol_4.this, "Please click on the checkbox before proceeding.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}