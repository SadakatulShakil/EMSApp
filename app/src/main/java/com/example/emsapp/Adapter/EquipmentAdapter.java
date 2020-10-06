package com.example.emsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.Model.Equipment.Equipment;
import com.example.emsapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.PropertyResourceBundle;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.viewHolder> {

    private Context context;
    private ArrayList<String> mEquipmentArrayList;
    private DatabaseReference equipmentRef;

    public EquipmentAdapter(Context context, ArrayList<String> mEquipmentArrayList) {
        this.context = context;
        this.mEquipmentArrayList = mEquipmentArrayList;
    }

    @NonNull
    @Override
    public EquipmentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_view, parent, false);
        return new EquipmentAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipmentAdapter.viewHolder holder, final int position) {
        final String equipmentName = mEquipmentArrayList.get(position);

        holder.equipmentName.setText(equipmentName);
       /* equipmentRef = FirebaseDatabase.getInstance().getReference("Equipment");
        holder.removeEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipmentRef.child(String.valueOf(equipmentName.indexOf(position)))
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mEquipmentArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView equipmentName;
        //private ImageView removeEquipment;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            equipmentName = itemView.findViewById(R.id.equipmentName);
            ///removeEquipment = itemView.findViewById(R.id.deleteEquipment);
        }
    }
}
