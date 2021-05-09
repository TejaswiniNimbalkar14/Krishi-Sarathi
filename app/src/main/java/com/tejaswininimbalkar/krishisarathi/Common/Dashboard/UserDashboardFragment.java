package com.tejaswininimbalkar.krishisarathi.Common.Dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

/*
 * @author Leena Bhadane and Tejaswini Nimbalkar
 */

public class UserDashboardFragment extends Fragment {

    int[] images = {

            R.drawable.image_slider_one,
            R.drawable.image_slider_two,
            R.drawable.image_slider_three,
            R.drawable.image_slider_four,
            R.drawable.image_slider_five,
            R.drawable.image_slider_six
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

        if (!isConnected(getActivity())) {
            showConnectionDialog();
        }

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            enterSession.setVisibility(View.VISIBLE);
            notification.setVisibility(View.GONE);
            enterUserSession();
        } else {
            enterSession.setVisibility(View.GONE);
            notification.setVisibility(View.VISIBLE);
        }

        SliderView sliderView = (SliderView) view.findViewById(R.id.imageSlider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        prepareList();

        mAdapter = new GridviewAdapter(this, listTitle, listImage);

        GridView gridView = (GridView) view.findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener((arg0, arg1, position, arg3) -> {
            Toast.makeText(getContext(), mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), GridItemActivity.class);
            intent.putExtra("Title", mAdapter.getItem(position));
            startActivity(intent);

        });

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

    public void prepareList() {
        listTitle = new ArrayList<>();
        listTitle.add("Farmer");
        listTitle.add("Equipments");
        listTitle.add("Fertilisers");
        listTitle.add("Market price");
        listTitle.add("Seeds");
        listTitle.add("Transportation");

        listImage = new ArrayList<>();
        listImage.add(R.drawable.image_farmer);
        listImage.add(R.drawable.image_equipment);
        listImage.add(R.drawable.image_fertilizer);
        listImage.add(R.drawable.image_market_price);
        listImage.add(R.drawable.image_seed);
        listImage.add(R.drawable.image_transportation);
    }

    private boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected());
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("Please connect to the internet to move further!");
        alertDialog.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}