package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.Model.Employee;
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
    private TextView userType;
    private EditText userNameEt, userPasswordEt, confirmPasswordEt;
    private Button signInBt;
    private ProgressBar progressBar;
    private String userRole;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private DatabaseReference employeeReference;
    private String uName, uPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        inItView();

        Intent intent = getIntent();
        userRole = intent.getStringExtra("userRole");

        userType.setText("Hello ! " + userRole);
        clickEvents();
    }

    private void clickEvents() {

        signInBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                uName = userNameEt.getText().toString().trim();
                uPassword = userPasswordEt.getText().toString().trim();
                confirmPassword = confirmPasswordEt.getText().toString().trim();

                if (uPassword.equals(confirmPassword)) {
                    if (uName.equals("Admin") && uPassword.equals("123321")) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(UserSignInActivity.this, AdminControllerActivity.class);
                        startActivity(intent);
                        finish();
                    } else {


                        employeeReference = FirebaseDatabase.getInstance().getReference("Employee");


                        employeeReference.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    Employee employeeInfo = userSnapshot.getValue(Employee.class);
                                    if (employeeInfo.getUserPgId().equals(uName)
                                            && employeeInfo.getUserPassword().equals(uPassword)) {
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(UserSignInActivity.this, UserHomePageActivity.class);
                                        intent.putExtra("userName", uName);
                                        startActivity(intent);
                                        finish();
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
            }
        });

    }///clickEvents


    private void inItView() {
        userType = findViewById(R.id.demoUser);
        userNameEt = findViewById(R.id.etUserName);
        userPasswordEt = findViewById(R.id.etUserPassword);
        confirmPasswordEt = findViewById(R.id.etConfirmPassword);
        signInBt = findViewById(R.id.btnSignIn);
        progressBar = findViewById(R.id.progressBar);
    }
}