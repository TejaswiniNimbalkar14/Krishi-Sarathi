package com.tejaswininimbalkar.krishisarathi.Common;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Booking.Adapter.OwnerAdapter;
import com.tejaswininimbalkar.krishisarathi.Booking.Fragment.DetailsFragment;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.R;

import org.w3c.dom.Text;

import javax.crypto.Mac;

public class EquiDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recycler;
    ImageView backBtn, cartBtn, equiImage;
    TextView equiName;
    LinearLayoutManager layout;
    OwnerAdapter ownerAdapter;

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

        recycler = (RecyclerView) view.findViewById(R.id.detailsRecyclerView);
        backBtn = (ImageView) view.findViewById(R.id.backArrowCard);
        equiImage = (ImageView) view.findViewById(R.id.cardEquiImage);
        equiName = (TextView) view.findViewById(R.id.equipmentName);

        equiName.setText(Machine_name);
        Glide.with(getContext()).load(imageUri).into(equiImage);

        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        FirebaseRecyclerOptions<OwnerModel> options = new FirebaseRecyclerOptions.Builder<OwnerModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Equipment"), OwnerModel.class)
                .build();

        ownerAdapter = new OwnerAdapter(options);

        recycler.setAdapter(ownerAdapter);

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