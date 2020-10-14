package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.emsapp.Adapter.ExecutionAdapter;
import com.example.emsapp.Adapter.MonthAdapter;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Execution;
import com.example.emsapp.Model.Month;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ExecutiveReportActivity extends AppCompatActivity {

    private EditText nameEt, phoneEt, dateEt, organizationNameEt, addressEt, quiresEt, givenAdviceEt, remarksEt;
    private LinearLayout layoutOfExecutive, layoutOfAccessPerson;
    private CheckBox cb1, cb2, cb3;
    private String accessName1, accessName2, accessName3, currentMonthName, monthName;
    private Button submitBtn;
    private ProgressBar progressBar;
    private Employee employee;
    private String pushId;
    private ArrayList<String> accessibleNameList = new ArrayList<>();
    private DatabaseReference executionReference;
    public static final String TAG = "Executive";
    private ImageView historyBtn, refreshBtn;
    private RecyclerView executionReportHistoryList;
    private Spinner monthSpinner;
    private ArrayList<Month> monthArrayList;
    private MonthAdapter monthAdapter;
    private DatabaseReference executionReportReference;
    private ArrayList<Execution> executionReportArrayList = new ArrayList<>();;
    private ExecutionAdapter executionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive_report);
        inItList();
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");
            ////for AccessUser ///////////

        executionReportHistoryList.setLayoutManager(new LinearLayoutManager(ExecutiveReportActivity.this));
        executionAdapter = new ExecutionAdapter(ExecutiveReportActivity.this, executionReportArrayList, employee);
        executionReportHistoryList.setAdapter(executionAdapter);

        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        getCurrentMonthReport(currentMonthName);
        ///////////////////////////////////////////////

        if(!employee.getUserDepartment().equals("Executive")){
            historyBtn.setVisibility(View.GONE);
            refreshBtn.setVisibility(View.VISIBLE);
            layoutOfExecutive.setVisibility(View.GONE);
            layoutOfAccessPerson.setVisibility(View.VISIBLE);
        }

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ExecutiveReportActivity.this, ExecutionReportHistoryActivity.class);
                intent1.putExtra("employeeInfo", employee);
                startActivity(intent1);
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentMonthReport(currentMonthName);
                Toast.makeText(ExecutiveReportActivity.this, "Report is Refreshed!", Toast.LENGTH_SHORT).show();
            }
        });

        dateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat sampleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                                dateEt.setText(sampleDateFormat.format(calendar.getTime()));
                                Log.d(TAG, "Start Time: "+dateEt);
                            }
                        };
                        new TimePickerDialog(ExecutiveReportActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                    }
                };
                new DatePickerDialog(ExecutiveReportActivity.this, dateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb1.isChecked()){
                    accessName1 = "MD Sir";
                    accessibleNameList.add(accessName1);
                }else{
                    accessibleNameList.remove(accessName1);
                }
            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb2.isChecked()){
                    accessName2 = "GM Sir";
                    accessibleNameList.add(accessName2);
                }else{
                    accessibleNameList.remove(accessName2);
                }
            }
        });

        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb3.isChecked()){
                    accessName3 = "Co-Ordinator";
                    accessibleNameList.add(accessName3);
                }else{
                    accessibleNameList.remove(accessName3);
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEt.getText().toString().trim();
                String phone = phoneEt.getText().toString().trim();
                String date = dateEt.getText().toString().trim();
                String organizationName = organizationNameEt.getText().toString().trim();
                String address = addressEt.getText().toString().trim();
                String quire = quiresEt.getText().toString().trim();
                String advice = givenAdviceEt.getText().toString().trim();
                String remarks = remarksEt.getText().toString().trim();


                storeExecutionData(accessibleNameList, name, phone, date, organizationName, address, quire, advice, remarks);

                nameEt.setText("");
                phoneEt.setText("");
                dateEt.setText("");
                organizationNameEt.setText("");
                addressEt.setText("");
                quiresEt.setText("");
                addressEt.setText("");
                remarksEt.setText("");
                givenAdviceEt.setText(" ");
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
            }
        });
    }

    private void storeExecutionData(final ArrayList<String> accessibleNameList,
                                    final String name, final String phone,
                                    final String date, final String organizationName,
                                    final String address, final String quire,
                                    final String advice, final String remarks) {
        Calendar calendar = Calendar.getInstance();
        currentMonthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        executionReference = FirebaseDatabase.getInstance().getReference("Executive").child(currentMonthName);
        pushId = executionReference.push().getKey();
        Execution executionInfo = new Execution(
                pushId,
                accessibleNameList,
                name, phone, date,
                organizationName, address,
                quire, advice, remarks);

        executionReference.push().setValue(executionInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ExecutiveReportActivity.this, "Report Upload successfully!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExecutiveReportActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void getCurrentMonthReport(String currentMonthName) {

        executionReportReference = FirebaseDatabase.getInstance().getReference("Executive")
                .child(currentMonthName);

        executionReportReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                executionReportArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Execution executionInfo = userSnapshot.getValue(Execution.class);
                    executionReportArrayList.add(executionInfo);

                    executionReportHistoryList.setLayoutManager(new LinearLayoutManager(ExecutiveReportActivity.this));
                    executionAdapter = new ExecutionAdapter(ExecutiveReportActivity.this, executionReportArrayList, employee);
                    executionReportHistoryList.setAdapter(executionAdapter);
                }
                Log.d(TAG, "onChildAdded: " + executionReportArrayList.size());
                executionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inItList() {
        monthArrayList = new ArrayList<>();
        monthArrayList.add(new Month("Current Month"));
        monthArrayList.add(new Month("January"));
        monthArrayList.add(new Month("February"));
        monthArrayList.add(new Month("March"));
        monthArrayList.add(new Month("April"));
        monthArrayList.add(new Month("May"));
        monthArrayList.add(new Month("June"));
        monthArrayList.add(new Month("July"));
        monthArrayList.add(new Month("August"));
        monthArrayList.add(new Month("September"));
        monthArrayList.add(new Month("October"));
        monthArrayList.add(new Month("November"));
        monthArrayList.add(new Month("December"));
    }

    private void inItView() {
        nameEt = findViewById(R.id.callerName);
        phoneEt = findViewById(R.id.callerContact);
        dateEt = findViewById(R.id.callingDate);
        organizationNameEt = findViewById(R.id.callerOrganization);
        addressEt = findViewById(R.id.callerAddress);
        quiresEt = findViewById(R.id.callerQuire);
        givenAdviceEt = findViewById(R.id.ourAdvice);
        remarksEt = findViewById(R.id.remarks);
        cb1 = findViewById(R.id.checkBox1);
        cb2 = findViewById(R.id.checkBox2);
        cb3 = findViewById(R.id.checkBox3);
        submitBtn = findViewById(R.id.btnSubmit);
        progressBar = findViewById(R.id.progressBar);
        historyBtn = findViewById(R.id.reportHistory);
        refreshBtn = findViewById(R.id.refreshReportHistory);
        layoutOfExecutive = findViewById(R.id.executiveLayout);
        layoutOfAccessPerson = findViewById(R.id.accessibleLayout);
        executionReportHistoryList = findViewById(R.id.recyclerViewForExecutionList);

        monthSpinner = findViewById(R.id.monthSpinner);

        monthAdapter = new MonthAdapter(ExecutiveReportActivity.this, monthArrayList);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Month clickedMonth = (Month) parent.getItemAtPosition(position);

                monthName = clickedMonth.getMonthName();
                if(monthName.equals("Current Month")){
                    Toast.makeText(ExecutiveReportActivity.this, "Please Select Your desire month name", Toast.LENGTH_SHORT).show();
                }else{

                    getDesireReportList(monthName);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getDesireReportList(String monthName) {
        executionReportReference = FirebaseDatabase.getInstance().getReference("Executive")
                .child(monthName);

        executionReportReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                executionReportArrayList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Execution executionInfo = userSnapshot.getValue(Execution.class);
                    executionReportArrayList.add(executionInfo);

                }
                Log.d(TAG, "onChildAdded: " + executionReportArrayList.size());
                executionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}