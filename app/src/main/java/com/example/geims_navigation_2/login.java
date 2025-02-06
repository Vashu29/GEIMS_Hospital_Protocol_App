package com.example.geims_navigation_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {
    private EditText edtUserName, edtPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.idEdtUserName);
        edtPassword = findViewById(R.id.idEdtPassword);
        btnLogin = findViewById(R.id.idBtnLogin);

        // Set login button click listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input from EditText fields
                String userName = edtUserName.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // Check credentials
                if (userName.equals("vashu") && password.equals("vvv3")) {
                    // Credentials are correct
                    Toast.makeText(login.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to PatientDetailsVerification activity
                    Intent intent = new Intent(login.this, after_login.class);
                    startActivity(intent);
                    finish(); // Optional: Close the login activity
                } else {
                    // Incorrect credentials
                    Toast.makeText(login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}