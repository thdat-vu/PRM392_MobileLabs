package com.example.lab3_1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declare variables
    ListView lvMonHoc;
    ArrayList<String> arrayCourse;

    EditText etMonHoc;
    Button btnAdd;
    Button btnUpdate;
    Button btnDelete;

    int position = -1;
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


        lvMonHoc=(ListView) findViewById(R.id.lvMonHoc);
        etMonHoc=(EditText) findViewById(R.id.etMonHoc);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnUpdate=(Button) findViewById(R.id.btnUpdate);
        btnDelete=(Button) findViewById(R.id.btnDelete);

        arrayCourse = new ArrayList<>();
        arrayCourse.add("Android");
        arrayCourse.add("PHP");
        arrayCourse.add("iOS");
        arrayCourse.add("Unity");
        arrayCourse.add("ASP.net");

        //declare array adapter

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayCourse);
        //set adapter
        lvMonHoc.setAdapter(adapter);

        lvMonHoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast
                Toast.makeText(MainActivity.this, "Bạn chọn " + arrayCourse.get(i), Toast.LENGTH_SHORT).show();
                position = i;
                etMonHoc.setText(arrayCourse.get(i));
            }
        });

        lvMonHoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrayCourse.remove(i);
                //reset the list
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = etMonHoc.getText().toString();
                if(course.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập môn học", Toast.LENGTH_SHORT).show();
                }else{
                    arrayCourse.remove(course);
                    etMonHoc.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = etMonHoc.getText().toString();
                if(course.isEmpty()){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập môn học", Toast.LENGTH_SHORT).show();
                }else{
                    arrayCourse.add(course);
                    etMonHoc.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course = etMonHoc.getText().toString();
                if(course.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập môn học", Toast.LENGTH_SHORT).show();
                }
                else {
                    arrayCourse.set(position, course);
                    etMonHoc.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}