package com.tejaswininimbalkar.krishisarathi.Booking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Booking.Adapter.MyAdapter;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.MyModel;
import com.tejaswininimbalkar.krishisarathi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link recFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recView;
    MyAdapter adapter;
    String equipment_name;
    String equip_img_Url;
    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Equipment");



    public recFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static recFragment newInstance(String param1, String param2) {
        recFragment fragment = new recFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rec_d, container, false);




        recView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));

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

