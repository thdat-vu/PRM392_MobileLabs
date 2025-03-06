package com.davt.lab15;


import java.text.NumberFormat;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.davt.lab15.Api.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderPayment extends AppCompatActivity {

    TextView tvSoluong, tvTongTien;
    Button btnThanhToan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_payment);

        tvSoluong = findViewById(R.id.tvSoLuong);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Intent intent = getIntent();
        tvSoluong.setText(intent.getStringExtra("soluong"));
        Double total = intent.getDoubleExtra("total", 0);

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String totalString = String.format("%.0f", total);
        String totalFormatted = formatter.format(total);
        tvTongTien.setText(totalFormatted);

        btnThanhToan.setOnClickListener(v -> {
            CreateOrder orderApi = new CreateOrder();
            try {
                JSONObject data = orderApi.createOrder(totalString);
                String code = data.getString("return_code");

                if (code.equals("1")) {
                    String token = data.getString("zp_trans_token");
                    ZaloPaySDK.getInstance().payOrder(OrderPayment.this, token, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(String s, String s1, String s2) {
                            Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent1.putExtra("result", "Thanh toán thành công");
                            intent1.putExtra("total", "Bạn đã thanh toán " + totalFormatted);
                            startActivity(intent1);
                        }

                        @Override
                        public void onPaymentCanceled(String s, String s1) {
                            Intent intent2 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent2.putExtra("result",  "Thanh toán đã được hủy");
                            startActivity(intent2);
                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                            Intent intent3 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent3.putExtra("result", "Lỗi thanh toán");
                            startActivity(intent3);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}