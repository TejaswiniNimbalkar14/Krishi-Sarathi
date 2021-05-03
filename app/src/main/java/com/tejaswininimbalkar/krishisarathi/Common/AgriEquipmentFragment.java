package com.tejaswininimbalkar.krishisarathi.Common;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Booking.Adapter.MyAdapter;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.MyModel;
import com.tejaswininimbalkar.krishisarathi.R;

public class AgriEquipmentFragment extends Fragment {
    RecyclerView recView;
    MyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agri_equipment, container, false);

        recView = (RecyclerView) view.findViewById(R.id.agriRecyclerView);
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<MyModel> options = new FirebaseRecyclerOptions.Builder<MyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Equipment"), MyModel.class)
                .build();
        adapter = new MyAdapter(options);

        recView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}