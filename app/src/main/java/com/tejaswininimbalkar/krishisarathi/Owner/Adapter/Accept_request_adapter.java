package com.tejaswininimbalkar.krishisarathi.Owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.AcceptReq_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.List;

public class Accept_request_adapter extends RecyclerView.Adapter<Accept_request_adapter.AcceptViewHolder>{

    Context context;
    List<AcceptReq_model> list;

    public Accept_request_adapter(Context context, List<AcceptReq_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AcceptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conform_booking_card,parent,false);

        return new AcceptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptViewHolder holder, int position) {
        holder.booking_id.setText("Booking ID :"+list.get(position).getBooking_Id());
        holder.equip_name.setText(list.get(position).getEquipment_name());
        holder.working_time.setText(list.get(position).getWorking_Date()+" "+list.get(position).getWorking_Time());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("User").child(list.get(position).getRequester_Id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String fullName = snapshot.child("fullName").getValue(String.class);
                    holder.requester_name.setText(fullName);

                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        reference.child("Equipment").child(list.get(position).getEquipment_name()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String equip_img_Url = snapshot.child("equip_img_Url").getValue(String.class);
                    Picasso.get().load(equip_img_Url).into(holder.equip_img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AcceptViewHolder extends RecyclerView.ViewHolder{

        TextView booking_id, equip_name, requester_name, land_address, working_time;
        ImageView equip_img;
        Button start,finish;

        public AcceptViewHolder(@NonNull View itemView) {
            super(itemView);

            booking_id = itemView.findViewById(R.id.booking_id);
            equip_name = itemView.findViewById(R.id.equipment_Name);
            requester_name = itemView.findViewById(R.id.Owner_name);
            land_address = itemView.findViewById(R.id.address);
            working_time = itemView.findViewById(R.id.work_time);
            equip_img = itemView.findViewById(R.id.equipment_image);
            start = itemView.findViewById(R.id.work_start);
            finish = itemView.findViewById(R.id.work_finish);

            start.setVisibility(View.VISIBLE);
        }
    }
}
