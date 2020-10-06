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

import com.example.emsapp.Model.Month;
import com.example.emsapp.R;

import java.util.ArrayList;

public class MonthAdapter extends ArrayAdapter<Month> {
    private ArrayList<Month> monthArrayList;
    private Context context;

    public MonthAdapter(@NonNull Context context, ArrayList<Month> monthArrayList) {
        super(context, 0, monthArrayList);
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
        Month attendanceMonth = getItem(position);

        if (position == 0) {
            productTypeName.setTextColor(Color.GRAY);
        } else {
            productTypeName.setTextColor(Color.BLACK);

        }
        if (attendanceMonth != null) {
            productTypeName.setText(attendanceMonth.getMonthName());
        }

        return convertView;
    }
}
