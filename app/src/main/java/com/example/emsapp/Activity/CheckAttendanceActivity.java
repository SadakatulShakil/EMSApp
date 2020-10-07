package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.emsapp.Adapter.AttendanceAdapter;
import com.example.emsapp.Adapter.UserListForAttendanceAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CheckAttendanceActivity extends AppCompatActivity {
    private RecyclerView selfAttendance, userAttendance;
    private LinearLayout attendanceCheckLayout;
    private String userRole, currentMonthName;
    private ArrayList<Attendance> mAttendanceArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfArrayList = new ArrayList<>();
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceReference;
    private ProgressBar progressBar;
    private Employee employeeIn;
    private Button selAttendanceBt, userAttendanceBt;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private UserListForAttendanceAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    public static final String TAG = "attendance";
    private ImageView historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_attendance);
        inItView();
        Intent intent = getIntent();
        userRole = intent.getStringExtra("userRole");
        employeeIn = (Employee) intent.getSerializableExtra("userInfo");

        selfAttendance.setLayoutManager(new LinearLayoutManager(CheckAttendanceActivity.this));
        attendanceAdapter = new AttendanceAdapter(CheckAttendanceActivity.this, mAttendanceArrayList);
        selfAttendance.setAdapter(attendanceAdapter);

        userAttendance.setLayoutManager(new LinearLayoutManager(CheckAttendanceActivity.this));
        mEmployeeAdapter = new UserListForAttendanceAdapter(CheckAttendanceActivity.this, employeeInfoList);
        userAttendance.setAdapter(mEmployeeAdapter);

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CheckAttendanceActivity.this, AttendanceHistoryActivity.class);
                intent1.putExtra("userInfo", employeeIn);
                startActivity(intent1);
            }
        });

        if (userRole.equals("General Employee")) {
            progressBar.setVisibility(View.VISIBLE);
            selfAttendance.setVisibility(View.VISIBLE);
            getSelfAttendance();
        } else if (userRole.equals("General Manager") || userRole.equals("Managing Director")) {
            progressBar.setVisibility(View.VISIBLE);
            attendanceCheckLayout.setVisibility(View.VISIBLE);
            selfAttendance.setVisibility(View.VISIBLE);
            getSelfAttendance();

            selAttendanceBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selfAttendance.setVisibility(View.VISIBLE);
                    userAttendance.setVisibility(View.GONE);
                    getSelfAttendance();
                }
            });
            userAttendanceBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selfAttendance.setVisibility(View.GONE);
                    userAttendance.setVisibility(View.VISIBLE);
                    getUserAttendance();
                }
            });

        }
    }

    private void getSelfAttendance() {
        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        attendanceReference = FirebaseDatabase.
                getInstance().
                getReference("Attendance")
                .child(currentMonthName);

        attendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAttendanceArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employeeIn.getUserPgId().equals(attendance.getPgId())) {
                        mAttendanceArrayList.add(attendance);
                        Log.d(TAG, "onChildAdded: " + mAttendanceArrayList.size());

                    }
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

    private void inItView() {
        selfAttendance = findViewById(R.id.recyclerViewForAttendanceList);
        userAttendance = findViewById(R.id.recyclerViewForUserAttendanceList);
        progressBar = findViewById(R.id.progressBar);
        attendanceCheckLayout = findViewById(R.id.checkAttendance);
        selAttendanceBt = findViewById(R.id.selfAttendance);
        userAttendanceBt = findViewById(R.id.userAttendance);
        historyBtn = findViewById(R.id.attendanceHistory);
    }
}