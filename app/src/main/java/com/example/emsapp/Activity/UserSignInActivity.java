package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.Adapter.UserRoleAdapter;
import com.example.emsapp.MainActivity;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.UserRole;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserSignInActivity extends AppCompatActivity {
    private EditText userNameEt, userPasswordEt;
    private Button signInBt;
    private ProgressBar progressBar;
    private String userRole;
    private Spinner userRoleSpinner;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private DatabaseReference employeeReference;
    private String uName, uPassword;
    private ArrayList<UserRole> mUserRoleList;
    private UserRoleAdapter mUserRoleAdapter;
    public static final String TAG = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        inItUserRoleList();
        inItView();

        clickEvents();
    }

    //////User Role List for DropDownList/////
    private void inItUserRoleList() {
        mUserRoleList = new ArrayList<>();
        mUserRoleList.add(new UserRole("Select User Role.."));
        mUserRoleList.add(new UserRole("Admin"));
        mUserRoleList.add(new UserRole("Managing Director"));
        mUserRoleList.add(new UserRole("General Manager"));
        mUserRoleList.add(new UserRole("Team Coordinator"));
        mUserRoleList.add(new UserRole("Project Manager"));
        mUserRoleList.add(new UserRole("General Employee"));
        mUserRoleList.add(new UserRole("Accounts"));
        mUserRoleList.add(new UserRole("Service"));
        mUserRoleList.add(new UserRole("Support"));
    }

    private void clickEvents() {

        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String currentUserRole = userRole;
                uName = userNameEt.getText().toString().trim();
                uPassword = userPasswordEt.getText().toString().trim();

                    if (uName.equals("Admin") && uPassword.equals("123321")) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(UserSignInActivity.this, AdminControllerActivity.class);
                        startActivity(intent);
                        finish();
                        SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                        preferences.edit().putString("TOKEN",uName).apply();
                        Log.d(TAG, "onChildAdded: "+ uName);
                    } else {

                        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");

                        employeeReference.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    Employee employeeInfo = userSnapshot.getValue(Employee.class);
                                    if (employeeInfo.getUserPgId().equals(uName) &&
                                            employeeInfo.getUserRole().equals(currentUserRole) &&
                                            employeeInfo.getUserPassword().equals(uPassword)) {
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(UserSignInActivity.this, UserHomePageActivity.class);
                                        intent.putExtra("pgId", uName);
                                        startActivity(intent);
                                        finish();
                                        SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                                        preferences.edit().putString("TOKEN",uName).apply();
                                        Log.d(TAG, "onChildAdded: "+ uName);
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
            }
        });

    }///clickEvents


    private void inItView() {
        userNameEt = findViewById(R.id.etUserName);
        userPasswordEt = findViewById(R.id.etUserPassword);
        signInBt = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);

        /////////User Role View Adapter work/////////
        userRoleSpinner = findViewById(R.id.userRoleSpinner);
        mUserRoleAdapter = new UserRoleAdapter(UserSignInActivity.this, mUserRoleList);
        userRoleSpinner.setAdapter(mUserRoleAdapter);
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole clickedUserRole = (UserRole) parent.getItemAtPosition(position);

                userRole = clickedUserRole.getUserRole();

                Toast.makeText(UserSignInActivity.this, userRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}