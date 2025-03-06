package com.davt.lab13;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });
    }

}