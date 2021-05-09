package com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.Booking.BookingActivity;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.PendingModel;

import java.util.ArrayList;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.ViewHolder> {


    ArrayList<PendingModel> mList;
    Context context;
    String OwnerId;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();

     public PendingAdapter(Context context ,ArrayList<PendingModel> mList) {
            this.mList = mList;
            this.context = context;
     }

    @NonNull
    @Override
    public PendingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_pending, parent ,false);
        return new PendingAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull PendingAdapter.ViewHolder holder, int position) {
        PendingModel pendingModel = mList.get(position);
        holder.booking_id.setText(pendingModel.getBooking_Id());
        holder.equipname.setText(pendingModel.getEquipment_name());

        /*holder.workingdate.setText(pendingModel.getWorking_Date());
        holder.workingtime.setText(pendingModel.getWorking_Time());*/
        OwnerId = pendingModel.getOwner_Id();

        databaseReference.child("User").child(OwnerId)
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

            //equip_img = itemView.findViewById(R.id.equipment_image);
            /*workingdate = itemView.findViewById(R.id.working_Date);
            workingtime = itemView.findViewById(R.id.working_Time);*/


        }
    }
}
