package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class health_checkup extends AppCompatActivity {
    String[] cipher = {"BMI(Body Mass Index)", "Pima Diabetes Prediction", "Ai Cancer Prediction", "Ai Pancreas Prediction", "Heart Disease Prediction","Skin Disease / Cancer Detection"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_checkup);

        ListView lv = findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item_health_checkup, // custom layout file
                R.id.textItem,                     // TextView inside the custom layout
                cipher
        );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("MainActivity", "ListView item clicked: " + i);  // Add this line
                Toast.makeText(health_checkup.this, "Clicked: " + cipher[i], Toast.LENGTH_SHORT).show();
                String str = lv.getAdapter().getItem(i).toString();
                if(i==0) {
                    Intent intent = new Intent(getApplicationContext(), bgi.class);
                    startActivity(intent);
                } else if(i==1) {
                    Intent intent = new Intent(getApplicationContext(), pima_diabetes.class);
                    startActivity(intent);
                }
            }
        });
    }
}