package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Adapter.FeedBackReportAdapter;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Execution;
import com.example.emsapp.Model.FeedBack;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ExecutionReportDetailsActivity extends AppCompatActivity {

    private RecyclerView executionFeedBackList;
    private FeedBackReportAdapter feedBackReportAdapter;
    private ArrayList<FeedBack> feedBackArrayList = new ArrayList<>();
    private DatabaseReference feedBackRef;
    private LinearLayout feedBackLayout;
    private TextView nameTv, contactTv, organizationTv, addressTv, quireTv, adviceTv, remarksTv, dateTv;
    private EditText feedBackEt;
    private Button sendFeedbackBt;
    private Execution executionInfo;
    private Employee employeeInfo;
    private String feedBackText, feedBackTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_report_details);

        inItView();

        Intent intent1 = getIntent();
        executionInfo = (Execution) intent1.getSerializableExtra("executionInfo");
        employeeInfo = (Employee) intent1.getSerializableExtra("employeeInfo");

        nameTv.setText(executionInfo.getCallerName());
        contactTv.setText(executionInfo.getCallerContact());
        organizationTv.setText(executionInfo.getCallerOrganization());
        addressTv.setText(executionInfo.getCallerAddress());
        quireTv.setText(executionInfo.getCallerQuire());
        adviceTv.setText(executionInfo.getCallerGivenAdvice());
        remarksTv.setText(executionInfo.getGivenRemarks());
        dateTv.setText(executionInfo.getCallingDate());



        sendFeedbackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedBackText = feedBackEt.getText().toString().trim();
                SendFeedBack(feedBackText, executionInfo, employeeInfo);
                feedBackEt.setText("");
            }
        });

        if(!employeeInfo.getUserDepartment().equals("Executive")){
            feedBackLayout.setVisibility(View.VISIBLE);
        }

    }

    private void SendFeedBack(String feedBackText, Execution executionInfo, Employee employeeInfo) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat myTimeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        feedBackTime = myTimeFormat.format(calendar.getTime());

        feedBackRef = FirebaseDatabase.getInstance().getReference("FeedBack");

        String reportId = executionInfo.getPushId();
        FeedBack feedBack = new FeedBack(reportId, feedBackTime, employeeInfo.getUserName(), feedBackText);

        feedBackRef.child(reportId).setValue(feedBack).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ExecutionReportDetailsActivity.this, "FeedBack Given Successfully !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void inItView() {
        nameTv = findViewById(R.id.callerNameTv);
        contactTv = findViewById(R.id.callerContactTv);
        organizationTv = findViewById(R.id.callerOrganizationTv);
        addressTv = findViewById(R.id.callerAddressTv);
        quireTv = findViewById(R.id.callerQuireTv);
        adviceTv = findViewById(R.id.ourAdviceTv);
        remarksTv = findViewById(R.id.remarksTv);
        dateTv = findViewById(R.id.dateViewTv);

        feedBackEt = findViewById(R.id.feedBackEt);
        sendFeedbackBt = findViewById(R.id.sendBtn);

        executionFeedBackList = findViewById(R.id.recyclerViewForFeedBackList);
        feedBackLayout = findViewById(R.id.feedBackLayout);

    }
}