package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.emsapp.Model.Attendance;
import com.example.emsapp.R;

public class SelfAttendanceDetailsActivity extends AppCompatActivity {
    private TextView dateViewTv, startTimeTv, finishTimeTv, startLocationTv, finishLocationTv, lateReason;
    private Attendance attendanceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_attendance_details);

        inItView();
        Intent intent = getIntent();
        attendanceInfo = (Attendance) intent.getSerializableExtra("attendanceInfo");

        dateViewTv.setText(attendanceInfo.getDate());
        startTimeTv.setText("Office Start At: "+attendanceInfo.getStartTime());
        startLocationTv.setText(attendanceInfo.getStartLocation());
        finishTimeTv.setText("Office Finish At: "+attendanceInfo.getFinishTime());
        finishLocationTv.setText(attendanceInfo.getFinishLocation());
        String reason = attendanceInfo.getMovementReason();
        if(reason.equals(null)){
            lateReason.setText(getString(R.string.late_reason));
        }else{
            lateReason.setText(reason);
        }
    }

    private void inItView() {
        dateViewTv = findViewById(R.id.dateTv);
        startTimeTv = findViewById(R.id.startOfficeTimeTv);
        startLocationTv = findViewById(R.id.startOfficeLocationTv);
        finishTimeTv = findViewById(R.id.finishOfficeTimeTv);
        finishLocationTv = findViewById(R.id.finishOfficeLocationTv);
        lateReason = findViewById(R.id.lateReasonTv);
    }
}