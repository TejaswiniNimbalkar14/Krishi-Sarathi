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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Owner.Adapter.Accept_request_adapter;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.AcceptReq_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;
import java.util.List;


public class EquipmentRequestAccept extends Fragment {

    RecyclerView recyclerView;
    Accept_request_adapter adapter;
    List<AcceptReq_model> list;
    DatabaseReference reference;
    String uid;

    public EquipmentRequestAccept() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment_request_accept, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        recyclerView = view.findViewById(R.id.recycler);

        list = new ArrayList<>();
        adapter = new Accept_request_adapter(getContext(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Booking").orderByChild("Owner_ID").equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    list.clear();
                    for (DataSnapshot data : snapshot.getChildren()){
                        AcceptReq_model model = data.getValue(AcceptReq_model.class);
                        Toast.makeText(getContext(), model.getBooking_Id(), Toast.LENGTH_SHORT).show();
                        list.add(model);
                    }
                    adapter.notifyDataSetChanged();
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