package com.example.geims_navigation_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.geims_navigation_2.Adapter.CategoryAdapter;
import com.example.geims_navigation_2.Adapter.TopDoctorAdapter;
import com.example.geims_navigation_2.ViewModel.MainViewModel;
import com.example.geims_navigation_2.databinding.ActivityMainBinding;
import com.example.geims_navigation_2.databinding.ActivityUserDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;

public class user_dashboard extends AppCompatActivity {
    private ActivityUserDashboardBinding binding;
    private MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDashboardBinding.inflate(getLayoutInflater());

        binding.imageView6.setOnClickListener(view -> showLogoutDialog());

        viewModel = new MainViewModel();
        setContentView(binding.getRoot());

        binding.imageView9.setOnClickListener(view -> {
            Intent intent = new Intent(user_dashboard.this, health_checkup.class);
            startActivity(intent);
        });

        binding.imageView8.setOnClickListener(view -> {
            Intent intent = new Intent(user_dashboard.this, Notepad.class);
            startActivity(intent);
        });

        initCategory();
        initTopDoctors();
    }

    private void initTopDoctors() {
        binding.progressBarDoctor.setVisibility(View.VISIBLE);
        viewModel.loadDoctors().observe(this, doctorsModels -> {
            binding.doctorView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.doctorView.setAdapter(new TopDoctorAdapter(doctorsModels));
            binding.progressBarDoctor.setVisibility(View.GONE);
        });

    }

    private void initCategory(){
        binding.progressBarCat.setVisibility(View.VISIBLE);
        viewModel.loadCategory().observe(this, categoryModels -> {
            binding.catView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.catView.setAdapter(new CategoryAdapter(categoryModels));
            binding.progressBarCat.setVisibility(View.GONE);
        });
    }

    private void showLogoutDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(user_dashboard.this, Login_2.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

}











































