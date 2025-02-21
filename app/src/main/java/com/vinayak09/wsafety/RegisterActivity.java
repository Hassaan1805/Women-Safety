package com.vinayak09.wsafety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Database helper instance
    private myHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // XML layout reference (update if needed)

        // Initialize database helper
        dbHelper = new myHelper(this);

        // Find views by ID
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        Button registerButton = findViewById(R.id.register_button);
        TextView signInOption = findViewById(R.id.sign_in_option);

        // Set up Register button click listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();


                // Validate input fields
                if (username.isEmpty() || password.isEmpty() ) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Insert data into database
                    dbHelper.insertUser(username, password);
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // Clear fields after successful registration
                    usernameInput.setText("");
                    passwordInput.setText("");
                }
            }
        });

        // Set up Sign In option click listener
        signInOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign In activity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close current activity
            }
        });
    }
}
