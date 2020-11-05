package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.DepartmentAdapter;
import com.example.emsapp.Adapter.UserConcernAdapter;
import com.example.emsapp.Adapter.UserRoleAdapter;
import com.example.emsapp.Model.Department;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.UserConcern;
import com.example.emsapp.Model.UserRole;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEmployeeActivity extends AppCompatActivity {

    private EditText eName, eEmail, ePhone, eNidNo, eCurrentCity, eCurrentLocation, eVillage, eUpazilla,
            eZilla, eDivision, ePgId, eDesignation, eJoiningDate, ePassword;
    private Spinner departmentSpinner, userRoleSpinner, userConcernSpinner;
    private String currentDepartment, currentUserRole, currentUserConcern;
    private Button registerBtn;
    private ArrayList<Department> mEmployeeDeptList;
    private DepartmentAdapter mDepartmentAdapter;
    private ArrayList<UserRole> mUserRoleList;
    private UserRoleAdapter mUserRoleAdapter;
    private UserConcernAdapter mUserConcernAdapter;
    private ArrayList<UserConcern> mUserConcernList;
    protected static TextView viewDate;
    private FirebaseUser user;
    private DatabaseReference employeeReference;
    private Employee employee;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    public static final String TAG = "AddEmployee";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        inItDepartmentList();
        inItUserRoleList();
        inItUserConcernList();
        inItView();
        final FragmentManager fm = getSupportFragmentManager();
        eJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment newFragment = new DatePickerFragment();

                newFragment.show(fm, "datePicker");
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerEmployeeData();
            }
        });
    }

    private void inItUserConcernList() {
        mUserConcernList = new ArrayList<>();
        mUserConcernList.add(new UserConcern("Select User Concern.."));
        mUserConcernList.add(new UserConcern("Polock Group"));
        mUserConcernList.add(new UserConcern("1 Touch"));
        mUserConcernList.add(new UserConcern("Dye Mac"));
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

    private void registerEmployeeData() {
        progressBar.setVisibility(View.VISIBLE);
                String name = eName.getText().toString().trim();
                String email = eEmail.getText().toString().trim();
                String phone = ePhone.getText().toString().trim();
                String nIdNo = eNidNo.getText().toString().trim();
                String currentCity = eCurrentCity.getText().toString().trim();
                String currentLocation = eCurrentLocation.getText().toString().trim();
                String village = eVillage.getText().toString().trim();
                String upazilla = eUpazilla.getText().toString().trim();
                String zilla = eZilla.getText().toString().trim();
                String division = eDivision.getText().toString().trim();
                String pgId = ePgId.getText().toString().trim();
                String department = currentDepartment;
                String userRole = currentUserRole;
                String userConcern = currentUserConcern;
                String designation = eDesignation.getText().toString().trim();
                String joiningDate = viewDate.getText().toString().trim();
                String password = ePassword.getText().toString().trim();

        if (name.isEmpty()) {
            eName.setError("Date is required!");
            eName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            eEmail.setError("Location is required!");
            eEmail.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            ePhone.setError("Description is required!");
            ePhone.requestFocus();
            return;
        }

        if (nIdNo.isEmpty()) {
            eNidNo.setError("Date is required!");
            eNidNo.requestFocus();
            return;
        }
        if (currentCity.isEmpty()) {
            eCurrentCity.setError("Location is required!");
            eCurrentCity.requestFocus();
            return;
        }
        if (currentLocation.isEmpty()) {
            eCurrentLocation.setError("Description is required!");
            eCurrentLocation.requestFocus();
            return;
        }

        if (village.isEmpty()) {
            eVillage.setError("Date is required!");
            eVillage.requestFocus();
            return;
        }
        if (upazilla.isEmpty()) {
            eUpazilla.setError("Location is required!");
            eUpazilla.requestFocus();
            return;
        }
        if (zilla.isEmpty()) {
            eZilla.setError("Description is required!");
            eZilla.requestFocus();
            return;
        }

        if (division.isEmpty()) {
            eDivision.setError("Date is required!");
            eDivision.requestFocus();
            return;
        }
        if (designation.isEmpty()) {
            eDesignation.setError("Location is required!");
            eDesignation.requestFocus();
            return;
        }
        if (joiningDate.isEmpty()) {
            viewDate.setError("Description is required!");
            viewDate.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            ePassword.setError("Description is required!");
            ePassword.requestFocus();
            return;
        }


        if(currentDepartment.equals("Select Department..")){
            Toast.makeText(AddEmployeeActivity.this, "please select your right Department please", Toast.LENGTH_SHORT).show();
        }else if(currentUserRole.equals("Select User Role..")){
            Toast.makeText(AddEmployeeActivity.this, "please select An User Role please", Toast.LENGTH_SHORT).show();
        }else if(currentUserRole.equals("Select User Concern..")){
            Toast.makeText(AddEmployeeActivity.this, "please select An User Concern please", Toast.LENGTH_SHORT).show();
        }else{
            storeEmployeeData(name, email, phone, nIdNo, currentCity, currentLocation, village,
                    upazilla, zilla, division, pgId, department, designation, joiningDate, password,userRole, userConcern);
        }
        eName.setText("");
        eEmail.setText("");
        ePhone.setText("");
        eNidNo.setText("");
        eCurrentCity.setText("");
        eCurrentLocation.setText("");
        eVillage.setText("");
        eUpazilla.setText("");
        eZilla.setText("");
        eDivision.setText("");
        ePgId.setText("");
        eDesignation.setText("");
        ePassword.setText("");
        eJoiningDate.setText("");
    }

    private void storeEmployeeData(final String name, final String email, final String phone, final String nIdNo,
                                   final String currentCity, final String currentLocation, final String village,
                                   final String upazilla, final String zilla, final String division, final String pgId,
                                   final String department, final String designation, final String joiningDate,
                                   final String password, final String userRole, final String userConcern) {



                            employeeReference = FirebaseDatabase.getInstance().getReference().child("Employee").child(department);
                            String pushId = employeeReference.push().getKey();
                            Employee employee = new Employee(pushId, name, email, phone, nIdNo, currentCity, currentLocation,
                                    village,upazilla, zilla, division, pgId, department, designation, joiningDate, password, userRole, userConcern);

                            employeeReference.push().setValue(employee).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddEmployeeActivity.this, "Successfully Registered Employee!", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent = new Intent(AddEmployeeActivity.this, AdminControllerActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(AddEmployeeActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        Log.d(TAG, "onComplete: "+task.getException().getMessage());
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onComplete: "+e.getMessage());
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
       registerBtn = findViewById(R.id.btnRegister);
       progressBar = findViewById(R.id.progressBar);
       ePassword = findViewById(R.id.passwordET);

       departmentSpinner = findViewById(R.id.departmentSpinner);
        mDepartmentAdapter = new DepartmentAdapter(AddEmployeeActivity.this, mEmployeeDeptList);

        departmentSpinner.setAdapter(mDepartmentAdapter);

        departmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Department clickedDepartment = (Department) parent.getItemAtPosition(position);

                currentDepartment = clickedDepartment.getDepartmentName();

                Toast.makeText(AddEmployeeActivity.this, currentDepartment +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userRoleSpinner = findViewById(R.id.userRoleSpinner);
        mUserRoleAdapter = new UserRoleAdapter(AddEmployeeActivity.this, mUserRoleList);

        userRoleSpinner.setAdapter(mUserRoleAdapter);

        userRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserRole clickedUserRole = (UserRole) parent.getItemAtPosition(position);

                currentUserRole = clickedUserRole.getUserRole();

                Toast.makeText(AddEmployeeActivity.this, currentUserRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userConcernSpinner = findViewById(R.id.userConcernSpinner);
        mUserConcernAdapter = new UserConcernAdapter(AddEmployeeActivity.this, mUserConcernList);

        userConcernSpinner.setAdapter(mUserConcernAdapter);

        userConcernSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserConcern clickedUserConcern = (UserConcern) parent.getItemAtPosition(position);

                currentUserConcern = clickedUserConcern.getUserConcern();

                Toast.makeText(AddEmployeeActivity.this, currentUserRole +" is selected !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //DatePickerMethods
    @SuppressLint("ValidFragment")
    public static class DatePickerFragment extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the chosen date
            viewDate = getActivity().findViewById(R.id.startDateET);
           /* int actualMonth = month+1; // Because month index start from zero
            // Display the unformatted date to TextView
            tvDate.setText("Year : " + year + ", Month : " + actualMonth + ", Day : " + day + "\n\n");*/

            // Create a Date variable/object with user chosen date
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();

            // Format the date using style medium and UK locale
            DateFormat df_medium_uk = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK);
            String df_medium_uk_str = df_medium_uk.format(chosenDate);
            // Display the formatted date
            viewDate.setText(df_medium_uk_str);
        }
    }
    //End of DatePickerMethods
}