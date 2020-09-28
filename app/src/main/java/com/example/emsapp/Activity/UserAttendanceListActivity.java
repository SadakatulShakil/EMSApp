package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.emsapp.Adapter.AttendanceAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class UserAttendanceListActivity extends AppCompatActivity {

    private RecyclerView userAttendanceListRv;
    private ArrayList<Attendance> mAttendanceArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfArrayList = new ArrayList<>();
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceReference;
    private ProgressBar progressBar;
    private Employee employeeInfo;
    private String userRole, currentMonthName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_attendance_list);

        inItView();
        Intent intent = getIntent();
        employeeInfo = (Employee) intent.getSerializableExtra("employeeInfo");

        userAttendanceListRv.setLayoutManager(new LinearLayoutManager(UserAttendanceListActivity.this));
        attendanceAdapter = new AttendanceAdapter(UserAttendanceListActivity.this, mAttendanceArrayList);
        userAttendanceListRv.setAdapter(attendanceAdapter);

        getUserAttendance();
    }

    private void getUserAttendance() {

        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        attendanceReference = FirebaseDatabase.
                getInstance().
                getReference("Attendance");

        attendanceReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                mAttendanceArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if(employeeInfo.getUserPgId().equals(attendance.getPgId())){
                        mAttendanceArrayList.add(attendance);
                    }
                }
                attendanceAdapter.notifyDataSetChanged();
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
        userAttendanceListRv = findViewById(R.id.recyclerViewForAttendanceList);
        progressBar = findViewById(R.id.progressBar);
    }
}