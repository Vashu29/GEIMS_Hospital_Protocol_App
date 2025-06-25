package com.example.geims_navigation_2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Protocol_Done_Patient extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view, btnBack, btnLogout;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocol_done_patient);

        //update = findViewById(R.id.btnUpdate);
        //delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        /*
        update.setOnClickListener(view -> {
            String nameTXT = name.getText().toString();
            String contactTXT = contact.getText().toString();
            String dobTXT = dob.getText().toString();

            Boolean checkupdatedata = DB.updateuserdata(nameTXT, contactTXT, dobTXT);
            if (checkupdatedata)
                Toast.makeText(Protocol_Done_Patient.this, "Entry Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Protocol_Done_Patient.this, "Entry Not Updated", Toast.LENGTH_SHORT).show();
        });

        delete.setOnClickListener(view -> {
            String contactTXT = contact.getText().toString();
            Boolean checkudeletedata = DB.deletedata(contactTXT);
            if (checkudeletedata)
                Toast.makeText(Protocol_Done_Patient.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Protocol_Done_Patient.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
        });*/

        view.setOnClickListener(view -> {
            Cursor res = DB.getdata();
            if (res.getCount() == 0) {
                Toast.makeText(Protocol_Done_Patient.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                return;
            }
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("Name :").append(res.getString(0)).append("\n");
                buffer.append("Contact :").append(res.getString(1)).append("\n");
                buffer.append("Date of Birth :").append(res.getString(2)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Protocol_Done_Patient.this);
            builder.setCancelable(true);
            builder.setTitle("User Entries");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }
}