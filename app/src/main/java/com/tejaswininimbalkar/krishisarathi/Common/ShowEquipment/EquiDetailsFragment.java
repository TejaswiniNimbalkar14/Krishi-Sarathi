package com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment;
/*
 * @author Devendra Kharatmal
 */
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Adapter.OwnerAdapter;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class EquiDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ImageView backBtn, cartBtn, equiImage;
    TextView equiName;
    OwnerAdapter ownerAdapter;

    RecyclerView recyclerView;
    ArrayList<OwnerModel> mList;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();

    String Machine_name, imageUri;
    private String mParam1;
    private String mParam2;

    public EquiDetailsFragment() {
        // Required empty public constructor
    }

    public EquiDetailsFragment(String machine_name, String imageUri) {
        Machine_name = machine_name;
        this.imageUri = imageUri;
    }

    public static EquiDetailsFragment newInstance(String param1, String param2) {
        EquiDetailsFragment fragment = new EquiDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_equi_details, container, false);

        backBtn = (ImageView) view.findViewById(R.id.backArrowCard);
        equiImage = (ImageView) view.findViewById(R.id.cardEquiImage);
        equiName = (TextView) view.findViewById(R.id.equipmentName);

        equiName.setText(Machine_name);
        Glide.with(getContext()).load(imageUri).into(equiImage);

        recyclerView = view.findViewById(R.id.detailsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mList = new ArrayList<>();
        ownerAdapter = new OwnerAdapter(getContext(), mList);

        recyclerView.setAdapter(ownerAdapter);

        databaseReference.child("Equipment").child(Machine_name).child("Owner Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String id = dataSnapshot.getKey();
                        databaseReference.child("Owner")
                                .child(id)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        OwnerModel model = snapshot.getValue(OwnerModel.class);
                                        model.setEquipment_Name(Machine_name);
                                        mList.add(model);
                                        ownerAdapter.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }

                                });
                    }


                }else{
                    Toast.makeText(getActivity(),"Data is not exist",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new AgriEquipmentFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }
}