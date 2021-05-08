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
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Equipment_Request_To_Owner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Equipment_Request_To_Owner extends Fragment {

    public String uid;
    public Equipment_Request_adapter equipmentRequestAdapter;
    public List<Booking_request_model> list = new ArrayList<>();
    public DatabaseReference reference;
    public FirebaseAuth mauth;
    public RecyclerView recyclerView;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Equipment_Request_To_Owner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Equipment_Request_To_Owner.
     */
    // TODO: Rename and change types and number of parameters
    public static Equipment_Request_To_Owner newInstance(String param1, String param2) {
        Equipment_Request_To_Owner fragment = new Equipment_Request_To_Owner();
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


    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment_request_to_owner, container, false);
        recyclerView =(RecyclerView) view.findViewById(R.id.recycler);

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