package com.example.geims_navigation_2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Survey_Complete_Pateints extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view, btnBack, btnLogout;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete_pateints);

        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Survey_Complete_Pateints.this, after_login.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Survey_Complete_Pateints.this, login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        insert.setOnClickListener(view -> {
            String nameTXT = name.getText().toString();
            String contactTXT = contact.getText().toString();
            String dobTXT = dob.getText().toString();

            Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, dobTXT);
            if (checkinsertdata)
                Toast.makeText(Survey_Complete_Pateints.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Survey_Complete_Pateints.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
        });

        update.setOnClickListener(view -> {
            String nameTXT = name.getText().toString();
            String contactTXT = contact.getText().toString();
            String dobTXT = dob.getText().toString();

            Boolean checkupdatedata = DB.updateuserdata(nameTXT, contactTXT, dobTXT);
            if (checkupdatedata)
                Toast.makeText(Survey_Complete_Pateints.this, "Entry Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Survey_Complete_Pateints.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
        });

        delete.setOnClickListener(view -> {
            String contactTXT = contact.getText().toString();
            Boolean checkudeletedata = DB.deletedata(contactTXT);
            if (checkudeletedata)
                Toast.makeText(Survey_Complete_Pateints.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Survey_Complete_Pateints.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
        });

        view.setOnClickListener(view -> {
            Cursor res = DB.getdata();
            if (res.getCount() == 0) {
                Toast.makeText(Survey_Complete_Pateints.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("Contact :").append(res.getString(0)).append("\n");
                buffer.append("Name :").append(res.getString(1)).append("\n");
                buffer.append("Date of Birth :").append(res.getString(2)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Survey_Complete_Pateints.this);
            builder.setCancelable(true);
            builder.setTitle("User Entries");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }
}