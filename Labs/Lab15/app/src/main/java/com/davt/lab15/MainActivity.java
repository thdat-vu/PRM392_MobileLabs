package com.davt.lab15;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private MaterialButton btnConfirm, btnPlus, btnMinus;
    private EditText etSoLuong;
    private TextView tvTotal;
    private static final double PRICE = 1000000.0;
    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupQuantityControls();
        setupPaymentButton();
    }

    private void initViews() {
        btnConfirm = findViewById(R.id.btnConfirm);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        etSoLuong = findViewById(R.id.etSoLuong);
        tvTotal = findViewById(R.id.tvTotal);
        etSoLuong.setText("1");
        updateTotal();
    }

    private void setupQuantityControls() {
        btnPlus.setOnClickListener(v -> changeQuantity(true));
        btnMinus.setOnClickListener(v -> changeQuantity(false));

        etSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable s) {
                validateQuantity();
            }
        });
    }

    private void changeQuantity(boolean increase) {
        String currentValue = etSoLuong.getText().toString();
        int quantity = currentValue.isEmpty() ? 0 : Integer.parseInt(currentValue);

        if (increase && quantity < MAX_QUANTITY) {
            quantity++;
        } else if (!increase && quantity > MIN_QUANTITY) {
            quantity--;
        }

        etSoLuong.setText(String.valueOf(quantity));
    }

    private void validateQuantity() {
        String input = etSoLuong.getText().toString();
        if (!input.isEmpty()) {
            int quantity = Integer.parseInt(input);
            if (quantity < MIN_QUANTITY) {
                etSoLuong.setText(String.valueOf(MIN_QUANTITY));
            } else if (quantity > MAX_QUANTITY) {
                etSoLuong.setText(String.valueOf(MAX_QUANTITY));
                Toast.makeText(this, "Số lượng tối đa là " + MAX_QUANTITY, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupPaymentButton() {
        btnConfirm.setOnClickListener(v -> {
            String soLuong = etSoLuong.getText().toString();
            if (soLuong.isEmpty()) {
                etSoLuong.setError("Vui lòng nhập số lượng");
                return;
            }

            double total = Double.parseDouble(soLuong) * PRICE;
            Intent intent = new Intent(MainActivity.this, OrderPayment.class);
            intent.putExtra("soluong", soLuong);
            intent.putExtra("total", total);
            startActivity(intent);
        });
    }

    private void updateTotal() {
        String soLuong = etSoLuong.getText().toString();
        if (!soLuong.isEmpty()) {
            double total = Double.parseDouble(soLuong) * PRICE;
            tvTotal.setText(String.format("Tổng tiền: %,.0f VNĐ", total));
        } else {
            tvTotal.setText("Tổng tiền: 0 VNĐ");
        }
    }
}