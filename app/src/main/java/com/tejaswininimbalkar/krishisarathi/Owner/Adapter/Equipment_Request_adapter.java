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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Request_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class Equipment_Request_adapter extends RecyclerView.Adapter<Equipment_Request_adapter.RequestHolder> {

    ArrayList<Request_model> list;
    Context context;

    public Equipment_Request_adapter(ArrayList<Request_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_equipment_request_to_owner,parent,false);
        RequestHolder requestHolder = new RequestHolder(view);

        return requestHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHolder holder, int position) {
        holder.booking_id.setText(list.get(position).getBooking_Id());
        holder.equip_name.setText(list.get(position).getEquipment_name());
        //holder.working_time.setText(list.get(position).getWorking_Date()+" "+list.get(position).getWorking_Time());

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        reference.child("User").child(list.get(position).getRequester_Id()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                try {
//                    String fullName = snapshot.getValue(String.class);
//                    holder.requester_name.setText(fullName);
//                }catch (Exception e){
//                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RequestHolder extends RecyclerView.ViewHolder{

        TextView booking_id,equip_name,requester_name,land_address,working_time;
        ImageView equip_img;
        FloatingActionButton conform_request,cancel_request;

        public RequestHolder(@NonNull View itemView) {
            super(itemView);

            booking_id = itemView.findViewById(R.id.booking_id);
            equip_name = itemView.findViewById(R.id.equip_name);
            requester_name = itemView.findViewById(R.id.requester_name);
            land_address = itemView.findViewById(R.id.address);
            working_time = itemView.findViewById(R.id.work_time);
            equip_img = itemView.findViewById(R.id.equip_img);
            conform_request = itemView.findViewById(R.id.request_accept);
            cancel_request = itemView.findViewById(R.id.request_cancel);
        }
    }
}
