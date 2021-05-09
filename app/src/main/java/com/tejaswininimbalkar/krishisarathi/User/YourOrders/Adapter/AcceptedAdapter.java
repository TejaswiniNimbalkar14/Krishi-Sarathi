package com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.AcceptedModel;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.PendingModel;

import java.util.ArrayList;

public class AcceptedAdapter extends RecyclerView.Adapter<AcceptedAdapter.ViewHolder> {

    ArrayList<AcceptedModel> mList;
    Context context;
    String OwnerId;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();

    public  AcceptedAdapter (Context context ,ArrayList<AcceptedModel> mList) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public  AcceptedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_pending, parent ,false);
        return new AcceptedAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull AcceptedAdapter.ViewHolder holder, int position) {
        AcceptedModel acceptedModel = mList.get(position);


        holder.booking_id.setText( acceptedModel.getBooking_Id());

        String equipmentName = acceptedModel.getEquipment_name();
        holder.equipname.setText(equipmentName);

        /*holder.workingdate.setText(pendingModel.getWorking_Date());
        holder.workingtime.setText(pendingModel.getWorking_Time());*/
        String ownerId = acceptedModel.getOwner_ID();

        databaseReference.child("User").child(ownerId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String ownerName = snapshot.child("fullName").getValue().toString();
                        holder.OwnerName.setText(ownerName);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        databaseReference.child("Equipment").child(equipmentName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String imgUri= snapshot.child("equip_img_Url").getValue().toString();
                        Glide.with(holder.equip_img.getContext()).load(imgUri).into(holder.equip_img);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView booking_id,workingdate,workingtime,equipname,owner_name;
        TextView OwnerName;

        ImageView equip_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            booking_id = itemView.findViewById(R.id.booking_id);
            equipname = itemView.findViewById(R.id.equipment_Name);
            OwnerName = itemView.findViewById(R.id.Owner_name);
            equip_img = itemView.findViewById(R.id.equipment_image);
            /*workingdate = itemView.findViewById(R.id.working_Date);
            workingtime = itemView.findViewById(R.id.working_Time);*/


        }
    }
}
