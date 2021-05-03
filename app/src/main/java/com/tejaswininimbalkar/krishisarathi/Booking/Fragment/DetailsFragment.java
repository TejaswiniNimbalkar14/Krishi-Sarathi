package com.tejaswininimbalkar.krishisarathi.Booking.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Booking.Adapter.OwnerAdapter;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.OwnerModel;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.EquiDetailsFragment;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String Machine_name, Machine_Price, Descp, imageUri;


    // TODO: Rename and change types of parameters
    RecyclerView recycler;
    LinearLayoutManager layout;
    List<OwnerModel> userList;
    OwnerAdapter ownerAdapter;
    private String mParam1;
    private String mParam2;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public DetailsFragment(String machine_name, String imageUri) {

        Machine_name = machine_name;
       /* Machine_Price = machine_Price;
        Descp = descp;*/
        this.imageUri = imageUri;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_details_d, container, false);

        ImageView imageView = view.findViewById(R.id.equipment_img1);
        TextView textView, textView1, textView2;
        Button bookNow, backIcon;


        backIcon = view.findViewById(R.id.back_icon);
        //bookNow = view.findViewById(R.id.bookNow);

        textView = view.findViewById(R.id.equipment_name1);
       /* textView1 = view.findViewById(R.id.priceHolder);
        textView2 = view.findViewById(R.id.decpHolder);*/


        textView.setText(Machine_name);
        // textView1.setText(Machine_Price);
        //textView2.setText(Descp);

        textView1 = view.findViewById(R.id.owner_count);

        Glide.with(getContext()).load(imageUri).into(imageView);

      /*  bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s= textView.getText().toString();

                Intent intent = new Intent(getActivity(), BookingActivity.class);
                intent.putExtra("name",s);
                startActivity(intent);


            }
        });*/


        recycler = view.findViewById(R.id.ownerRec1);
        layout = new LinearLayoutManager(getContext());
        layout.setOrientation(RecyclerView.VERTICAL);


        FirebaseRecyclerOptions<OwnerModel> opt = new FirebaseRecyclerOptions.Builder<OwnerModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Equipment"), OwnerModel.class)
                .build();

        ownerAdapter = new OwnerAdapter(opt);

        recycler.setAdapter(ownerAdapter);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompat activity = (AppCompat) getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.drawerLayout, new recFragment()).addToBackStack(null).commit();
            }
        });

        return view;
    }


}