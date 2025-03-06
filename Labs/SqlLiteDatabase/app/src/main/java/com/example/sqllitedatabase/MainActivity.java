package com.example.sqllitedatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Database database;
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

        database = new Database(this, "GhiChu.sqlite", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TenCV nvarchar(200))");

        Cursor dataCongViec = database.getData("SELECT * FROM CongViec");
        while (dataCongViec.moveToNext()) {
            String tenCV = dataCongViec.getString(1);
            Toast.makeText(this, tenCV, Toast.LENGTH_SHORT).show();
        }

    }
}