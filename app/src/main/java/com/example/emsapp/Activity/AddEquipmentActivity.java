package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Equipment.Equipment;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddEquipmentActivity extends AppCompatActivity {
    private CheckBox  c1, c2, c3, c4, c5 ,c6, c7, c8, c9, c10;
    private String  e1, e2, e3, e4, e5 ,e6, e7, e8, e9, e10;
    private EditText otherEquipmentsEt;
    private Button addBtn;
    private Employee employee;
    private Equipment equipment;
    private DatabaseReference equipmentReference;
    private ArrayList<String> equipmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);
        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked()){
                    e1 = "Laptop";
                    equipmentList.add(e1);
                }else{
                    equipmentList.remove(e1);
                }
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c2.isChecked()){
                    e2 = "PC";
                    equipmentList.add(e2);
                }else{
                    equipmentList.remove(e2);
                }
            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c3.isChecked()){
                    e3 = "Monitor";
                    equipmentList.add(e3);
                }else{
                    equipmentList.remove(e3);
                }
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c4.isChecked()){
                    e4 = "Keyboard";
                    equipmentList.add(e4);
                }else{
                    equipmentList.remove(e4);
                }
            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c5.isChecked()){
                    e5 = "Mouse";
                    equipmentList.add(e5);
                }else{
                    equipmentList.remove(e5);
                }
            }
        });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c6.isChecked()){
                    e6 = "Dairy";
                    equipmentList.add(e6);
                }else{
                    equipmentList.remove(e6);
                }
            }
        });
        c7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c7.isChecked()){
                    e7 = "Printer";
                    equipmentList.add(e7);
                }else{
                    equipmentList.remove(e7);
                }
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c8.isChecked()){
                    e8 = "Server";
                    equipmentList.add(e8);
                }else{
                    equipmentList.remove(e8);
                }
            }
        });
        c9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c9.isChecked()){
                    e9 = "Photocopy Machine";
                    equipmentList.add(e9);
                }else{
                    equipmentList.remove(e9);
                }
            }
        });

        c10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c10.isChecked()){
                    otherEquipmentsEt.setVisibility(View.VISIBLE);
                }else{
                    otherEquipmentsEt.setVisibility(View.GONE);
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                e10 = otherEquipmentsEt.getText().toString().trim();
                equipmentList.add(e10);

                equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment");

                Equipment equipment = new Equipment(equipmentList, employee.getUserPgId());
                equipmentReference.push().setValue(equipment).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(AddEquipmentActivity.this,  "Size: "+equipmentList.size(), Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(AddEquipmentActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }

        });

        c1.setChecked(false);
        c2.setChecked(false);
        c3.setChecked(false);
        c4.setChecked(false);
        c5.setChecked(false);
        c6.setChecked(false);
        c7.setChecked(false);
        c8.setChecked(false);
        c9.setChecked(false);
        c10.setChecked(false);


    }

    private void inItView() {
        c1 = findViewById(R.id.checkBox1);
        c2 = findViewById(R.id.checkBox2);
        c3 = findViewById(R.id.checkBox3);
        c4 = findViewById(R.id.checkBox4);
        c5 = findViewById(R.id.checkBox5);
        c6 = findViewById(R.id.checkBox6);
        c7 = findViewById(R.id.checkBox7);
        c8 = findViewById(R.id.checkBox8);
        c9 = findViewById(R.id.checkBox9);
        c10 = findViewById(R.id.checkBox10);
        otherEquipmentsEt = findViewById(R.id.otherEquipment);
        addBtn = findViewById(R.id.btnAdd);
    }
}