package com.example.emsapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emsapp.Model.Department;
import com.example.emsapp.R;

import java.util.ArrayList;

public class DepartmentAdapter extends ArrayAdapter<Department> {
    private ArrayList<Department> departmentArrayList;
    private Context context;

    public DepartmentAdapter(@NonNull Context context, ArrayList<Department> departmentArrayList) {
        super(context, 0, departmentArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    @Override
    public boolean isEnabled(int position) {
        return position == 0 ? false : true;
    }

    private View initView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.department_type, parent, false);

        }

        TextView productTypeName = convertView.findViewById(R.id.departmentType);
        Department employeeDepartment = getItem(position);

        if (position == 0) {
            productTypeName.setTextColor(Color.GRAY);
        } else {
            productTypeName.setTextColor(Color.BLACK);

        }
        if (employeeDepartment != null) {
            productTypeName.setText(employeeDepartment.getDepartmentName());
        }

        return convertView;
    }
}
