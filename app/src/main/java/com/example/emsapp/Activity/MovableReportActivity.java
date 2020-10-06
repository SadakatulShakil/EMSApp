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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MovableReportActivity extends AppCompatActivity {
    private EditText reasonTextEt;
    private Button startOffice, finishOffice;
    private LinearLayout startInformation, finishInformation;
    private TextView startTime, startLocation, finishTime, finishLocation, reason;
    private  String addingTimeForStart, addingTimeForFinish,
            addingDate, addressForStart, addressForFinish,addressStart, addressFinish, monthName, moveReason,setPId, getPId;
    private FusedLocationProviderClient client;
    private Geocoder geocoder;
    private Employee employee;
    private List<Address> addresses;
    public static final String TAG =  "InOut";
    private DatabaseReference movableReportReference;
    private String userRole, currentMonthName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movable_report);
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        setCheckInOutData();

        startOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startInformation.setVisibility(View.VISIBLE);
                moveReason = reasonTextEt.getText().toString().trim();
                if (moveReason.isEmpty()) {
                    reasonTextEt.setError("Reason is required!");
                    reasonTextEt.requestFocus();
                    return;
                }
                reason.setText(moveReason);
                reasonTextEt.setText("");
                ///Current Time///
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat myTimeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                addingTimeForStart = myTimeFormat.format(calendar.getTime());
                SimpleDateFormat myDateFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.US);
                addingDate = myDateFormat.format(calendar.getTime());
                startTime.setText(addingTimeForStart);
                ///Current Time//////
                final String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
                Log.d(TAG, "onClick: " + monthName);
                ///current Location////
                geocoder= new Geocoder(MovableReportActivity.this, Locale.getDefault());
                client = LocationServices.getFusedLocationProviderClient(MovableReportActivity.this);
                requestPermission();

                if(ActivityCompat.checkSelfPermission(MovableReportActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                client.getLastLocation().addOnSuccessListener((Activity) MovableReportActivity.this, new OnSuccessListener<Location>() {
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
                                storeStartMoveStatus(addressStart, monthName, moveReason);


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

        finishOffice.setOnClickListener(new View.OnClickListener() {
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
                geocoder= new Geocoder(MovableReportActivity.this, Locale.getDefault());
                client = LocationServices.getFusedLocationProviderClient(MovableReportActivity.this);
                requestPermission();

                if(ActivityCompat.checkSelfPermission(MovableReportActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                client.getLastLocation().addOnSuccessListener((Activity) MovableReportActivity.this, new OnSuccessListener<Location>() {
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
                                storeFinishMoveStatus(addressFinish, monthName);


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

    private void storeStartMoveStatus(String addressStart, String monthName, String moveReason) {
        movableReportReference = FirebaseDatabase.getInstance().getReference("MovableReport")
                .child(monthName);

        setPId = movableReportReference.push().getKey();
        Attendance movableInfo = new Attendance(
                employee.getUserPgId(),
                addingDate,
                addingTimeForStart,
                addressStart,
                moveReason,
                setPId
        );
        movableReportReference.child(setPId).setValue(movableInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startTime.setClickable(false);
                    Toast.makeText(MovableReportActivity.this, "Your Office Start Information Updated !", Toast.LENGTH_SHORT).show();
                }
                else{
                    startTime.setClickable(true);
                    Toast.makeText(MovableReportActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void storeFinishMoveStatus(String addressStart, String monthName) {
        movableReportReference = FirebaseDatabase.getInstance().getReference("MovableReport")
                .child(monthName);

        Attendance movableInfo = new Attendance(
                employee.getUserPgId(),
                addingDate,
                startTime.getText().toString().trim(),
                startLocation.getText().toString().trim(),
                addingTimeForFinish,
                addressStart,
                reason.getText().toString().trim(),
                getPId
        );

        movableReportReference.child(getPId).setValue(movableInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    finishTime.setClickable(false);
                    Toast.makeText(MovableReportActivity.this, "Your Office Finishing Information Updated !", Toast.LENGTH_SHORT).show();
                }
                else{
                    finishTime.setClickable(false);
                    Toast.makeText(MovableReportActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        movableReportReference = FirebaseDatabase.
                getInstance().
                getReference("MovableReport")
                .child(currentMonthName);

        movableReportReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Attendance attendance = userSnapshot.getValue(Attendance.class);
                    if (employee.getUserPgId().equals(attendance.getPgId())) {
                        getPId = attendance.getPushId();
                        startTime.setText(attendance.getStartTime());
                        startLocation.setText(attendance.getStartLocation());
                        reason.setText(attendance.getMovementReason());
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

        startOffice = findViewById(R.id.startBtn);
        finishOffice = findViewById(R.id.endBtn);
        startInformation = findViewById(R.id.startDetailsLayout);
        finishInformation = findViewById(R.id.endDetailsLayout);
        startTime = findViewById(R.id.startTimeTv);
        startLocation = findViewById(R.id.startLocationTv);
        finishTime = findViewById(R.id.endTimeTv);
        finishLocation = findViewById(R.id.endLocationTv);
        reasonTextEt = findViewById(R.id.reasonTextEt);
        reason = findViewById(R.id.reasonTv);
    }
}