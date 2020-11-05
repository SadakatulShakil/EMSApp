package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Model.Attendance;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OfficeInAndOutActivity extends AppCompatActivity {

    private Button startOfficeBtn, finishOfficeBtn;
    private LinearLayout startInformation, finishInformation;
    private EditText lateReasonEt;
    private TextView startTime, startLocation, finishTime, finishLocation, demoInTime, demoOverTime, reason;
    private  String addingTimeForStart, addingTimeForFinish,
            addingDate, addressForStart, addressForFinish,addressStart, addressFinish, monthName, setPId, getPId, lateReason;
    private FusedLocationProviderClient client;
    private Geocoder geocoder;
    private Employee employee;
    private List<Address> addresses;
    public static final String TAG =  "InOut";
    private String userRole, currentMonthName;
    private DatabaseReference attendanceReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_in_and_out);
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        ///////////Compare Office late Count Down/////
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a",Locale.US);
        String currentTime = sdf.format(calendar.getTime());
        try {
            Date estimatedOfficeTime = sdf.parse("04:10 PM");
            Date checkInTime = sdf.parse(currentTime);

            int diff = checkInTime.compareTo(estimatedOfficeTime);
            Log.d(TAG, "onCreate: " + "Compare Value: "+ diff);

            if(diff>0){
                demoOverTime.setVisibility(View.VISIBLE);
                demoInTime.setVisibility(View.GONE);
                lateReasonEt.setVisibility(View.VISIBLE);
            }else if(diff<0){
                demoInTime.setVisibility(View.VISIBLE);
                demoOverTime.setVisibility(View.GONE);
                lateReasonEt.setVisibility(View.VISIBLE);
                lateReasonEt.setHint(getString(R.string.gratings_hint));
            }else if(diff == 0){
                demoInTime.setVisibility(View.VISIBLE);
                demoOverTime.setVisibility(View.GONE);
                lateReasonEt.setVisibility(View.VISIBLE);
                lateReasonEt.setHint(getString(R.string.gratings_hint));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ///////////Compare Office late Count Down/////
        setCheckInOutData();

        startOfficeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + "officeStartClickd");
                //startInformation.setVisibility(View.VISIBLE);
                lateReason = lateReasonEt.getText().toString().trim();
                if (lateReason.isEmpty()) {
                    lateReasonEt.setError("Reason is required!");
                    lateReasonEt.requestFocus();
                    return;
                }
                reason.setText(lateReason);
                lateReasonEt.setVisibility(View.GONE);
                demoInTime.setVisibility(View.GONE);
                demoOverTime.setVisibility(View.GONE);

                ///Current Time///
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myTimeFormat = new SimpleDateFormat("hh:mm a",Locale.US);
                addingTimeForStart = myTimeFormat.format(calendar.getTime());
                SimpleDateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
                addingDate = myDateFormat.format(calendar.getTime());
                startTime.setText(addingTimeForStart);
                ///Current Time//////
                final String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
                Log.d(TAG, "onClick: " + monthName);
                ///current Location////
                geocoder= new Geocoder(OfficeInAndOutActivity.this, Locale.getDefault());
                client = LocationServices.getFusedLocationProviderClient(OfficeInAndOutActivity.this);
                requestPermission();

                if(ActivityCompat.checkSelfPermission(OfficeInAndOutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                client.getLastLocation().addOnSuccessListener((Activity) OfficeInAndOutActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location != null)
                        {


                            //txtLocation.setText(location.toString());
                            Double lat = location.getLatitude();
                            Double  lng = location.getLongitude();

                            try {
                                addresses = geocoder.getFromLocation(lat,lng,1);

                                addressStart = addresses.get(0).getAddressLine(0);

                                startLocation.setText(addressStart);

                                Log.d(TAG, "onSuccessLocation : " + addressForStart);
                                storeStartStatus(addressStart, monthName, lateReason);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            startLocation.setText("Couldn't find !");
                        }

                    }
                });
                ///current Location////
            }
        });

        finishOfficeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finishInformation.setVisibility(View.VISIBLE);
                ///Current Time///
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myTimeFormat = new SimpleDateFormat("hh:mm a",Locale.US);
                addingTimeForFinish = myTimeFormat.format(calendar.getTime());
                SimpleDateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
                addingDate = myDateFormat.format(calendar.getTime());
                finishTime.setText(addingTimeForFinish);
                ///Current Time//////
                monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
                ///current Location////
                geocoder= new Geocoder(OfficeInAndOutActivity.this, Locale.getDefault());
                client = LocationServices.getFusedLocationProviderClient(OfficeInAndOutActivity.this);
                requestPermission();

                if(ActivityCompat.checkSelfPermission(OfficeInAndOutActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                client.getLastLocation().addOnSuccessListener((Activity) OfficeInAndOutActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if(location != null)
                        {


                            //txtLocation.setText(location.toString());
                            Double lat = location.getLatitude();
                            Double  lng = location.getLongitude();

                            try {
                                addresses = geocoder.getFromLocation(lat,lng,1);

                                addressFinish = addresses.get(0).getAddressLine(0);

                                finishLocation.setText(addressFinish);

                                Log.d(TAG, "onSuccessLocation : " + addressForFinish);
                                storeFinishStatus(addressFinish, monthName, lateReason);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            finishLocation.setText("Couldn't find !");
                        }

                    }
                });

                ///current Location////
            }
        });

    }

    private void storeStartStatus(String addressStart, String monthName, String lateReason) {
        attendanceReference = FirebaseDatabase.getInstance().getReference("Attendance")
                .child(monthName);

        setPId = attendanceReference.push().getKey();
        Attendance attendanceInfo = new Attendance(
                employee.getUserPgId(),
                addingDate,
                addingTimeForStart,
                addressStart,
                lateReason,
                setPId
        );
        attendanceReference.child(setPId).setValue(attendanceInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startOfficeBtn.setClickable(false);
                    Toast.makeText(OfficeInAndOutActivity.this, "Your Office Start Information Updated !", Toast.LENGTH_SHORT).show();
                }
                else{
                    startOfficeBtn.setClickable(true);
                    Toast.makeText(OfficeInAndOutActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void storeFinishStatus(String addressStart, String monthName, String lateReason) {
        attendanceReference = FirebaseDatabase.getInstance().getReference("Attendance")
                .child(monthName);

        Attendance attendanceInfo = new Attendance(
                employee.getUserPgId(),
                addingDate,
                startTime.getText().toString().trim(),
                startLocation.getText().toString().trim(),
                addingTimeForFinish,
                addressStart,
                lateReason,
                getPId

        );

        attendanceReference.child(getPId).setValue(attendanceInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    finishOfficeBtn.setClickable(false);
                    Toast.makeText(OfficeInAndOutActivity.this, "Your Office Finishing Information Updated !", Toast.LENGTH_SHORT).show();
                }
                else{
                    finishOfficeBtn.setClickable(true);
                    Toast.makeText(OfficeInAndOutActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }

    private void setCheckInOutData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
        final String currentDate = myDateFormat.format(calendar.getTime());
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        attendanceReference = FirebaseDatabase.
                getInstance().
                getReference("Attendance")
                .child(currentMonthName);

        attendanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employee.getUserPgId().equals(attendance.getPgId()) && attendance.getDate().equals(currentDate)) {
                        getPId = attendance.getPushId();
                        startTime.setText(attendance.getStartTime());
                        startLocation.setText(attendance.getStartLocation());
                        reason.setText(attendance.getMovementReason());

                        finishTime.setText(attendance.getFinishTime());
                        finishLocation.setText(attendance.getFinishLocation());
                        //Log.d(TAG, "onChildAdded: " + mAttendanceArrayList.size());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void inItView() {

        startOfficeBtn = findViewById(R.id.startBtn);
        finishOfficeBtn = findViewById(R.id.endBtn);
        startInformation = findViewById(R.id.startDetailsLayout);
        finishInformation = findViewById(R.id.endDetailsLayout);
        startTime = findViewById(R.id.startTimeTv);
        startLocation = findViewById(R.id.startLocationTv);
        finishTime = findViewById(R.id.endTimeTv);
        finishLocation = findViewById(R.id.endLocationTv);
        demoInTime = findViewById(R.id.inTime);
        demoOverTime = findViewById(R.id.overTime);
        lateReasonEt = findViewById(R.id.reasonTextEt);
        reason = findViewById(R.id.reasonTv);
    }
}