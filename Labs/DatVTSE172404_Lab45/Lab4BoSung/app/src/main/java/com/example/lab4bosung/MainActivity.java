    package com.example.lab4bosung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


        private static final String TAG = "MainActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Log.d(TAG, "onCreate");

            Button sendDataButton = findViewById(R.id.sendDataButton);
            sendDataButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);


                    intent.putExtra("STRING_DATA", "Cửa Hàng Gạo Thành Đạt");
                    intent.putExtra("INT_DATA", 48);
                    intent.putExtra("BOOLEAN_DATA", true);


                    String[] stringArray = {"ST25", "Lài thơm", "Tấm loại I"};
                    intent.putExtra("ARRAY_DATA", stringArray);


                    Rice rice = new Rice("ST25", 25000);
                    intent.putExtra("ENTITY_DATA", rice);

                    // Gửi Bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("BUNDLE_STRING", "Combo gạo Thành Đạt");
                    bundle.putInt("BUNDLE_INT", 48);
                    intent.putExtra("BUNDLE_DATA", bundle);

                    Log.d(TAG, "Sending data to SecondActivity");
                    startActivity(intent);
                }
            });
        }

}