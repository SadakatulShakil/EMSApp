package com.example.emsapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emsapp.Model.Execution;
import com.example.emsapp.R;

public class ExecutionReportDetailsActivity extends AppCompatActivity {

    private TextView nameTv, contactTv, organizationTv, addressTv, quireTv, adviceTv, remarksTv, dateTv;
    private EditText feedBackEt;
    private Button sendFeedbackBt;
    private Execution executionInfo;
    private String feedBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execution_report_details);

        inItView();

        Intent intent1 = getIntent();
        executionInfo = (Execution) intent1.getSerializableExtra("executionInfo");

        nameTv.setText(executionInfo.getCallerName());
        contactTv.setText(executionInfo.getCallerContact());
        organizationTv.setText(executionInfo.getCallerOrganization());
        addressTv.setText(executionInfo.getCallerAddress());
        quireTv.setText(executionInfo.getCallerQuire());
        adviceTv.setText(executionInfo.getCallerGivenAdvice());
        remarksTv.setText(executionInfo.getGivenRemarks());
        dateTv.setText(executionInfo.getCallingDate());

        feedBackText = feedBackEt.getText().toString().trim();

        sendFeedbackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendFeedBack(feedBackText);
            }
        });

    }

    private void SendFeedBack(String feedBackText) {
        Toast.makeText(this, "Wait buddy its underConstruction mood", Toast.LENGTH_SHORT).show();
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

    }
}