package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Execution;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ExecutiveReportActivity extends AppCompatActivity {

    private EditText nameEt, phoneEt, dateEt, organizationNameEt, addressEt, quiresEt, givenAdviceEt, remarksEt;
    private CheckBox cb1, cb2, cb3;
    private String accessName1, accessName2, accessName3, currentMonthName;
    private Button submitBtn;
    private ProgressBar progressBar;
    private Employee employee;
    private String pushId;
    private ArrayList<String> accessibleNameList = new ArrayList<>();
    private DatabaseReference executionReference;
    public static final String TAG = "Executive";
    private ImageView historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executive_report);
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ExecutiveReportActivity.this, ExecutionReportHistoryActivity.class);
                startActivity(intent1);
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
    }
}