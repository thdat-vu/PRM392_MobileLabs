package com.davt.lab11;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TraineeService traineeService;
    EditText etname, etemail, etphone, etgioitinh;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etname = findViewById(R.id.etName);
        etemail = findViewById(R.id.etEmail);
        etphone = findViewById(R.id.etPhone);
        etgioitinh = findViewById(R.id.etGender);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(this);

        traineeService = TraineeRepository.getTraineeService();
    }

    private void save() {
        String name = etname.getText().toString();
        String email = etemail.getText().toString();
        String phone = etphone.getText().toString();
        String gender = etgioitinh.getText().toString();


        Trainee trainee = new Trainee(name, email, phone, gender);

        try {
            Call<Trainee> call = traineeService.createTrainees(trainee);
            call.enqueue(new Callback<Trainee>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    if (response.body() != null) {
                        Toast.makeText( MainActivity.this,  "Save successfully",
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    Toast.makeText( MainActivity.this,  "Save Fail",
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d( "Loi", e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        save();
    }
}