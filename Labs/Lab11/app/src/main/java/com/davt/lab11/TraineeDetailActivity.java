package com.davt.lab11;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.davt.lab11.TraineeService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeDetailActivity extends AppCompatActivity {
    
    private EditText etName, etEmail, etPhone, etGender;
    private Button btnUpdate;
    private TraineeService traineeService;
    private String traineeId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        btnUpdate = findViewById(R.id.btnSave);
        btnUpdate.setText("Update");
        
        // Initialize service
        traineeService = APIClient.getClient().create(TraineeService.class);
        
        // Get trainee ID from intent
        traineeId = getIntent().getStringExtra("traineeId");
        
        // Load trainee details
        loadTraineeDetails();
        
        // Set up button listener
        btnUpdate.setOnClickListener(v -> {
            updateTrainee();
        });
    }
    
    private void loadTraineeDetails() {
        Call<Trainee> call = traineeService.getTrainees(traineeId);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                Trainee trainee = response.body();
                if (trainee != null) {
                    etName.setText(trainee.getName());
                    etEmail.setText(trainee.getEmail());
                    etPhone.setText(trainee.getPhone());
                    etGender.setText(trainee.getGender());
                }
            }
            
            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                // Handle failure
            }
        });
    }
    
    private void updateTrainee() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();
        
        Trainee trainee = new Trainee(name, email, phone, gender);
        Call<Trainee> call = traineeService.updateTrainees(traineeId, trainee);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.body() != null) {
                    Toast.makeText(TraineeDetailActivity.this, "Update successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            
            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                // Handle failure
            }
        });
    }
}