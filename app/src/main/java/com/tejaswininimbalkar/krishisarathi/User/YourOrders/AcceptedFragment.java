package com.tejaswininimbalkar.krishisarathi.User.YourOrders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter.AcceptedAdapter;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Adapter.PendingAdapter;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.AcceptedModel;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.Model.PendingModel;

import java.util.ArrayList;


public class AcceptedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    ArrayList<AcceptedModel> mList;
    AcceptedAdapter acceptedAdapter;
    TextView OwnerName;
    String requesterId;
    String userId;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user;

    private String mParam1;
    private String mParam2;

    public AcceptedFragment() {
    }

    public static AcceptedFragment newInstance(String param1, String param2) {
        AcceptedFragment fragment = new AcceptedFragment();
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
        View  view = inflater.inflate(R.layout.fragment_accepted, container, false);

        recyclerView = view.findViewById(R.id.acceptedRec);
        OwnerName = view.findViewById(R.id.Owner_name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mList = new ArrayList<>();
        acceptedAdapter = new AcceptedAdapter(getContext(), mList);

        recyclerView.setAdapter( acceptedAdapter);

        user = auth.getCurrentUser();
        userId = user.getUid();


        databaseReference.child("Booking").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String ID = dataSnapshot.getKey();
                        databaseReference.child("Booking").child(ID)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        requesterId = snapshot.child("Requester_Id").getValue().toString();
                                        if(userId.equals(requesterId)){
                                            AcceptedModel model = snapshot.getValue(AcceptedModel.class);
                                            mList.add(model);
                                            acceptedAdapter.notifyDataSetChanged();
                                        }
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