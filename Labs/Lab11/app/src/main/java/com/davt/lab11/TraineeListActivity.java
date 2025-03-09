package com.davt.lab11;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeListActivity extends AppCompatActivity {
    
    private Spinner spIdList;
    private Button btnAdd, btnView, btnDelete;
    private TraineeService traineeService;
    private ArrayList<String> traineeNames = new ArrayList<>();
    private Map<Integer, String> positionToIdMap = new HashMap<>();
    private String selectedTraineeId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_list);
        
        // Initialize views
        spIdList = findViewById(R.id.spIdList);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
        
        // Initialize service
        traineeService = APIClient.getClient().create(TraineeService.class);
        
        // Load trainee IDs
        loadIdList();
        
        // Set up spinner listener
        spIdList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTraineeId = positionToIdMap.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTraineeId = null;
            }
        });
        
        // Set up button listeners
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(TraineeListActivity.this, MainActivity.class);
            startActivity(intent);
        });
        
        btnView.setOnClickListener(v -> {
            if (selectedTraineeId != null) {
                Intent intent = new Intent(TraineeListActivity.this, TraineeDetailActivity.class);
                intent.putExtra("traineeId", selectedTraineeId);
                startActivity(intent);
            } else {
                Toast.makeText(TraineeListActivity.this, "Please select a trainee", Toast.LENGTH_SHORT).show();
            }
        });
        
        btnDelete.setOnClickListener(v -> {
            if (selectedTraineeId != null) {
                deleteTrainee(selectedTraineeId);
            } else {
                Toast.makeText(TraineeListActivity.this, "Please select a trainee", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadIdList();
    }

    private void loadIdList() {
        Call<Trainee[]> call = traineeService.getTrainees();
        call.enqueue(new Callback<Trainee[]>() {
            @Override
            public void onResponse(Call<Trainee[]> call, Response<Trainee[]> response) {
                Trainee[] trainees = response.body();
                if (trainees == null) {
                    return;
                }

                traineeNames.clear();
                positionToIdMap.clear();
                for (int i = 0; i < trainees.length; i++) {
                    Trainee trainee = trainees[i];
                    traineeNames.add(trainee.getName());
                    positionToIdMap.put(i, String.valueOf(trainee.getId()));
                }

                ArrayAdapter adapter = new ArrayAdapter(
                        TraineeListActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        traineeNames);
                spIdList.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Trainee[]> call, Throwable t) {
                // Handle failure
                Toast.makeText(TraineeListActivity.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void deleteTrainee(String traineeId) {
        Call<Trainee> call = traineeService.deleteTrainees(traineeId);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.body() != null) {
                    Toast.makeText(TraineeListActivity.this, "Delete successfully", Toast.LENGTH_LONG).show();
                    loadIdList();
                }
            }
            
            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                // Handle failure
            }
        });
    }
}