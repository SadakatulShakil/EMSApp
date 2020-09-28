package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.emsapp.Model.Attendance;
import com.example.emsapp.R;

public class SelfMovableDetailsActivity extends AppCompatActivity {
    private TextView dateViewTv, startTimeTv, finishTimeTv, startLocationTv, finishLocationTv, movementReasonTv;
    private Attendance attendanceInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_movable_details);
        inItView();
        Intent intent = getIntent();
        attendanceInfo = (Attendance) intent.getSerializableExtra("attendanceInfo");

        dateViewTv.setText(attendanceInfo.getDate());
        startTimeTv.setText("Office Start At: "+attendanceInfo.getStartTime());
        startLocationTv.setText(attendanceInfo.getStartLocation());
        finishTimeTv.setText("Office Finish At: "+attendanceInfo.getFinishTime());
        finishLocationTv.setText(attendanceInfo.getFinishLocation());
        movementReasonTv.setText(attendanceInfo.getMovementReason());
    }
    private void inItView() {
        dateViewTv = findViewById(R.id.dateTv);
        startTimeTv = findViewById(R.id.startOfficeTimeTv);
        startLocationTv = findViewById(R.id.startOfficeLocationTv);
        finishTimeTv = findViewById(R.id.finishOfficeTimeTv);
        finishLocationTv = findViewById(R.id.finishOfficeLocationTv);
        movementReasonTv = findViewById(R.id.movementReasonTv);
    }
}