package com.example.eventbusimplementation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterProfile extends RecyclerView.Adapter<RecyclerAdapterProfile.ViewHolder> {

    Context context;
    List<ProfilePojo> profilePojoList = new ArrayList<>();
    Activity activity;

    public RecyclerAdapterProfile(Context context, List<ProfilePojo> profilePojoList,Activity activity) {
        this.context = context;
        this.profilePojoList = profilePojoList;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.certificate_list,parent,false);
        return new RecyclerAdapterProfile.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.name.setText("Name: "+profilePojoList.get(position).getName());
        holder.issuedate.setText("Issue Date: "+profilePojoList.get(position).getIssueDate());
        holder.percent.setText("Percentage Obtained: "+profilePojoList.get(position).getPercent());

    }

    @Override
    public int getItemCount() {
        return profilePojoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,issuedate,percent,viewCertificate;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            issuedate = itemView.findViewById(R.id.issueDate);
            percent = itemView.findViewById(R.id.percentageObtained);
            viewCertificate = itemView.findViewById(R.id.viewCertificate);
            constraintLayout = itemView.findViewById(R.id.constraintView3);
        }
    }
}

