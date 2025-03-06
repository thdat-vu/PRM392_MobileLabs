package com.example.lab3_3;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvPlayer;
    ArrayList<Player> playerList;
    Button btnAdd, btnEdit, btnDelete, btnSelectImage;
    EditText edtName, edtTeam, edtNationality;
    ImageView imgvFlag;
    int selectedPosition = -1;
    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    PlayerAdapter adapter;
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
        initListView();
        adapter = new PlayerAdapter(this, R.layout.player_item, playerList);
        lvPlayer.setAdapter(adapter);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnEdit = (Button) findViewById(R.id.buttonEdit);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        edtName = (EditText) findViewById(R.id.edtName);
        edtTeam = (EditText) findViewById(R.id.edtTeamMoTa);
        edtNationality = (EditText) findViewById(R.id.edtNationality);
        imgvFlag = (ImageView) findViewById(R.id.imgvFlag);
        btnSelectImage = (Button) findViewById(R.id.buttonSelectImage);

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
        lvPlayer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPosition = i;
                Player selectedTraiCay = playerList.get(i);
                edtName.setText(selectedTraiCay.getName());
                edtTeam.setText(selectedTraiCay.getTeam());
                edtNationality.setText(selectedTraiCay.getNationality());
                if (selectedTraiCay.getImageUri().startsWith("content://")) {
                    imgvFlag.setImageURI(Uri.parse(selectedTraiCay.getImageUri()));
                }
                else {
                    int resId = Integer.parseInt(selectedTraiCay.getImageUri());
                    imgvFlag.setImageResource(resId);
                }
                selectedImageUri = Uri.parse(selectedTraiCay.getImageUri());
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

    private void initListView() {
        lvPlayer = findViewById(R.id.listViewPlayer);
        playerList = new ArrayList<>();
        playerList.add(new Player("Lionel Messi", "Inter Miami", "Argentina", String.valueOf(R.drawable.argentina)));
        playerList.add(new Player("Christiano Ronaldo", "Al-Nassr FC", "Portugal", String.valueOf(R.drawable.portugal)));

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
            imgvFlag.setImageURI(selectedImageUri);
        }
    }
    private void clearFields() {
        edtName.setText("");
        edtTeam.setText("");
        edtNationality.setText("");
        imgvFlag.setImageResource(R.drawable.ic_launcher_foreground);
        selectedImageUri = null;
    }

    private void addItem() {
        String name = edtName.getText().toString();
        String team = edtTeam.getText().toString();
        String nationality = edtNationality.getText().toString();
        String imageFlag = String.valueOf(R.drawable.ic_launcher_foreground);
        if(selectedImageUri !=null){
            imageFlag = selectedImageUri.toString();
        }
        if (name.isEmpty() || team.isEmpty() || nationality.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên, mô tả và quốc tịch", Toast.LENGTH_SHORT).show();
            return;
        }
        playerList.add(new Player(name, team, nationality, imageFlag));
        adapter.notifyDataSetChanged();
        clearFields();
    }

    private void updateItem() {
        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn một mục để câp nhật", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = edtName.getText().toString();
        String team = edtTeam.getText().toString();
        String nationality = edtNationality.getText().toString();
        String imageFlag = String.valueOf(R.drawable.ic_launcher_foreground);
        if(selectedImageUri !=null){
            imageFlag = selectedImageUri.toString();
        }
        if (name.isEmpty() || team.isEmpty() || nationality.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền tên và mô tả", Toast.LENGTH_SHORT).show();
            return;
        }
        Player traiCay = playerList.get(selectedPosition);
        traiCay.setName(name);
        traiCay.setTeam(team);
        traiCay.setNationality(nationality);
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
                playerList.remove(selectedPosition);
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
}