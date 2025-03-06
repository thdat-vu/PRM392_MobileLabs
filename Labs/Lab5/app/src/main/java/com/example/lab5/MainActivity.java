package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> userList = createUserList();

        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        findViewById(R.id.buttonNext).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private List<User> createUserList() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            userList.add(new User("DatVT", "Vu Thanh Dat", "datvtse@gmail.com"));
            userList.add(new User("KhoiPM", "Pham Minh Khoi", "khoipmse@gmail.com"));
            userList.add(new User("TungVT", "Vo Thanh Tung", "tungvtse@gmail.com"));
            userList.add(new User("GiangDQ", "Dam Quang Giang", "giangdq@gmail.com"));
            userList.add(new User("DucLM", "Le Minh Duc", "duclm@gmail.com"));
        }
        return userList;
    }
}