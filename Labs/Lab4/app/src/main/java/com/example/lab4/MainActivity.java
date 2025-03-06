package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnChooseFood, btnChooseDrink, btnExit;
    private TextView tvSelectedItems, tvTotal;

    // Biến lưu giá hiện tại của từng loại
    private double currentFoodPrice = 0;
    private double currentDrinkPrice = 0;

    // Biến lưu tên món ăn, thức uống đã chọn
    private String selectedFood = "";
    private String selectedDrink = "";

    private ActivityResultLauncher<Intent> foodActivityResultLauncher;
    private ActivityResultLauncher<Intent> drinkActivityResultLauncher;

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

        Log.d(TAG, "onCreate");
        btnChooseFood = findViewById(R.id.btnChooseFood);
        btnChooseDrink = findViewById(R.id.btnChooseDrink);
        btnExit = findViewById(R.id.btnExit);
        tvSelectedItems = findViewById(R.id.tvSelectedItems);
        tvTotal = findViewById(R.id.tvTotal);

        foodActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Lấy tên món và giá món từ FoodActivity
                            selectedFood = data.getStringExtra("foodName");
                            double newFoodPrice = data.getDoubleExtra("foodPrice", 0);
                            currentFoodPrice = newFoodPrice;
                            // Tính lại tổng tiền từ món ăn và đồ uống hiện tại
                            double total = currentFoodPrice + currentDrinkPrice;
                            tvTotal.setText(String.format("Tổng tiền: %,.0f VND", total));
                            updateSelectedItems();
                        }
                    }
                }
        );

        drinkActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Lấy tên thức uống và giá từ DrinkActivity
                            selectedDrink = data.getStringExtra("drinkName");
                            double newDrinkPrice = data.getDoubleExtra("drinkPrice", 0);
                            currentDrinkPrice = newDrinkPrice;
                            double total = currentFoodPrice + currentDrinkPrice;
                            tvTotal.setText(String.format("Tổng tiền: %,.0f VND", total));
                            updateSelectedItems();
                        }
                    }
                }
        );

        btnChooseFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            foodActivityResultLauncher.launch(intent);
        });

        btnChooseDrink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            drinkActivityResultLauncher.launch(intent);
        });

        btnExit.setOnClickListener(v -> finish());
    }

    private void updateSelectedItems() {
        tvSelectedItems.setText(selectedFood + " - " + selectedDrink);
    }

    // Các lifecycle method còn lại
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
