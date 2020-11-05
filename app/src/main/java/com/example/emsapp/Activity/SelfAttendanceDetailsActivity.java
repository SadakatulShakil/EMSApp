package com.example.emsapp.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.emsapp.Model.Attendance;
import com.example.emsapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SelfAttendanceDetailsActivity extends AppCompatActivity {
    private TextView dateViewTv, startTimeTv, finishTimeTv, startLocationTv, finishLocationTv, lateReason;
    private Attendance attendanceInfo;
    public static final String TAG = "selfAttendance";

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        ///////////Compare Office late Count Down/////
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.US);
        String currentTime = sdf.format(calendar.getTime());

        try {
            Date estimatedOfficeTime = sdf.parse("09:15 AM");
            Date checkInTime = sdf.parse(attendanceInfo.getStartTime());

            int diff = checkInTime.compareTo(estimatedOfficeTime);
            Log.d(TAG, "onCreate: " + "Compare Value: "+ diff);

            if(diff>0){///late office
                lateReason.setText(reason);
            }else if(diff<0){///in time office
                lateReason.setText(getString(R.string.late_reason));
                lateReason.setTextColor(getColor(R.color.green));
            }else if(diff == 0){//in time office
                lateReason.setText(getString(R.string.late_reason));
                lateReason.setTextColor(getColor(R.color.green));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///////////Compare Office late Count Down/////

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