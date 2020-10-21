package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserHomePageActivity extends AppCompatActivity {

    private CardView profileInfo, checkInOut, movableReport,
            movementEvents, attendance, addEquipment, executionReport;
    private TextView userOriginalName;
    private String PgId, userRole;
    private DatabaseReference employeeReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        inItView();
        Intent intent = getIntent();
        ////Data came from User SignInActivity//////
        PgId = intent.getStringExtra("pgId");
        getAuthUserData();

    }

    private void getAuthUserData() {

        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");

        employeeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Employee employeeInfo = userSnapshot.getValue(Employee.class);

                    if(employeeInfo.getUserPgId().equals(PgId)){
                        userOriginalName.setText("Hello! "+ employeeInfo.getUserName());
                        userRole = employeeInfo.getUserRole();
                        GoForDetails(employeeInfo);
                        GoForCheckInOutInfo(employeeInfo,userRole);
                        //////Different userInterface process///////////
                        if(employeeInfo.getUserDepartment().equals("Executive") ||
                                employeeInfo.getUserDepartment().equals("HR_Admin")){
                            executionReport.setVisibility(View.VISIBLE);
                        }
                    }

                }
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


        ///////Different option Working Process////////
    private void GoForDetails(final Employee employeeInfo) {
        profileInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomePageActivity.this, UserDetailsActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                startActivity(intent);
            }
        });
    }
    ///////Different option Working Process////////
    private void GoForCheckInOutInfo(final Employee employeeInfo, final String userRole) {
        checkInOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, OfficeInAndOutActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                startActivity(intent);
            }
        });

        movableReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, MovableReportActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                startActivity(intent);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, CheckAttendanceActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });
        movementEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, CheckMovableEventsActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });

        addEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, CheckEquipmentActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });

        executionReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomePageActivity.this, ExecutiveReportActivity.class);
                intent.putExtra("userInfo", employeeInfo);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });
    }

    private void inItView() {
        profileInfo = findViewById(R.id.profileInfoLayout);
        checkInOut = findViewById(R.id.checkInOutLayout);
        movableReport = findViewById(R.id.movableLayout);
        userOriginalName = findViewById(R.id.userName);
        attendance = findViewById(R.id.checkAttendanceLayout);
        movementEvents = findViewById(R.id.movementCheckLayout);
        addEquipment = findViewById(R.id.equipmentsLayout);
        executionReport = findViewById(R.id.executionLayout);
    }
}