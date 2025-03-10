package com.davt.lab10;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.davt.lab10.constants.Constants;
import com.davt.lab10.db.AppDatabase;
import com.davt.lab10.executors.AppExecutors;
import com.davt.lab10.model.Person;

public class EditPersonActivity extends AppCompatActivity {

    private EditText etFirstName;
    private EditText etLastName;
    private Button btnSave;
    private int mPersonId;
    private Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_edit);

        // Add null check before accessing ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initViews();

        mDb = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "app-database")
                .allowMainThreadQueries() // Add this for development
                .build();

        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            btnSave.setText("Update");
            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    final Person person = mDb.personDao().loadPersonById(mPersonId);
                    // Use runOnUiThread to update UI elements
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            populateUI(person);
                        }
                    });
                }
            });
        }
    }

    private void initViews() {
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    // In the onSaveButtonClicked method
    public void onSaveButtonClicked() {
        // Get the text from the EditText fields
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        
        // Validate that both fields are filled
        if (firstName.isEmpty()) {
            etFirstName.setError("First name is required");
            return;
        }
        
        if (lastName.isEmpty()) {
            etLastName.setError("Last name is required");
            return;
        }
        
        // Create a new person with the validated input
        final Person person = new Person(firstName, lastName);
    
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    // This is a new person, insert it
                    mDb.personDao().insert(person);
                } else {
                    // This is an existing person, update it
                    person.setUid(mPersonId);
                    mDb.personDao().update(person);
                }
                finish();
            }
        });
    }

    private void populateUI(Person person) {
        if (person == null) {
            return;
        }
        etFirstName.setText(person.getFirstName());
        etLastName.setText(person.getLastName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}