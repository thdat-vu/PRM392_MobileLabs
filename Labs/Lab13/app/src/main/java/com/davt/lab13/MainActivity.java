package com.davt.lab13;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private EditText emailEditText, passwordEditText, phoneEditText, otpEditText;
    private Button loginButton, registerButton, sendOtpButton, verifyOtpButton;
    private TextView forgotPasswordText;

    // Firebase Authentication
    private FirebaseAuth mAuth;

    // Verification ID for OTP
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // UI Elements
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        phoneEditText = findViewById(R.id.phoneEditText);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        otpEditText = findViewById(R.id.otpEditText);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);

        // Email Login
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });
    }

    private void loginUser(String email, String password) {
        // This method would handle Firebase Authentication login
        // Implementation would typically go here, e.g., mAuth.signInWithEmailAndPassword(email, password)
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
                    else {
                        Toast.makeText(MainActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}