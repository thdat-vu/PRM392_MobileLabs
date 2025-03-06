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

public class DrinkActivity extends AppCompatActivity {
    private static final String TAG = "DrinkActivity";
    private ListView lvDrink;
    private Button btnDatMon;
    private List<DrinkItem> drinkList;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        lvDrink = findViewById(R.id.lvDrink);
        btnDatMon = findViewById(R.id.btnDatMon);

        // Tạo danh sách thức uống
        drinkList = new ArrayList<>();
        drinkList.add(new DrinkItem("Pepsi", "Nước ngọt Pepsi", 10000, R.drawable.pepsi));
        drinkList.add(new DrinkItem("Heineken", "Bia Heineken lon", 20000, R.drawable.heineken));
        drinkList.add(new DrinkItem("Tiger", "Bia Tiger lon", 18000, R.drawable.tiger));
        drinkList.add(new DrinkItem("Sài Gòn Đỏ", "Bia Sài Gòn Đỏ", 15000, R.drawable.saigon));

        // Gán adapter
        DrinkAdapter adapter = new DrinkAdapter(this, drinkList);
        lvDrink.setAdapter(adapter);

        // Bắt sự kiện chọn item
        lvDrink.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        // Xử lý nút "Đặt món"
        btnDatMon.setOnClickListener(v -> {
            if (selectedPosition >= 0) {
                DrinkItem selectedDrink = drinkList.get(selectedPosition);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("drinkName", selectedDrink.getName());
                resultIntent.putExtra("drinkPrice", selectedDrink.getPrice());

                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, "Vui lòng chọn đồ uống trước khi đặt!", Toast.LENGTH_SHORT).show();
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
