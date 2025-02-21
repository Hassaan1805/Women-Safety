package com.vinayak09.wsafety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterNumberActivity extends AppCompatActivity {
    myHelper db;
    TextInputEditText number;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_number);

        db = new myHelper(this);
        username = findViewById(R.id.username_display);
        number = findViewById(R.id.numberEdit);
        SharedPreferences loginPreferences = getSharedPreferences("username",MODE_PRIVATE);
        String userString = loginPreferences.getString("user","NONE");
        String myUser = db.getUsername();
        username.setText("Hello " + myUser );

    }

    public void saveNumber(View view) {
        String numberString = number.getText().toString();
        if(numberString.length()==10){
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("ENUM", numberString);
            myEdit.apply();
            RegisterNumberActivity.this.finish();
        }else {
            Toast.makeText(this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(RegisterNumberActivity.this, MainActivity.class); // Replace with actual class
        startActivity(intent);

    }

}