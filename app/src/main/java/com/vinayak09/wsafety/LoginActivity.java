package com.vinayak09.wsafety;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private myHelper dbHelper; // Instance of the database helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // XML layout for login

        // Initialize database helper
        dbHelper = new myHelper(this);

        // Find views by ID
        EditText usernameInput = findViewById(R.id.username);
        EditText passwordInput = findViewById(R.id.password);
        Button signInButton = findViewById(R.id.sign_in_button);
        TextView registerOption = findViewById(R.id.register_option);

        // Set up Sign In button click listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validate inputs
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Check credentials in the database
                    boolean isValid = validateCredentials(username, password);
                    if (isValid) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity (e.g., HomeActivity)
                        Intent intent = new Intent(LoginActivity.this, RegisterNumberActivity.class); // Replace with actual class
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set up Register option click listener
        registerOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        SharedPreferences loginPreferences = getSharedPreferences("username",MODE_PRIVATE);
        SharedPreferences.Editor userEdit = loginPreferences.edit();
        userEdit.putString("user", usernameInput.getText().toString().trim());
        userEdit.apply();
    }

    // Function to validate user credentials
    private boolean validateCredentials(String username, String password) {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM " + myHelper.TB_NAME + " WHERE username = ? AND password = ?",
                new String[]{username, password}
        );

        boolean isValid = cursor.getCount() > 0; // Check if any record exists
        cursor.close(); // Close the cursor
        return isValid;
    }
}
