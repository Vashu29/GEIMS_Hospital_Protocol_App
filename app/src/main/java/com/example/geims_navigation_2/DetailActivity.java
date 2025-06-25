package com.example.geims_navigation_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.geims_navigation_2.Adapter.DateAdapter;
import com.example.geims_navigation_2.Adapter.TimeAdapter;
import com.example.geims_navigation_2.Domain.DoctorsModel;
import com.example.geims_navigation_2.databinding.ActivityDetailBinding;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private DoctorsModel item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        item = (DoctorsModel) getIntent().getSerializableExtra("object");

        setVariable();
        InitDate();
        InitTime();
    }

    private void InitTime() {
        binding.timeView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        binding.timeView.setAdapter(new TimeAdapter(generateTimeSlots()));
    }

    private void InitDate() {
        binding.dateView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        binding.dateView.setAdapter(new DateAdapter(generateDates()));
    }

    public static List<String> generateTimeSlots(){
        List<String> timeSlots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        for(int i = 0; i < 24; i+=2){
            LocalTime time = LocalTime.of(i, 0);
            timeSlots.add(time.format(formatter));
        }
        return timeSlots;
    }

    public static List<String> generateDates() {
        List<String> dates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE/dd/MM");
        for(int i = 0; i < 7; i++){
            dates.add(today.plusDays(i).format(formatter));
        }
        return dates;
    }

    private void setVariable() {

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(DetailActivity.this)
                .load(item.getPicture())
                .into(binding.img);

        binding.addressTxt.setText(item.getAddress());
        binding.nameTxt.setText(item.getName());
        binding.specialTxt.setText(item.getSpecial());
        binding.patiensTxt.setText(item.getPatiens()+"");
        binding.experinceTxt.setText(item.getExpriense()+" Years");

        binding.button.setOnClickListener(v -> {
            String selectedDate = DateAdapter.getSelectedDate();
            String selectedTime = TimeAdapter.getSelectedTime();

            // Get EditText values
            String userName = ((EditText) findViewById(R.id.et1)).getText().toString().trim();
            String userEmail = ((EditText) findViewById(R.id.et2)).getText().toString().trim();
            String userPhone = ((EditText) findViewById(R.id.et3)).getText().toString().trim();

            // Validation
            if (userName.isEmpty() || userEmail.isEmpty() || userPhone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!userEmail.endsWith("@gmail.com")) {
                Toast.makeText(this, "Enter a valid Gmail address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userPhone.length() != 10 || !userPhone.matches("\\d+")) {
                Toast.makeText(this, "Enter a valid 10-digit phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show();
                return;
            }

            // Format and send message
            String message = "Appointment Booked!\n"
                    + "Name: " + userName + "\n"
                    + "Email: " + userEmail + "\n"
                    + "Phone: " + userPhone + "\n\nWith\n"
                    + "Doctor: Dr. " + item.getName() + "\n"
                    + "Date: " + selectedDate + "\n"
                    + "Time: " + selectedTime;

            sendSms("8818076503", message);
        });
    }

    private void sendSms(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

}






















