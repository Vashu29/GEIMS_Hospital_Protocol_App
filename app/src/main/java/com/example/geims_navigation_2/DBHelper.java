package com.example.geims_navigation_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        // Updated primary key from 'name' to 'contact'
        DB.execSQL("CREATE TABLE Userdetails(contact TEXT PRIMARY KEY, name TEXT, dob TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("DROP TABLE IF EXISTS Userdetails");
        onCreate(DB); // Recreate the table with the updated schema
    }

    public Boolean insertuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("contact", contact);
        contentValues.put("dob", dob);
        long result = DB.insert("Userdetails", null, contentValues);
        return result != -1;
    }

    public Boolean updateuserdata(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("dob", dob);
        Cursor cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE contact = ?", new String[]{contact});
        if (cursor.getCount() > 0) {
            long result = DB.update("Userdetails", contentValues, "contact=?", new String[]{contact});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deletedata(String contact) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Userdetails WHERE contact = ?", new String[]{contact});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Userdetails", "contact=?", new String[]{contact});
            return result != -1;
        } else {
            return false;
        }
    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("SELECT * FROM Userdetails", null);
    }
}
