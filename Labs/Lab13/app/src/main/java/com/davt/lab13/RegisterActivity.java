package com.davt.lab13;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        emailEditText = findViewById(R.id.registerEmailEditText);
        passwordEditText = findViewById(R.id.registerPasswordEditText);
        registerButton = findViewById(R.id.registerSubmitButton);

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        finish(); // Return to login screen or previous activity
                    }
                });
    }
}