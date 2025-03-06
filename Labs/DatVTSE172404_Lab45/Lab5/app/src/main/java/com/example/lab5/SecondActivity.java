package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> showAddDialog());


        findViewById(R.id.buttonBack).setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit, null);
        builder.setView(dialogView);

        EditText etTitle = dialogView.findViewById(R.id.etTitle);
        EditText etContent = dialogView.findViewById(R.id.etContent);

        builder.setPositiveButton("Add", (dialog, which) -> {
            Item newItem = new Item(
                    etTitle.getText().toString(),
                    etContent.getText().toString()
            );
            itemList.add(newItem);
            adapter.notifyItemInserted(itemList.size() - 1);
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
}