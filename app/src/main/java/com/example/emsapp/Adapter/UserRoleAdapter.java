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
import com.example.emsapp.Model.UserRole;
import com.example.emsapp.R;

import java.util.ArrayList;

public class UserRoleAdapter extends ArrayAdapter<UserRole> {
    private ArrayList<UserRole> userRoleArrayList;
    private Context context;

    public UserRoleAdapter(@NonNull Context context, ArrayList<UserRole> userRoleArrayList) {
        super(context, 0, userRoleArrayList);
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
        UserRole userRole = getItem(position);

        if (position == 0) {
            productTypeName.setTextColor(Color.GRAY);
        } else {
            productTypeName.setTextColor(Color.BLACK);

        }
        if (userRole != null) {
            productTypeName.setText(userRole.getUserRole());
        }

        return convertView;
    }
}
