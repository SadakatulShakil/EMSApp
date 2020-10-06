package com.example.emsapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.Query;
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
    public static final String TAG = "Equipment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_equipment);

        inItView();
        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("userInfo");
        //getAuthUser();

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

    private void getAuthUser() {
       /* employeeReference = FirebaseDatabase.getInstance().getReference("Employee");

        employeeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Employee employeeInfo = userSnapshot.getValue(Employee.class);

                    if(employeeInfo.getUserPgId().equals(userName)){
                        userOriginalName.setText("Hello! "+ employeeInfo.getUserName());
                        userRole = employeeInfo.getUserRole();
                        GoForDetails(employeeInfo);
                        GoForCheckInOutInfo(employeeInfo,userRole);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
    }

    private void getAllEquipment() {
        equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment");

        Query query = equipmentReference.orderByChild("pgId").equalTo(employee.getUserPgId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                equipmentList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {

                    Equipment equipment = userSnapshot.getValue(Equipment.class);
                    equipmentList.add(equipment);
                }
                for(int i = 0; i< equipmentList.size(); i++){

                    equipmentNameList.addAll(equipmentList.get(i).getEquipmentName());
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