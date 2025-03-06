package com.example.lab2_2;

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

    EditText edtNumber1, edtNumber2;
    Button btnAdd, btnSubtract, btnMultiply, btnDivide;
    TextView tvResult;

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

        edtNumber1 = findViewById(R.id.edtNumber1);
        edtNumber2 = findViewById(R.id.edtNumber2);
        tvResult = findViewById(R.id.tvResult);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSubtract = findViewById(R.id.btnSubtract);
        Button btnMultiply = findViewById(R.id.btnMultiply);
        Button btnDivide = findViewById(R.id.btnDivide);

        btnAdd.setOnClickListener(v -> calculate("+"));
        btnSubtract.setOnClickListener(v -> calculate("-"));
        btnMultiply.setOnClickListener(v -> calculate("*"));
        btnDivide.setOnClickListener(v -> calculate("/"));
    }

    private void calculate(String operation){
        String num1Str = edtNumber1.getText().toString();
        String num2Str = edtNumber2.getText().toString();
        double result;

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ số", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);

            switch (operation) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        // Handle division by zero
                        result = 0;
                        Toast.makeText(this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                    } else if (num1 == 0) {
                        // Handle the edge case where num1 is zero
                        result = 0;
                        Toast.makeText(this, "Kết quả là 0 vì tử số bằng 0", Toast.LENGTH_SHORT).show();
                    } else {
                        // Perform division if both numbers are valid
                        result = num1 / num2;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }

            tvResult.setText("Kết quả: " + result);
        } catch (Exception e) {
            Toast.makeText(this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();

        }
    }
}