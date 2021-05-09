package com.tejaswininimbalkar.krishisarathi.Owner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Booking_request_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.HashMap;
import java.util.List;

public class Equipment_Request_adapter extends RecyclerView.Adapter<Equipment_Request_adapter.RequestHolder> {

    Context context;
    List<Booking_request_model> list;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    public Equipment_Request_adapter(Context context, List<Booking_request_model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_request_to_rent_card, parent, false);
        RequestHolder requestHolder = new RequestHolder(view);
        return requestHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        holder.booking_id.setText("Booking ID :"+list.get(position).getBooking_Id());
        holder.equip_name.setText(list.get(position).getEquipment_name());
        holder.working_time.setText(list.get(position).getWorking_Date()+" "+list.get(position).getWorking_Time());

        Toast.makeText(context, list.get(position).getEquipment_name(), Toast.LENGTH_SHORT).show();
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

    public class RequestHolder extends RecyclerView.ViewHolder {

        TextView booking_id, equip_name, requester_name, land_address, working_time;
        ImageView equip_img;
        FloatingActionButton conform_request, cancel_request;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);

            booking_id = itemView.findViewById(R.id.booking_id);
            equip_name = itemView.findViewById(R.id.equipment_Name);
            requester_name = itemView.findViewById(R.id.Owner_name);
            land_address = itemView.findViewById(R.id.address);
            working_time = itemView.findViewById(R.id.work_time);
            equip_img = itemView.findViewById(R.id.equipment_image);
            conform_request = itemView.findViewById(R.id.request_accept);
            cancel_request = itemView.findViewById(R.id.request_cancel);

            conform_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Booking_request_model position = list.get(getAdapterPosition());
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("Booking_Id",position.getBooking_Id());
                    map.put("Equipment_name",position.getEquipment_name());
                    map.put("Requester_Id",position.getRequester_Id());
                    map.put("Working_Date",position.getWorking_Date());
                    map.put("Working_Time",position.getWorking_Time());
                    map.put("Working_Address",land_address.getText().toString());
                    map.put("Owner_ID",uid);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("Booking").child(position.getBooking_Id()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Booking Conform", Toast.LENGTH_SHORT).show();

                            reference.child("Owner").child(uid).child("Booking_Request").child(position.getBooking_Id()).removeValue();
                            reference.child("User").child(position.getRequester_Id()).child("Pending Request").child(position.getBooking_Id()).removeValue();
                        }
                    });


                }
            });

            cancel_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Cancel",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
