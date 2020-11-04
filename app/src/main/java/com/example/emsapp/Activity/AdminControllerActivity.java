package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.MainActivity;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminControllerActivity extends AppCompatActivity {
    private RecyclerView employeeRecyclerView;
    private FloatingActionButton addEmployeeBtn;
    private TextView logOutBtn;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    private ProgressBar progressBar;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);
        iniItView();
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();

        employeeRecyclerView.setLayoutManager(new LinearLayoutManager(AdminControllerActivity.this));
        mEmployeeAdapter = new EmployeeAdapter(AdminControllerActivity.this, employeeInfoList);
        employeeRecyclerView.setAdapter(mEmployeeAdapter);

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
                preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                preferences.edit().putString("TOKEN",null).apply();
                finish();
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(AdminControllerActivity.this, UserSignInActivity.class);
                startActivity(intent);
            }
        });

        getAndShowEmployeeList();
    }

    private void getAndShowEmployeeList() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");


        employeeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Employee employeeInfo = userSnapshot.getValue(Employee.class);

                    employeeInfoList.add(employeeInfo);
                }
                mEmployeeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniItView() {
        employeeRecyclerView = findViewById(R.id.recyclerViewForEmployeeList);
        addEmployeeBtn = findViewById(R.id.addEmployeeFAB);
        logOutBtn = findViewById(R.id.logOutBt);
        progressBar = findViewById(R.id.progressBar);

    }
}