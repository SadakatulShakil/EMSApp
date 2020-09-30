package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.emsapp.Adapter.EquipmentAdapter;
import com.example.emsapp.Model.Employee;
import com.example.emsapp.Model.Equipment.Equipment;
import com.example.emsapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CheckEquipmentActivity extends AppCompatActivity {
    private RecyclerView equipmentRecyclerView;
    private DatabaseReference equipmentReference;
    private EquipmentAdapter equipmentAdapter;
    private ArrayList<String> equipmentNameList = new ArrayList<>();
    private ArrayList<Equipment> equipmentList = new ArrayList<>();
    private FloatingActionButton addEquipmentBtn;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_equipment);

        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");

        equipmentRecyclerView.setLayoutManager(new LinearLayoutManager(CheckEquipmentActivity.this));
        equipmentAdapter = new EquipmentAdapter(CheckEquipmentActivity.this, equipmentNameList);
        equipmentRecyclerView.setAdapter(equipmentAdapter);

        getAllEquipment();

        addEquipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckEquipmentActivity.this, AddEquipmentActivity.class);
                intent.putExtra("userInfo", employee);
                startActivity(intent);
            }
        });
    }

    private void getAllEquipment() {
        equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment");
        equipmentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                equipmentList.clear();
                for(DataSnapshot userSnapshot : snapshot.getChildren()){
                    Equipment equipment = userSnapshot.getValue(Equipment.class);
                    if(employee.getUserPgId().equals(equipment.getPgId())){
                        equipmentList.add(equipment);
                    }
                }
                if(equipmentList!=null && !equipmentList.isEmpty()){

                    equipmentNameList.addAll(equipmentList.get(0).getEquipmentName());

                }else{
                    Toast.makeText(CheckEquipmentActivity.this, "There was no data Available", Toast.LENGTH_SHORT).show();
                }
                    equipmentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void inItView() {
        equipmentRecyclerView = findViewById(R.id.recyclerViewForEquipmentList);
        addEquipmentBtn = findViewById(R.id.addEquipmentFAB);
    }
}