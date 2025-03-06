package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private static final String TAG = "FoodActivity";
    private ListView lvFood;
    private Button btnDatMon;
    private List<FoodItem> foodList;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        lvFood = findViewById(R.id.lvFood);
        btnDatMon = findViewById(R.id.btnDatMon);

        // Khởi tạo dữ liệu
        foodList = new ArrayList<>();
        foodList.add(new FoodItem("Phở Hà Nội", "Phở truyền thống Hà Nội", 50000, R.drawable.pho));
        foodList.add(new FoodItem("Bún Bò Huế", "Bún bò đặc sản Huế", 55000, R.drawable.bunbohue));
        foodList.add(new FoodItem("Mì Quảng", "Mì Quảng đậm đà", 45000, R.drawable.miquang));
        foodList.add(new FoodItem("Hủ Tíu Sài Gòn", "Hủ tíu Nam Vang", 40000, R.drawable.hutiu));

        // Gán adapter
        FoodAdapter adapter = new FoodAdapter(this, foodList);
        lvFood.setAdapter(adapter);

        // Bắt sự kiện chọn item
        lvFood.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        // Xử lý nút "Đặt món"
        btnDatMon.setOnClickListener(v -> {
            if(selectedPosition >= 0) {
                FoodItem selectedFood = foodList.get(selectedPosition);
                // Tạo intent trả kết quả
                Intent resultIntent = new Intent();
                resultIntent.putExtra("foodName", selectedFood.getName());
                resultIntent.putExtra("foodPrice", selectedFood.getPrice());

                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Vui lòng chọn món ăn trước khi đặt!", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
