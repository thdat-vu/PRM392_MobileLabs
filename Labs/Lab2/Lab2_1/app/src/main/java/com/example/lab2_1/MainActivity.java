package com.example.lab2_1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // declare variables
    private EditText minInput;
    private EditText maxInput;
    private Button btnGenerate;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // assign variables
        minInput = findViewById(R.id.minInput);
        maxInput = findViewById(R.id.maxInput);
        btnGenerate = findViewById(R.id.btnGenerate);
        resultText = findViewById(R.id.resultText);
        // set on click listener
        btnGenerate.setOnClickListener(v -> {

//            // get min and max values
//            int min = Integer.parseInt(minInput.getText().toString());
//            int max = Integer.parseInt(maxInput.getText().toString());
//            // generate random number
//            int randomNumber = (int) (Math.random() * (max - min + 1) + min);
//            // set result text
//            resultText.setText("Result: " + randomNumber);

            String minStr = minInput.getText().toString().trim();
            String maxStr = maxInput.getText().toString().trim();

            // Validate input
            if (minStr.isEmpty() || maxStr.isEmpty()) {
                Toast.makeText(this, "Please enter both min and max values", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int min = Integer.parseInt(minStr);
                int max = Integer.parseInt(maxStr);

                if (min > max) {
                    Toast.makeText(this, "Min value must be less than or equal to max value", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Generate random number
                int randomNumber = (int) (Math.random() * (max - min + 1) + min);
                resultText.setText("Result: " + randomNumber);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number format. Please enter valid integers.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}