package com.vinayak09.wsafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myHelper extends SQLiteOpenHelper {

    // Database and Table details
    final static String DB_NAME = "registration_db";
    final static String TB_NAME = "users";
    final static String COL_1 = "id";         // ID column
    final static String COL_2 = "username";  // Username column
    final static String COL_3 = "password";  // Password column

    public myHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating table with appropriate columns
        db.execSQL("CREATE TABLE " + TB_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For future versions of the app
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db);
    }

    // Method to insert a new user's registration credentials into the database
    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if a user already exists
        Cursor cursor = db.query(TB_NAME, new String[]{COL_1}, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            // If a user exists, update the record
            ContentValues cv = new ContentValues();
            cv.put(COL_2, username);
            cv.put(COL_3, password);
            db.update(TB_NAME, cv, null, null);
        } else {
            // If no user exists, insert new user
            ContentValues cv = new ContentValues();
            cv.put(COL_2, username);
            cv.put(COL_3, password);
            db.insert(TB_NAME, null, cv);
        }
        cursor.close();
    }

    // Method to fetch the username from the database
    public String getUsername() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COL_2 + " FROM " + TB_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Assuming you want the first username in the table
            String username = cursor.getString(cursor.getColumnIndex(COL_2));
            cursor.close();
            return username;
        }

        cursor.close();
        return "NONE"; // Return "NONE" if no username is found
    }
}
