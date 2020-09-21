package com.example.emsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.emsapp.Activity.AdminControllerActivity;
import com.example.emsapp.Activity.UserSignInActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button adminBtn, userBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inItView();
        clickEvents();

    }

    private void clickEvents() {
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = "admin";
                Intent intent = new Intent(MainActivity.this, UserSignInActivity.class);
                intent.putExtra("user", userType);
                startActivity(intent);
            }
        });
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = "employee";
                Intent intent = new Intent(MainActivity.this, UserSignInActivity.class);
                intent.putExtra("user", userType);
                startActivity(intent);
            }
        });
    }

    private void inItView() {
        adminBtn = findViewById(R.id.adminUser);
        userBtn = findViewById(R.id.employeeUser);
    }
}