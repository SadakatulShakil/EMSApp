package com.example.emsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.R;

import java.util.ArrayList;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.viewHolder> {

    private Context context;
    private ArrayList<String> mEquipmentArrayList;

    public EquipmentAdapter(Context context,ArrayList<String> mEquipmentArrayList) {
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
    public void onBindViewHolder(@NonNull EquipmentAdapter.viewHolder holder, int position) {

        String equipmentName = mEquipmentArrayList.get(position);

        holder.equipmentName.setText(equipmentName);




    }

    @Override
    public int getItemCount() {
        return mEquipmentArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView equipmentName;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            equipmentName = itemView.findViewById(R.id.equipmentName);
        }
    }
}
