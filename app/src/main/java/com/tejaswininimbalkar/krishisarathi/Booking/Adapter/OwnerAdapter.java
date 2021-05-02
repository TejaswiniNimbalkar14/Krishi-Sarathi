package com.tejaswininimbalkar.krishisarathi.Booking.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tejaswininimbalkar.krishisarathi.Booking.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;

import java.security.acl.Owner;
import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {

    ArrayList<OwnerModel> mList;
    Context context;

    public OwnerAdapter( Context context, ArrayList<OwnerModel> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public OwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.owner_card_d, parent ,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerAdapter.ViewHolder holder, int position) {
        OwnerModel ownerModel = mList.get(position);
        holder.ownerName.setText(ownerModel.getOwner_Name());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView ownerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ownerName = itemView.findViewById(R.id.owner_name);
        }
    }



}
