package com.example.emsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.Activity.SelfMovableDetailsActivity;
import com.example.emsapp.Model.Attendance;
import com.example.emsapp.R;

import java.util.ArrayList;

public class MovableAdapter extends RecyclerView.Adapter<MovableAdapter.viewHolder> {
    private Context context;
    private ArrayList<Attendance> movementArrayList;

    public MovableAdapter(Context context, ArrayList<Attendance> movementArrayList) {
        this.context = context;
        this.movementArrayList = movementArrayList;
    }

    @NonNull
    @Override
    public MovableAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view, parent, false);
        return new MovableAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovableAdapter.viewHolder holder, int position) {
        final Attendance attendanceInfo = movementArrayList.get(position);
        String date = attendanceInfo.getDate();
        String start = attendanceInfo.getStartTime();
        String finish = attendanceInfo.getFinishTime();

        holder.dateTv.setText("Date: "+date);
        holder.startTimeTv.setText("Started: "+start);
        holder.finishTimeTv.setText("Finished: "+finish);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelfMovableDetailsActivity.class);
                intent.putExtra("attendanceInfo", attendanceInfo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movementArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView dateTv, startTimeTv, finishTimeTv;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.date);
            startTimeTv = itemView.findViewById(R.id.startTime);
            finishTimeTv = itemView.findViewById(R.id.endTime);
        }
    }
}
