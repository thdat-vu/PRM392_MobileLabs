package com.example.lab9;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    ListView lvCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCongViec = (ListView) findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();
        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        //Tao database GhiChu
        database = new Database(this, "GhiChu.sqlite", null, 1);

        // Tao table CongViec
        database.QueryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement, " +
                "tenCV nvarchar(200))");

        // Check if data exists before inserting
        Cursor checkData = database.GetData("SELECT * FROM CongViec WHERE tenCV IN ('Project Android', 'Design app')");
        if (checkData.getCount() == 0) {
            // Insert initial data only if no records exist
            database.QueryData("Insert into CongViec values(null, 'Project Android')");
            database.QueryData("Insert into CongViec values(null, 'Design app')");
        }

        // Load data using the GetDataCongViec method
        GetDataCongViec();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menuAdd) {
            DialogItem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogItem(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);
        
        EditText edtTen = (EditText) dialog.findViewById(R.id.editTextTenCV);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonThem);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);
        
        // Set click listener for Add button
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = edtTen.getText().toString().trim();
                // Check if input is empty, blank, or just whitespace
                if(tencv.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc !", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("Insert into CongViec values(null, '"+tencv+"')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Close dialog after adding
                    // Refresh data in ListView
                    GetDataCongViec();
                }
            }
        });
        
        // Set click listener for Cancel button
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        
        dialog.show();
    }
    
    // Method to refresh data in ListView
    private void GetDataCongViec(){
        // Clear current data
        arrayCongViec.clear();
        
        // Get updated data from database
        Cursor dataCongViec = database.GetData("Select * from CongViec");
        while (dataCongViec.moveToNext()) {
            int id = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            arrayCongViec.add(new CongViec(id, ten));
        }
        
        // Update ListView
        adapter.notifyDataSetChanged();
    }
    
    // Ham xoa cong viec
    public void DialogXoaCongViec(String tencv, int Id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc " + tencv + " không ? ");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ Id +"'");
                Toast.makeText(MainActivity.this, "Đã xóa "+ tencv, Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });
        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                
            }
        });
        dialogXoa.show();
    }
    
    // Ham cap nhat cong viec
    public void DialogSuaCongViec(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);
        
        EditText edtTenCV = (EditText) dialog.findViewById(R.id.editTextTenCV);
        Button btnXacNhan = (Button) dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHuy);
        
        edtTenCV.setText(ten);
        
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edtTenCV.getText().toString().trim();
                // Check if input is empty, blank, or just whitespace
                if(tenMoi.isEmpty()){
                    Toast.makeText(MainActivity.this, "Tên công việc không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("UPDATE CongViec SET TenCV = '"+ tenMoi +"' WHERE id = '"+ id +"'");
                    Toast.makeText(MainActivity.this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });
        
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss(); }
        });
        
        dialog.show();
    }
}