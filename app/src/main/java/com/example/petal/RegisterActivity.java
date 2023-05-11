package com.example.petal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    Button btn;
    EditText username, password, mobile;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn = findViewById(R.id.signupBTN);
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        mobile = findViewById(R.id.register_mobile);
        DB = new DBHelper(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String mobileNum = mobile.getText().toString();

                if (user.equals("") || pass.equals("") || mobileNum.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkUser = DB.checkUsername(user);
                    if (!checkUser) {
                        Boolean insert = DB.registerUser(user, pass, mobileNum);
                        if (insert) {
                            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Already exists! Please sign in or change username.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}