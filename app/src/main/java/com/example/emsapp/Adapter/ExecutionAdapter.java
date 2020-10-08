package com.example.emsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.Model.Execution;
import com.example.emsapp.R;

import java.util.ArrayList;

public class ExecutionAdapter extends RecyclerView.Adapter<ExecutionAdapter.viewHolder> {
    private Context context;
    private ArrayList<Execution> executionArrayList;

    public ExecutionAdapter(Context context, ArrayList<Execution> executionArrayList) {
        this.context = context;
        this.executionArrayList = executionArrayList;
    }

    @NonNull
    @Override
    public ExecutionAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.execution_report, parent, false);
        return new ExecutionAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExecutionAdapter.viewHolder holder, int position) {
        final Execution executionInfo = executionArrayList.get(position);

        holder.callingDate.setText(executionInfo.getCallingDate());
        holder.callerName.setText(executionInfo.getCallerName());
        holder.callerOrganization.setText(executionInfo.getCallerOrganization());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Under Construction process", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return executionArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView callingDate, callerName, callerOrganization;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            callingDate = itemView.findViewById(R.id.date);
            callerName = itemView.findViewById(R.id.callerNameEt);
            callerOrganization = itemView.findViewById(R.id.callerOrganizationEt);
        }
    }
}
