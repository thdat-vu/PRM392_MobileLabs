package com.example.lab2_3;



import static com.example.lab2_3.R.id.btnSignIn;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    // Views
    private EditText etUsername;
    private EditText etPassword;
    private TextView tvNotAccountYet;
    private Button btnSignIn;

    // Notify
    private final String REQUIRE = "Require";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Reference from Layout
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvNotAccountYet = findViewById(R.id.tvNotAccountYet);
        btnSignIn = findViewById(R.id.btnSignIn);

        // Register event
        tvNotAccountYet.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    private boolean checkInput() {
        // Username
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            etUsername.setError(REQUIRE);
            return false;
        }

        // Password
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError(REQUIRE);
            return false;
        }

        // Valid
        return true;
    }

    private void signIn() {
        // Invalid
        if (!checkInput()) {
            return;
        }

        // Start MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Close current activity
    }

    private void signUpForm() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignIn) {
            signIn();
        } else if (v.getId() == R.id.tvNotAccountYet) {
            signUpForm();
        }
    }
}
