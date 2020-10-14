package com.example.emsapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emsapp.Model.FeedBack;
import com.example.emsapp.R;

import java.util.ArrayList;

public class FeedBackReportAdapter extends RecyclerView.Adapter<FeedBackReportAdapter.viewHolder> {

    private Context context;
    private ArrayList<FeedBack> feedBackArrayList;

    public FeedBackReportAdapter(Context context, ArrayList<FeedBack> feedBackArrayList) {
        this.context = context;
        this.feedBackArrayList = feedBackArrayList;
    }

    @NonNull
    @Override
    public FeedBackReportAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_view, parent, false);
        return new FeedBackReportAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedBackReportAdapter.viewHolder holder, int position) {
        final FeedBack  feedBackInfo = feedBackArrayList.get(position);
        holder.feedBackDate.setText(feedBackInfo.getFeedBackTime());
        holder.feedBackSender.setText(feedBackInfo.getFeedBackSender());
        holder.feedBackText.setText(feedBackInfo.getFeedBackText());

    }

    @Override
    public int getItemCount() {
        return feedBackArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView feedBackDate, feedBackSender, feedBackText;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            feedBackDate = itemView.findViewById(R.id.date);
            feedBackSender = itemView.findViewById(R.id.senderNameTv);
            feedBackText = itemView.findViewById(R.id.feedBackTextTv);
        }
    }
}
