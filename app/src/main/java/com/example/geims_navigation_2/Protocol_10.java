package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Protocol_10 extends AppCompatActivity {
    CheckBox checkBox;
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol10);

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
                Intent intent = new Intent(Protocol_10.this, Survey_Complete_Pateints.class);
                startActivity(intent);
            } else {
                // Show a toast message
                Toast.makeText(Protocol_10.this, "Please click on the checkbox before proceeding.", Toast.LENGTH_SHORT).show();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    // Navigate to the next activity
                    Toast.makeText(Protocol_10.this, "All Protocols Done.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Protocol_10.this, Survey_Complete_Pateints.class);
                    startActivity(intent);
                } else {
                    // Show a toast message
                    Toast.makeText(Protocol_10.this, "Please click on the checkbox before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}