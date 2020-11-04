package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.EmployeeAdapter;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployeeDetailsActivity extends AppCompatActivity {

    private TextView eName, eEmail, ePhone, eNidNo, eCurrentCity,
            eCurrentLocation, eVillage, eUpazilla, eZilla,
            eDivision, ePgId, eDesignation, eJoiningDate, eDepartment, userDeleteBtn;
    private FirebaseUser user;
    private ArrayList<Employee> employeeInfoList = new ArrayList<>();
    private EmployeeAdapter mEmployeeAdapter;
    private DatabaseReference employeeReference;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private FloatingActionButton updateUserInfo;
    private Employee employee;
    private DatabaseReference databaseReference;
    public static final String TAG = "Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("employeeInfo");
        databaseReference =FirebaseDatabase.getInstance().getReference("Employee");

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

        userDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = employee.geteId();
                databaseReference.child(userId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(EmployeeDetailsActivity.this, "Successfully deleted! "+ "userName: "+ employee.getUserName(), Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(EmployeeDetailsActivity.this, AdminControllerActivity.class);
                            startActivity(intent1);
                            finish();
                        }
                    }
                });
            }
        });

        updateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EmployeeDetailsActivity.this, UpdateUserInfoActivity.class);
                intent1.putExtra("userInfo", employee);
                startActivity(intent1);
            }
        });

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
        eJoiningDate = findViewById(R.id.startDateET);
        userDeleteBtn = findViewById(R.id.deleteUser);
        updateUserInfo = findViewById(R.id.updateFAB);
        //progressBar = findViewById(R.id.progressBar);
        eDepartment = findViewById(R.id.department);
    }
}