package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.emsapp.MainActivity;
import com.example.emsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class AdminControllerActivity extends AppCompatActivity {
    private RecyclerView employeeRecyclerView;
    private FloatingActionButton addEmployeeBtn;
    private TextView logOutBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);
        iniItView();

        addEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminControllerActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                firebaseAuth.signOut();
                Intent intent = new Intent(AdminControllerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniItView() {
        employeeRecyclerView = findViewById(R.id.recyclerViewForEmployeeList);
        addEmployeeBtn = findViewById(R.id.addEmployeeFAB);
        logOutBtn = findViewById(R.id.logOutBtn);

    }
}