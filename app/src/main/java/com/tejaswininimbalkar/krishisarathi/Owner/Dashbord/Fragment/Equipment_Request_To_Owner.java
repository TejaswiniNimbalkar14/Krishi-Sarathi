package com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Owner.Adapter.Equipment_Request_adapter;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Booking_request_model;

import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_info;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Request_model;

import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;
import java.util.List;

public class Equipment_Request_To_Owner extends Fragment {

    public String uid;
    public Equipment_Request_adapter equipmentRequestAdapter;
    public List<Booking_request_model> list = new ArrayList<>();
    public DatabaseReference reference;
    public FirebaseAuth mauth;
    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment_request_to_owner, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser user = mauth.getCurrentUser();
        uid = user.getUid();

        equipmentRequestAdapter = new Equipment_Request_adapter(getContext(), list);
        recyclerView.setAdapter(equipmentRequestAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference();

        //list.add(new Booking_request_model("ghggsaguydg","hgggsguga","husguydguga","56454678","RTJYM47hfRPyO5DqQppmGISOf7y1"));

        reference.child("Owner").child(uid).child("Booking_Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        String id = dataSnapshot.getKey();
//                        reference.child("Owner")
//                                .child(uid)
//                                .child("Booking_Request")
//                                .child(id)
//                                .child("Order No 1")
//                                .addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        Request_model model = snapshot.getValue(Request_model.class);
//                                        list.add(model);
//                                        equipmentRequestAdapter.notifyDataSetChanged();
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Data is not exist", Toast.LENGTH_LONG).show();
//                }

                list.clear();
                for (DataSnapshot data : snapshot.getChildren()){
                     Booking_request_model model = data.getValue(Booking_request_model.class);
                    Toast.makeText(getContext(), model.getEquipment_name(), Toast.LENGTH_SHORT).show();
                     list.add(model);
                    equipmentRequestAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}