package com.example.petal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddPlant extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private EditText etSize;
    private EditText etSunExposure;
    private EditText etSoilType;
    private ImageView ivImage;
    private Button btnSelectImage;
    private Button btnSavePlant;
    private DBHelper db;
    private Bitmap selectedImage;
    UserSessionManager session ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        etName = findViewById(R.id.editTextPlantName);
        etDescription = findViewById(R.id.editTextDescription);
        etSize = findViewById(R.id.editTextSize);
        etSunExposure = findViewById(R.id.editTextSunExposure);
        etSoilType = findViewById(R.id.editTextSoilType);
        ivImage = findViewById(R.id.imageViewPlantImage);
        btnSelectImage = findViewById(R.id.buttonUpload);
        session = new UserSessionManager(this);
        btnSavePlant = findViewById(R.id.buttonSavePlant);
        db = new DBHelper(this);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnSavePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the plant data to the database
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String size = etSize.getText().toString();
                String sunExposure = etSunExposure.getText().toString();
                String soilType = etSoilType.getText().toString();

                // Validate plant name and image
                if (TextUtils.isEmpty(name)) {
                    etName.setError("Plant name is required");
                    etName.requestFocus();
                    return;
                }
                if (selectedImage == null) {
                    Toast.makeText(AddPlant.this, "Please select an image for the plant", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convert the selected image to a byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                String username = session.getUserId();
                Plant plant = new Plant(name, description, size, sunExposure, soilType, imageBytes, username);
                db.insertPlant(plant);

                // Close the activity and return to the previous screen
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the selected image and display it in the image view
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ivImage.setImageBitmap(selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}