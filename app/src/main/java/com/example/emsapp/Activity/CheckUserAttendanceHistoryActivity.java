package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.AttendanceAdapter;
import com.example.emsapp.Adapter.MonthAdapter;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Month;
import com.example.emsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckUserAttendanceHistoryActivity extends AppCompatActivity {
    private RecyclerView attendanceHistoryList;
    private Spinner monthSpinner;
    private ArrayList<Month> monthArrayList;
    private TextView nullMessage;
    private MonthAdapter monthAdapter;
    private String monthName;
    private Employee employeeInfo;
    private ArrayList<Attendance> mAttendanceArrayList = new ArrayList<>();
    private ArrayList<Attendance> mSelfArrayList = new ArrayList<>();
    private AttendanceAdapter attendanceAdapter;
    private DatabaseReference attendanceReference;
    public static final String TAG = "history";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_attendance_history);
        inItList();
        inItView();
        Intent intent = getIntent();
        employeeInfo = (Employee) intent.getSerializableExtra("userInfo");

        attendanceHistoryList.setLayoutManager(new LinearLayoutManager(CheckUserAttendanceHistoryActivity.this));
        attendanceAdapter = new AttendanceAdapter(CheckUserAttendanceHistoryActivity.this, mAttendanceArrayList);
        attendanceHistoryList.setAdapter(attendanceAdapter);
    }

    private void inItList() {
        monthArrayList = new ArrayList<>();
        monthArrayList.add(new Month("Select Month.."));
        monthArrayList.add(new Month("January"));
        monthArrayList.add(new Month("February"));
        monthArrayList.add(new Month("March"));
        monthArrayList.add(new Month("April"));
        monthArrayList.add(new Month("May"));
        monthArrayList.add(new Month("June"));
        monthArrayList.add(new Month("July"));
        monthArrayList.add(new Month("August"));
        monthArrayList.add(new Month("September"));
        monthArrayList.add(new Month("October"));
        monthArrayList.add(new Month("November"));
        monthArrayList.add(new Month("December"));
    }

    private void inItView() {
        attendanceHistoryList = findViewById(R.id.recyclerViewForAttendanceHistoryList);
        nullMessage = findViewById(R.id.nullDataTv);
        monthSpinner = findViewById(R.id.monthSpinner);
        monthAdapter = new MonthAdapter(CheckUserAttendanceHistoryActivity.this, monthArrayList);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Month clickedMonth = (Month) parent.getItemAtPosition(position);

                monthName = clickedMonth.getMonthName();
                if(monthName.equals("Select Month..")){
                    Toast.makeText(CheckUserAttendanceHistoryActivity.this, "Please Select Your desire month first", Toast.LENGTH_SHORT).show();
                }else{

                    getDesireAttendanceList(monthName);
                }


                //Toast.makeText(AttendanceHistoryActivity.this, monthName +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getDesireAttendanceList(String monthName) {
        attendanceReference = FirebaseDatabase.
                getInstance().
                getReference("Attendance")
                .child(monthName);
        attendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAttendanceArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employeeInfo.getUserPgId().equals(attendance.getPgId())) {
                        mAttendanceArrayList.add(attendance);
                    }
                }
                Log.d(TAG, "onChildAdded: " + mAttendanceArrayList.size());
                if(mAttendanceArrayList.size() == 0){
                    nullMessage.setVisibility(View.VISIBLE);
                }else{
                    nullMessage.setVisibility(View.GONE);
                    attendanceHistoryList.setVisibility(View.VISIBLE);
                }
                attendanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}