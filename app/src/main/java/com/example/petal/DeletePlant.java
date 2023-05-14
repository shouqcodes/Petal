package com.example.petal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class DeletePlant extends AppCompatActivity {
    EditText plantName;
    Button deleteplant;


    DBHelper db;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plant);

        plantName = findViewById(R.id.plantname);
        deleteplant = findViewById(R.id.deletebuttonbyname);
        session = new UserSessionManager(this);
        db = new DBHelper(this);

        String username = session.getUserId();


        deleteplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = plantName.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    plantName.setError("Plant name is required");
                    plantName.requestFocus();
                    return;
                }

                String username = session.getUserId();
                if (db.checkPlant(name, username)) {
                    db.DeletePlant(name, username);
                    Toast.makeText(DeletePlant.this, "Your Plant is deleted successfully", Toast.LENGTH_SHORT).show();
                    // Close the activity and return to the previous screen
                    finish();
                } else {
                    plantName.setError("Failed. Make sure you have this plant, or check plant name spelling.");
                }
            }
        });
    }
}