package com.example.lab3_2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView lvTraicay;

    ArrayList<TraiCay> arrayTraiCay;

    Button btnAdd, btnEdit, btnDelete, btnSelectImage;
    EditText edtTen, edtMoTa;
    ImageView imgHinh;
    int selectedPosition = -1;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    TraiCayAdapter adapter;
    private Uri selectedImageUri;

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
        AnhXa();
        adapter = new TraiCayAdapter(this, R.layout.dong_trai_cay, arrayTraiCay);
        lvTraicay.setAdapter(adapter);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnEdit = (Button) findViewById(R.id.buttonEdit);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtMoTa = (EditText) findViewById(R.id.edtMoTa);
        imgHinh = (ImageView) findViewById(R.id.imageviewHinh);
        btnSelectImage = (Button) findViewById(R.id.buttonSelectImage);

        //CRUD:
        //Add item

        // Add item
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });

         // Update item
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem();
            }
        });

        // Delete item
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        // Select item
        lvTraicay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = i;
                TraiCay selectedTraiCay = arrayTraiCay.get(i);
                edtTen.setText(selectedTraiCay.getTen());
                edtMoTa.setText(selectedTraiCay.getMota());
                if (selectedTraiCay.getHinh().startsWith("content://")) {
                    imgHinh.setImageURI(Uri.parse(selectedTraiCay.getHinh()));
                }
                else {
                    int resId = Integer.parseInt(selectedTraiCay.getHinh());
                    imgHinh.setImageResource(resId);
                }
                selectedImageUri = Uri.parse(selectedTraiCay.getHinh());
            }
        });
         // Select image
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void AnhXa(){
        lvTraicay = (ListView) findViewById(R.id.listViewTraiCay);
        arrayTraiCay = new ArrayList<>();
        arrayTraiCay.add(new TraiCay("Chuối Tiêu", "Chuối tiêu Long An", String.valueOf(R.drawable.chuoi)));
        arrayTraiCay.add(new TraiCay("Dưa Hấu", "Dưa Hấu Long An", String.valueOf(R.drawable.duahau)));
//        arrayTraiCay.add(new TraiCay("Chuối Tiêu", "Chuối tiêu Long An", ""));
//        arrayTraiCay.add(new TraiCay("Dưa Hấu", "Dưa Hấu Long An", ""));
    }


    private void addItem() {
        String ten = edtTen.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String hinh = String.valueOf(R.drawable.ic_launcher_foreground);
        if(selectedImageUri !=null){
            hinh = selectedImageUri.toString();
        }
        if (ten.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên và mô tả", Toast.LENGTH_SHORT).show();
            return;
        }
        arrayTraiCay.add(new TraiCay(ten, moTa, hinh));
        adapter.notifyDataSetChanged();
        clearFields();
    }

    private void updateItem() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn một mục để câp nhật", Toast.LENGTH_SHORT).show();
            return;
        }
        String ten = edtTen.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String hinh = String.valueOf(R.drawable.ic_launcher_foreground);
        if(selectedImageUri !=null){
            hinh = selectedImageUri.toString();
        }
        if (ten.isEmpty() || moTa.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên và mô tả", Toast.LENGTH_SHORT).show();
            return;
        }
        TraiCay traiCay = arrayTraiCay.get(selectedPosition);
        traiCay.setTen(ten);
        traiCay.setMota(moTa);
        traiCay.setHinh(hinh);
        adapter.notifyDataSetChanged();
        clearFields();
        selectedPosition = -1;
    }

    private void deleteItem() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Chọn một mục để xóa", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có muốn xóa mục này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                arrayTraiCay.remove(selectedPosition);
                adapter.notifyDataSetChanged();
                clearFields();
                selectedPosition = -1;
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });
        builder.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imgHinh.setImageURI(selectedImageUri);
        }
    }
    private void clearFields() {
        edtTen.setText("");
        edtMoTa.setText("");
        imgHinh.setImageResource(R.drawable.ic_launcher_foreground);
        selectedImageUri = null;
    }
}