package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.emsapp.Adapter.MovableAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserMovementListActivity extends AppCompatActivity {
    private RecyclerView userMovementListRv;
    private ArrayList<Attendance> mAttendanceArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfArrayList = new ArrayList<>();
    private MovableAdapter userMovementAdapter;
    private DatabaseReference movementReference;
    private ProgressBar progressBar;
    private Employee employeeInfo;
    private String userRole, currentMonthName;
    private ImageView movementHistoryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_movement_list);

        inItView();
        Intent intent = getIntent();
        employeeInfo = (Employee) intent.getSerializableExtra("employeeInfo");

        userMovementListRv.setLayoutManager(new LinearLayoutManager(UserMovementListActivity.this));
        userMovementAdapter = new MovableAdapter(UserMovementListActivity.this, mAttendanceArrayList);
        userMovementListRv.setAdapter(userMovementAdapter);

        getUserMovement();

        movementHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(UserMovementListActivity.this, CheckUserMovementHistoryActivity.class);
                intent1.putExtra("userInfo", employeeInfo);
                startActivity(intent1);
            }
        });

    }

    private void getUserMovement() {

        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        movementReference = FirebaseDatabase.
                getInstance().
                getReference("MovableReport")
                .child(currentMonthName);
        movementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAttendanceArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if(employeeInfo.getUserPgId().equals(attendance.getPgId())){
                        mAttendanceArrayList.add(attendance);
                    }
                }
                userMovementAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inItView() {
        userMovementListRv = findViewById(R.id.recyclerViewForMovementList);
        progressBar = findViewById(R.id.progressBar);
        movementHistoryBtn = findViewById(R.id.movementHistory);
    }
}