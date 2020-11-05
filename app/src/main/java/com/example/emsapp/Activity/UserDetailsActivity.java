package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {
    private TextView eName, eEmail, ePhone, eNidNo, eCurrentCity,
            eCurrentLocation, eVillage, eUpazilla, eZilla,
            eDivision, ePgId, eDesignation, eJoiningDate, eDepartment, eConcern;
    private FirebaseUser user;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        inItView();
        final Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        eName.setText("Name: "+employee.getUserName());
        eEmail.setText("Email: "+employee.getUserEmail());
        ePhone.setText("Contact: "+employee.getUserPhone());
        eNidNo.setText("Nid: "+employee.getUserNid());
        eCurrentCity.setText("City: "+employee.getUserCurrentCity());
        eCurrentLocation.setText("Location: "+employee.getUserCurrentLocation());
        eVillage.setText("Village: "+employee.getUserVillage());
        eUpazilla.setText("Upazilla: "+employee.getUserUpazilla());
        eZilla.setText("Zilla: "+employee.getUserZilla());
        eDivision.setText("Division: "+employee.getUserDivision());
        ePgId.setText("PG ID: "+employee.getUserPgId());
        eDesignation.setText("Designation: "+employee.getUserDesignation());
        eJoiningDate.setText("Joining Date: "+employee.getUserJoiningDate());
        eDepartment.setText("Department: "+employee.getUserDepartment());
        eConcern.setText("Concern: "+employee.getUserConcern());

    }

    private void inItView() {
        firebaseAuth = FirebaseAuth.getInstance();
        eName = findViewById(R.id.employeeNameEt);
        eEmail = findViewById(R.id.employeeEmailEt);
        ePhone = findViewById(R.id.employeePhoneEt);
        eNidNo = findViewById(R.id.employeeNidNoEt);
        eCurrentCity = findViewById(R.id.employeeCurrentCityEt);
        eCurrentLocation = findViewById(R.id.employeeLocationEt);
        eVillage = findViewById(R.id.employeeVillageEt);
        eUpazilla = findViewById(R.id.employeeUpazillaEt);
        eZilla = findViewById(R.id.employeeZillaEt);
        eDivision = findViewById(R.id.employeeDivisionEt);
        ePgId = findViewById(R.id.employeePgIdEt);
        eDesignation = findViewById(R.id.employeeDesignationEt);
        eConcern = findViewById(R.id.employeeConcernEt);
        eJoiningDate = findViewById(R.id.startDateET);
        //progressBar = findViewById(R.id.progressBar);
        eDepartment = findViewById(R.id.department);

    }
}