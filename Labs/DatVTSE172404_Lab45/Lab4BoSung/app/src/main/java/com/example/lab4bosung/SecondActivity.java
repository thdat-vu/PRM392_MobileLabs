package com.example.lab4bosung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {


    private static final String TAG = "SecondActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate");

        textView = findViewById(R.id.dataTextView);

        // Nhận dữ liệu từ MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            // Nhận các loại dữ liệu
            String stringData = intent.getStringExtra("STRING_DATA");
            int intData = intent.getIntExtra("INT_DATA", 0);
            boolean booleanData = intent.getBooleanExtra("BOOLEAN_DATA", false);
            String[] arrayData = intent.getStringArrayExtra("ARRAY_DATA");
            Rice entityData = (Rice) intent.getSerializableExtra("ENTITY_DATA");
            Bundle bundleData = intent.getBundleExtra("BUNDLE_DATA");

            Log.d(TAG, "Received all data successfully");

            // Nút 1: Hiển thị String
            Button showStringButton = findViewById(R.id.showStringButton);
            showStringButton.setOnClickListener(v -> {
                textView.setText("Tên cửa hàng: " + stringData);
                Log.d(TAG, "Displayed String Data");
            });

            // Nút 2: Hiển thị Integer
            Button showIntButton = findViewById(R.id.showIntButton);
            showIntButton.setOnClickListener(v -> {
                textView.setText("Số cửa hàng: " + intData);
                Log.d(TAG, "Displayed Integer Data");
            });

            // Nút 3: Hiển thị Boolean
            Button showBooleanButton = findViewById(R.id.showBooleanButton);
            showBooleanButton.setOnClickListener(v -> {
                textView.setText("Tính hợp pháp: " + booleanData);
                Log.d(TAG, "Displayed Boolean Data");
            });

            // Nút 4: Hiển thị Array
            Button showArrayButton = findViewById(R.id.showArrayButton);
            showArrayButton.setOnClickListener(v -> {
                StringBuilder arrayString = new StringBuilder("Danh sách best seller: ");
                for (String item : arrayData) {
                    arrayString.append(item).append(", ");
                }
                textView.setText(arrayString.toString());
                Log.d(TAG, "Displayed Array Data");
            });

            // Nút 5: Hiển thị Entity
            Button showEntityButton = findViewById(R.id.showEntityButton);
            showEntityButton.setOnClickListener(v -> {
                textView.setText("Gạo: " + entityData.getName() + ", Giá: " + entityData.getPrice());
                Log.d(TAG, "Displayed Entity Data");
            });

            // Nút 6: Hiển thị Bundle
            Button showBundleButton = findViewById(R.id.showBundleButton);
            showBundleButton.setOnClickListener(v -> {
                String bundleString = bundleData.getString("BUNDLE_STRING");
                int bundleInt = bundleData.getInt("BUNDLE_INT");
                textView.setText("Bundle Data: Tên chuỗi cửa hàng: " + bundleString + ", Số cửa hàng: " + bundleInt);
                Log.d(TAG, "Displayed Bundle Data");
            });
        }
    }
}