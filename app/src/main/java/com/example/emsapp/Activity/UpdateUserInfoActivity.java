package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.emsapp.Adapter.DepartmentAdapter;
import com.example.emsapp.Adapter.UserRoleAdapter;
import com.example.emsapp.Model.Department;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.UserRole;
import com.example.emsapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UpdateUserInfoActivity extends AppCompatActivity {
    private EditText eName, eEmail, ePhone, eNidNo, eCurrentCity, eCurrentLocation, eVillage, eUpazilla,
            eZilla, eDivision, ePgId, eDesignation, eJoiningDate, ePassword;
    private Spinner departmentSpinner, userRoleSpinner;
    private String currentDepartment, currentUserRole;
    private Button updateBtn;
    private ArrayList<Department> mEmployeeDeptList;
    private DepartmentAdapter mDepartmentAdapter;
    private ArrayList<UserRole> mUserRoleList;
    private UserRoleAdapter mUserRoleAdapter;
    private ProgressBar progressBar;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);
        inItDepartmentList();
        inItUserRoleList();
        inItView();
        final Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        eName.setText(employee.getUserName());
        eEmail.setText(employee.getUserEmail());
        ePhone.setText(employee.getUserPhone());
        eNidNo.setText(employee.getUserNid());
        eCurrentCity.setText(employee.getUserCurrentCity());
        eCurrentLocation.setText(employee.getUserCurrentLocation());
        eVillage.setText(employee.getUserVillage());
        eUpazilla.setText(employee.getUserUpazilla());
        eZilla.setText(employee.getUserZilla());
        eDivision.setText(employee.getUserDivision());
        ePgId.setText(employee.getUserPgId());
        eDesignation.setText(employee.getUserDesignation());
        eJoiningDate.setText(employee.getUserJoiningDate());
        ePassword.setText(employee.getUserPassword());
    }

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

    private void inItDepartmentList() {
        mEmployeeDeptList = new ArrayList<>();
        mEmployeeDeptList.add(new Department("Select Department.."));
        mEmployeeDeptList.add(new Department("HR_Admin"));
        mEmployeeDeptList.add(new Department("Accounts"));
        mEmployeeDeptList.add(new Department("Legal_Adviser"));
        mEmployeeDeptList.add(new Department("Software"));
        mEmployeeDeptList.add(new Department("Hardware_Networking"));
        mEmployeeDeptList.add(new Department("Graphics_Designer"));
        mEmployeeDeptList.add(new Department("Executive"));
        mEmployeeDeptList.add(new Department("Marketing"));
        mEmployeeDeptList.add(new Department("Architecture"));
        mEmployeeDeptList.add(new Department("Civil"));
        mEmployeeDeptList.add(new Department("Service"));
    }

    private void inItView() {
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
        eJoiningDate = findViewById(R.id.startDateET);
        updateBtn = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        ePassword = findViewById(R.id.passwordET);

        departmentSpinner = findViewById(R.id.departmentSpinner);
        mDepartmentAdapter = new DepartmentAdapter(UpdateUserInfoActivity.this, mEmployeeDeptList);

        departmentSpinner.setAdapter(mDepartmentAdapter);

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department clickedDepartment = (Department) parent.getItemAtPosition(position);

                currentDepartment = clickedDepartment.getDepartmentName();

                Toast.makeText(UpdateUserInfoActivity.this, currentDepartment +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userRoleSpinner = findViewById(R.id.userRoleSpinner);
        mUserRoleAdapter = new UserRoleAdapter(UpdateUserInfoActivity.this, mUserRoleList);

        userRoleSpinner.setAdapter(mUserRoleAdapter);

        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole clickedUserRole = (UserRole) parent.getItemAtPosition(position);

                currentUserRole = clickedUserRole.getUserRole();

                Toast.makeText(UpdateUserInfoActivity.this, currentUserRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}