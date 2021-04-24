package com.tejaswininimbalkar.krishisarathi.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.Dashboard;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.GridItemActivity;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.GridviewAdapter;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.SliderAdapter;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * @author Leena Bhadane and Tejaswini Nimbalkar
 */

public class UserDashboardFragment extends Fragment {

    int[] images = {

            R.drawable.first_image,
            R.drawable.second_image,
            R.drawable.third_image,
            R.drawable.fourth_image,
            R.drawable.fifth_image,
            R.drawable.sixth_image
    };
    //private TextView wlcmMsg;
    private ImageView enterSession, notification;
    private GridviewAdapter mAdapter;
    private ArrayList<String> listTitle;
    private ArrayList<Integer> listImage;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        //wlcmMsg = (TextView) view.findViewById(R.id.wlcmMsg);
        enterSession = (ImageView) view.findViewById(R.id.enterUserSession);
        notification = (ImageView) view.findViewById(R.id.icon_notification);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            enterSession.setVisibility(View.VISIBLE);
            notification.setVisibility(View.GONE);
            enterUserSession();
        } else {
            enterSession.setVisibility(View.GONE);
            notification.setVisibility(View.VISIBLE);
        }

        SliderView sliderView = (SliderView) view.findViewById ( R.id.imageSlider );

        SliderAdapter sliderAdapter = new SliderAdapter ( images );
        sliderView.setSliderAdapter ( sliderAdapter );
        sliderView.setIndicatorAnimation ( IndicatorAnimationType.WORM );
        sliderView.setSliderTransformAnimation ( SliderAnimations.DEPTHTRANSFORMATION );
        sliderView.startAutoCycle ( );

        prepareList();

        mAdapter = new GridviewAdapter(this, listTitle, listImage);

        GridView gridView = (GridView) view.findViewById ( R.id.gridView1 );
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener( (arg0, arg1, position, arg3) -> {
            Toast.makeText( getContext(), mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent ( getContext(), GridItemActivity.class );
            intent.putExtra("Title", mAdapter.getItem(position));
            startActivity (intent);

        } );

        return view;
    }

    private void enterUserSession() {
        enterSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Send_Otp_Page.class));
                getActivity().finish();
            }
        });
    }

    public void prepareList()
    {
        listTitle = new ArrayList<> ( );
        listTitle.add("Farmer");
        listTitle.add("Equipments");
        listTitle.add("Fertilisers");
        listTitle.add("Market price");
        listTitle.add("Seeds");
        listTitle.add("Transportation");

        listImage = new ArrayList<> ( );
        listImage.add(R.drawable.farmer_image);
        listImage.add(R.drawable.equipments_image );
        listImage.add(R.drawable.fertilizer_image);
        listImage.add(R.drawable.marketpr_image);
        listImage.add(R.drawable.seed_image);
        listImage.add(R.drawable.transportation_image);
    }
}