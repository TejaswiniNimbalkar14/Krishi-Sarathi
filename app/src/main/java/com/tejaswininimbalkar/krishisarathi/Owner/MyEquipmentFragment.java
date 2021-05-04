package com.tejaswininimbalkar.krishisarathi.Owner;

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
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.Owner.Adapter.MyEquiAdapter;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_info;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class MyEquipmentFragment extends Fragment {
    RecyclerView recyclerView;
    MyEquiAdapter adapter;

    ArrayList<Equipment_info> mList;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_equipment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.myEquiRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mList = new ArrayList<>();

        adapter = new MyEquiAdapter(mList, getContext());
        recyclerView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference.child("Owner").child(uid).child("Equipment_Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String id = dataSnapshot.getKey();
                        databaseReference.child("Owner")
                                .child(uid)
                                .child("Equipment_Details")
                                .child(id)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Equipment_info info = snapshot.getValue(Equipment_info.class);
                                        mList.add(info);
                                        adapter.notifyDataSetChanged();

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }

                                });
                    }


                } else {
                    Toast.makeText(getActivity(), "Data is not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}