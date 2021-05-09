package com.tejaswininimbalkar.krishisarathi.User.YourOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Adapter.OwnerAdapter;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter.PendingAdapter;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.PendingModel;

import java.util.ArrayList;


public class PendingFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    ArrayList<PendingModel> mList;
    PendingAdapter pendingAdapter;
    TextView OwnerName;
    String OwnerId;

    String userId;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    private String mParam1;
    private String mParam2;

    public PendingFragment() {
        // Required empty public constructor
    }



    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();
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
        View  view = inflater.inflate(R.layout.fragment_pending, container, false);

        recyclerView = view.findViewById(R.id.pendingRec);
        OwnerName = view.findViewById(R.id.Owner_name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mList = new ArrayList<>();
        pendingAdapter = new  PendingAdapter(getContext(), mList);

        recyclerView.setAdapter( pendingAdapter);

        user = auth.getCurrentUser();
        userId = user.getUid();


        databaseReference.child("User").child(userId).child("Pending Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String ID = dataSnapshot.getKey();
                        databaseReference.child("User").child(userId).child("Pending Request")
                                .child(ID)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       PendingModel model = snapshot.getValue(PendingModel.class);
                                       mList.add(model);
                                       pendingAdapter.notifyDataSetChanged();

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




        return  view;

    }


}