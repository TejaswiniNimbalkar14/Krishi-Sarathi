package com.tejaswininimbalkar.krishisarathi.Common.Dashboard;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

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
    FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationEt;
    private GridviewAdapter mAdapter;
    private ArrayList<String> listTitle;
    private ArrayList<Integer> listImage;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        getLocationAndSetToView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        //wlcmMsg = (TextView) view.findViewById(R.id.wlcmMsg);
        enterSession = (ImageView) view.findViewById(R.id.enterUserSession);
        notification = (ImageView) view.findViewById(R.id.icon_notification);
        locationEt = (TextView) view.findViewById(R.id.locationTv);
        progressBar = (ProgressBar) view.findViewById(R.id.dasboardProgress);

        progressBar.setVisibility(View.VISIBLE);



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

    private void getLocationAndSetToView() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        locationEt.setText(addressList.get(0).getLocality() + ", " + addressList.get(0).getCountryName());
                        progressBar.setVisibility(View.GONE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Please turn on location service", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        listTitle.add(getResources().getString(R.string.farmer));
        listTitle.add(getResources().getString(R.string.equipment));
        listTitle.add(getResources().getString(R.string.fertilizer));
        listTitle.add(getResources().getString(R.string.market_price));
        listTitle.add(getResources().getString(R.string.seeds));
        listTitle.add(getResources().getString(R.string.transport));

        listImage = new ArrayList<>();
        listImage.add(R.drawable.image_farmer);
        listImage.add(R.drawable.image__equipments);
        listImage.add(R.drawable.image_fertiliser);
        listImage.add(R.drawable.image_marketpr);
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