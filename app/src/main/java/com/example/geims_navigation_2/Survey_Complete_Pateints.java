package com.example.geims_navigation_2;

import android.content.ContentValues;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.google.firebase.auth.FirebaseAuth;

public class Survey_Complete_Pateints extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view, btnBack, btnLogout;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete_pateints);

        Button btnDownloadCSV = findViewById(R.id.btnDownloadCSV);
        btnDownloadCSV.setOnClickListener(view -> {
            exportDatabaseToCSV();
        });

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
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Survey_Complete_Pateints.this, Login_2.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        insert.setOnClickListener(view -> {
            String nameTXT = name.getText().toString();
            String contactTXT = contact.getText().toString();
            String dobTXT = dob.getText().toString();

            if (!contactTXT.matches("\\d{10,12}")) {
                contact.setError("Enter a valid phone number (10-12 digits, no special characters)");
                return;  // Stop execution if invalid
            }

            // Validate DOB (No special characters, must be DDMMYYYY format)
            if (!dobTXT.matches("\\d{8}")) {  // Ensures exactly 8 digits (DDMMYYYY)
                dob.setError("Enter DOB in DDMMYYYY format without / or -");
                return;
            }

            String formattedDOB = convertDOBFormat(dobTXT);
            if (formattedDOB == null) {
                dob.setError("Invalid Date. Please enter in DDMMYYYY format.");
                return;
            }

            Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, formattedDOB);
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
                buffer.append("Name :").append(res.getString(0)).append("\n");
                buffer.append("Contact :").append(res.getString(1)).append("\n");
                buffer.append("Date of Birth :").append(res.getString(2)).append("\n\n");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(Survey_Complete_Pateints.this);
            builder.setCancelable(true);
            builder.setTitle("User Entries");
            builder.setMessage(buffer.toString());
            builder.show();
        });
    }

    private String convertDOBFormat(String dobTXT) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputFormat.parse(dobTXT);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;  // Return null if conversion fails
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportDatabaseToCSV() {
        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // For Android 10 and above, use MediaStore API
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "UserData.csv");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    Toast.makeText(this, "Failed to create file!", Toast.LENGTH_SHORT).show();
                    return;
                }

                fos = (FileOutputStream) getContentResolver().openOutputStream(uri);
            } else {
                // For Android 9 and below, use the traditional method
                File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(downloadsDir, "UserData.csv");
                fos = new FileOutputStream(file);
            }

            // Write CSV headers
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.append("Name, Contact, Date of Birth\n");

            // Write data rows
            while (res.moveToNext()) {
                writer.append(res.getString(0)).append(", ")
                        .append(res.getString(1)).append(", ")
                        .append(res.getString(2)).append("\n");
            }

            writer.flush();
            writer.close();
            fos.close();

            Toast.makeText(this, "CSV Downloaded: Check File Manager -> Downloads", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error exporting CSV: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
 /*
package com.example.geims_navigation_2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class Survey_Complete_Pateints extends AppCompatActivity {
    EditText name, contact, dob;
    Button insert, update, delete, view, btnBack, btnLogout, btnDownloadCSV;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_complete_pateints);

        btnBack = findViewById(R.id.btnBack);
        btnLogout = findViewById(R.id.btnLogout);
        btnDownloadCSV = findViewById(R.id.btnDownloadCSV);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);
        DB = new DBHelper(this);

        insert.setOnClickListener(view -> {
            String nameTXT = name.getText().toString().trim();
            String contactTXT = contact.getText().toString().trim();
            String dobTXT = dob.getText().toString().trim();

            if (!contactTXT.matches("\\d{10,12}")) {
                contact.setError("Enter a valid phone number (10-12 digits)");
                return;
            }

            if (!dobTXT.matches("\\d{8}")) {
                dob.setError("Enter DOB in DDMMYYYY format");
                return;
            }

            boolean isInserted = DB.insertuserdata(nameTXT, contactTXT, dobTXT);
            Toast.makeText(this, isInserted ? "New Entry Inserted" : "Entry Not Inserted", Toast.LENGTH_SHORT).show();
        });

        update.setOnClickListener(view -> {
            boolean isUpdated = DB.updateuserdata(name.getText().toString(), contact.getText().toString(), dob.getText().toString());
            Toast.makeText(this, isUpdated ? "Entry Updated" : "Entry Not Updated", Toast.LENGTH_SHORT).show();
        });

        delete.setOnClickListener(view -> {
            boolean isDeleted = DB.deletedata(contact.getText().toString());
            Toast.makeText(this, isDeleted ? "Entry Deleted" : "Entry Not Deleted", Toast.LENGTH_SHORT).show();
        });

        view.setOnClickListener(view -> {
            Cursor res = DB.getdata();
            if (res.getCount() == 0) {
                Toast.makeText(this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                return;
            }

            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                buffer.append("Name: ").append(res.getString(0)).append("\n");
                buffer.append("Contact: ").append(res.getString(1)).append("\n");
                buffer.append("DOB: ").append(res.getString(2)).append("\n\n");
            }
            res.close();

            new AlertDialog.Builder(this)
                    .setTitle("User Entries")
                    .setMessage(buffer.toString())
                    .setCancelable(true)
                    .show();
        });

        btnDownloadCSV.setOnClickListener(view -> exportDatabaseToCSV());
    }

    private void exportDatabaseToCSV() {
        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            FileOutputStream fos;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "UserData.csv");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri == null) {
                    Toast.makeText(this, "Failed to create file!", Toast.LENGTH_SHORT).show();
                    return;
                }
                fos = (FileOutputStream) getContentResolver().openOutputStream(uri);
            } else {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "UserData.csv");
                fos = new FileOutputStream(file);
            }

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.append("Name, Contact, DOB\n");
            while (res.moveToNext()) {
                writer.append(res.getString(0)).append(", ").append(res.getString(1)).append(", ").append(res.getString(2)).append("\n");
            }
            writer.flush();
            writer.close();
            fos.close();

            Toast.makeText(this, "CSV Downloaded!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error exporting CSV: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}

 */
