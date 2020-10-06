package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.emsapp.Adapter.MovableAdapter;
import com.example.emsapp.Adapter.UserListForMovementAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CheckMovableEventsActivity extends AppCompatActivity {
    private RecyclerView selfMovementRv, userMovementRv;
    private LinearLayout movementCheckLayout;
    private String userRole, currentMonthName;
    private ArrayList<Attendance> mMovementArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfMovementList = new ArrayList<>();
    private MovableAdapter movementAdapter;
    private DatabaseReference movementReference;
    private ProgressBar progressBar;
    private Employee employeeInfo;
    private Button selfMovementBt, userMovementBt;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private UserListForMovementAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    private ImageView historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_movable_events);

        inItView();
        Intent intent = getIntent();
        userRole = intent.getStringExtra("userRole");
        employeeInfo = (Employee) intent.getSerializableExtra("userInfo");

        selfMovementRv.setLayoutManager(new LinearLayoutManager(CheckMovableEventsActivity.this));
        movementAdapter = new MovableAdapter(CheckMovableEventsActivity.this, mMovementArrayList);
        selfMovementRv.setAdapter(movementAdapter);

        userMovementRv.setLayoutManager(new LinearLayoutManager(CheckMovableEventsActivity.this));
        mEmployeeAdapter = new UserListForMovementAdapter(CheckMovableEventsActivity.this, employeeInfoList);
        userMovementRv.setAdapter(mEmployeeAdapter);

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CheckMovableEventsActivity.this, MovementHistoryActivity.class);
                intent1.putExtra("userInfo", employeeInfo);
                startActivity(intent1);
            }
        });

        if (userRole.equals("General Employee")) {
            progressBar.setVisibility(View.VISIBLE);
            selfMovementRv.setVisibility(View.VISIBLE);
            getSelfMovement();
        } else if (userRole.equals("General Manager") && userRole.equals("Managing Director")) {
            progressBar.setVisibility(View.VISIBLE);
            movementCheckLayout.setVisibility(View.VISIBLE);
            selfMovementRv.setVisibility(View.VISIBLE);
            getSelfMovement();

            selfMovementBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selfMovementRv.setVisibility(View.VISIBLE);
                    userMovementRv.setVisibility(View.GONE);
                    getSelfMovement();
                }
            });

            userMovementBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selfMovementRv.setVisibility(View.GONE);
                    userMovementRv.setVisibility(View.VISIBLE);
                    getUserMovement();
                }
            });
        }

    }

    private void getSelfMovement() {
        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        movementReference = FirebaseDatabase.
                getInstance().
                getReference("MovableReport")
                .child(currentMonthName);
        movementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMovementArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employeeInfo.getUserPgId().equals(attendance.getPgId())) {
                        mMovementArrayList.add(attendance);
                    }
                }
                movementAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserMovement() {
        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");
        employeeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                employeeInfoList.clear();
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

    private void inItView() {
        selfMovementRv = findViewById(R.id.recyclerViewForMovementList);
        userMovementRv = findViewById(R.id.recyclerViewForUserMovementList);
        progressBar = findViewById(R.id.progressBar);
        movementCheckLayout = findViewById(R.id.checkMovement);
        selfMovementBt = findViewById(R.id.selfMovement);
        userMovementBt = findViewById(R.id.userMovement);
        historyBtn = findViewById(R.id.movementHistory);
    }
}