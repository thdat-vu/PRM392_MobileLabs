package com.davt.lab6optiom_menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private Button btnOptionsMenu;
    private Button btnPopupMenu;
    private Button btnChonMau;
    private ConstraintLayout manHinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize buttons and layout
        btnOptionsMenu = findViewById(R.id.btnOptionsMenu);
        btnPopupMenu = findViewById(R.id.btnPopupMenu);
        btnChonMau = findViewById(R.id.btnChonMau);
        manHinh = findViewById(R.id.manHinh);
        
        // Set click listeners
        btnOptionsMenu.setOnClickListener(v -> {
            openOptionsMenu();
        });
        
        btnPopupMenu.setOnClickListener(v -> {
            showPopupMenu();
        });
        
        // Register the button for context menu
        registerForContextMenu(btnChonMau);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        
        if (id == R.id.menu_share) {
            showToast("Bạn đã chọn item Share");
            return true;
        } else if (id == R.id.menu_search) {
            showToast("Bạn đã chọn item Search");
            return true;
        } else if (id == R.id.submenu_email) {
            showToast("Bạn đã chọn item Email");
            return true;
        } else if (id == R.id.submenu_phone) {
            showToast("Bạn đã chọn item Phone");
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.menuDo) {
            manHinh.setBackgroundColor(Color.RED);
        } else if (id == R.id.menuVang) {
            manHinh.setBackgroundColor(Color.YELLOW);
        } else if (id == R.id.menuXanh) {
            manHinh.setBackgroundColor(Color.BLUE);
        }
        
        return super.onContextItemSelected(item);
    }
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    public void openOptionsMenu() {
        // Open the options menu programmatically
        super.openOptionsMenu();
    }
    
    private void showPopupMenu() {
        // Create a PopupMenu attached to the button
        PopupMenu popupMenu = new PopupMenu(this, btnPopupMenu);
        
        // Inflate the menu resource
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        
        // Set click listener for menu items
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.menuThem) {
                btnPopupMenu.setText("MENU THEM");
                return true;
            } else if (id == R.id.menuSua) {
                btnPopupMenu.setText("MENU SUA");
                return true;
            } else if (id == R.id.menuXoa) {
                btnPopupMenu.setText("MENU XOA");
                return true;
            }
            return false;
        });
        
        // Show the popup menu
        popupMenu.show();
    }
}