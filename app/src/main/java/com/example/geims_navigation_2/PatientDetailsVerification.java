package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class PatientDetailsVerification extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_verification);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientDetailsVerification.this, MainActivity.class);
                startActivity(i);
            }
        });

        String[] patients = {
                "John Doe",
                "Jane Smith",
                "Michael Brown",
                "Emily Davis",
                "David Wilson",
        };
        ListView listV = findViewById(R.id.listV);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patients);
        listV.setAdapter(ad);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PatientDetailsVerification.this, MainActivity.class);
                startActivity(i);
            }
        });

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(PatientDetailsVerification.this, "Clicked: " + i, Toast.LENGTH_SHORT).show();
                String str = listV.getAdapter().getItem(i).toString();
                if(i==0) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                } else if (i==1) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                } else if (i==2) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                } else if (i==3) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                } else if (i==4) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                } else if (i==5) {
                    Intent intent = new Intent(getApplicationContext(), Protocol_1.class);
                    startActivity(intent);
                }
            }
        });
    }
}