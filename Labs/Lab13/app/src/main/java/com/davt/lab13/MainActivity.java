package com.davt.lab13;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private EditText emailEditText, passwordEditText, phoneEditText, otpEditText;
    private Button loginButton, registerButton, sendOtpButton, verifyOtpButton;
    private TextView forgotPasswordText;

    // Firebase Authentication
    private FirebaseAuth mAuth;
    
    // PhoneAuthProvider
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

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
        
        // Register button click listener
        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        });
        
        // Setup phone authentication callbacks
        setupPhoneAuthCallbacks();
        
        // Send OTP button click listener
        sendOtpButton.setOnClickListener(v -> {
            String phoneNumber = phoneEditText.getText().toString().trim();
            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Format phone number with country code if not already included
            if (!phoneNumber.startsWith("+")) {
                phoneNumber = "+84" + phoneNumber; // Using Vietnam country code as an example
            }
            
            sendOtpToPhone(phoneNumber);
        });
        
        // Verify OTP button click listener
        verifyOtpButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            
            verifyOtp(otp);
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
    
    private void setupPhoneAuthCallbacks() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                // This callback is invoked if an invalid request for verification is made,
                // for instance if the phone number format is not valid.
                Toast.makeText(MainActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                // The SMS verification code has been sent to the provided phone number
                // Now we need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                verificationId = s;
                Toast.makeText(MainActivity.this, "OTP has been sent to your phone", Toast.LENGTH_SHORT).show();
            }
        };
    }
    
    private void sendOtpToPhone(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        
        Toast.makeText(this, "Sending OTP to " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
    
    private void verifyOtp(String otp) {
        if (verificationId != null) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
            signInWithPhoneAuthCredential(credential);
        } else {
            Toast.makeText(this, "Please request OTP first", Toast.LENGTH_SHORT).show();
        }
    }
    
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(MainActivity.this, "Authentication successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        // Sign in failed, display a message and update the UI
                        Toast.makeText(MainActivity.this, "Authentication failed: " + task.getException().getMessage(), 
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}