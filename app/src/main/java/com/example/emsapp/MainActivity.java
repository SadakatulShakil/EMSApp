package com.example.emsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emsapp.Activity.AddEmployeeActivity;
import com.example.emsapp.Activity.AdminControllerActivity;
import com.example.emsapp.Activity.UserSignInActivity;
import com.example.emsapp.Adapter.UserRoleAdapter;
import com.example.emsapp.Model.UserRole;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button letsContinue;
    FirebaseAuth firebaseAuth;
    private Spinner userRoleSpinner;
    private String  currentUserRole;
    private ArrayList<UserRole> mUserRoleList;
    private UserRoleAdapter mUserRoleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        letsContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userRole = currentUserRole;
                Intent intent = new Intent(MainActivity.this, UserSignInActivity.class);
                intent.putExtra("userRole", userRole);
                startActivity(intent);
            }
        });
    }

    private void inItView() {
        letsContinue = findViewById(R.id.btnToLogin);

        /////////User Role View Adapter work/////////
        userRoleSpinner = findViewById(R.id.userRoleSpinner);
        mUserRoleAdapter = new UserRoleAdapter(MainActivity.this, mUserRoleList);
        userRoleSpinner.setAdapter(mUserRoleAdapter);
        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole clickedUserRole = (UserRole) parent.getItemAtPosition(position);

                currentUserRole = clickedUserRole.getUserRole();

                Toast.makeText(MainActivity.this, currentUserRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}