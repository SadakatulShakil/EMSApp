package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.emsapp.Adapter.AttendanceAdapter;
import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CheckAttendanceActivity extends AppCompatActivity {
    private RecyclerView selfAttendance, userAttendance;
    private String userRole, currentMonthName;
    private ArrayList<Attendance> mAttendanceArrayList = new ArrayList<>();;
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceReference;
    private ProgressBar progressBar;
    private Employee employeeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);
        inItView();
        Intent intent = getIntent();
        userRole = intent.getStringExtra("userRole");
        employeeInfo = (Employee) intent.getSerializableExtra("userInfo");

        selfAttendance.setLayoutManager(new LinearLayoutManager(CheckAttendanceActivity.this));
        attendanceAdapter = new AttendanceAdapter(CheckAttendanceActivity.this, mAttendanceArrayList);
        selfAttendance.setAdapter(attendanceAdapter);

        if(userRole.equals("General Employee")){
            progressBar.setVisibility(View.VISIBLE);
            selfAttendance.setVisibility(View.VISIBLE);
            getSelfAttendance();
        }
        else if(userRole.equals("General Manager")){
            userAttendance.setVisibility(View.VISIBLE);
            getUserAttendance();
        }
    }

    private void getSelfAttendance() {
        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        attendanceReference = FirebaseDatabase.
                getInstance().
                getReference("Attendance").
                child(currentMonthName);

        Query query = attendanceReference.orderByChild("pgId").limitToLast(1).equalTo(employeeInfo.getUserPgId());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mAttendanceArrayList.clear();
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Attendance attendance = userSnapshot.getValue(Attendance.class);
                        mAttendanceArrayList.add(attendance);
                    }
                    attendanceAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    private void getUserAttendance() {

    }

    private void inItView() {
        selfAttendance = findViewById(R.id.recyclerViewForAttendanceList);
        userAttendance = findViewById(R.id.recyclerViewForUserAttendanceList);
        progressBar = findViewById(R.id.progressBar);
    }
}