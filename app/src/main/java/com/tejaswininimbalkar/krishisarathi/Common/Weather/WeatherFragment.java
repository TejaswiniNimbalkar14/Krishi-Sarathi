package com.tejaswininimbalkar.krishisarathi.Common.Weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tejaswininimbalkar.krishisarathi.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {


    Button getInfo;
    TextView tempText, descText, cityText, humidityText, lattitude, longitude, loactionTxt, country;
    String name = "";

    FusedLocationProviderClient fusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        cityText = (TextView) view.findViewById(R.id.cityText);
//        code = (EditText) view.findViewById(R.id.cityCode);
//        getInfo = (Button) view.findViewById(R.id.getInfoBtn);
        tempText = (TextView) view.findViewById(R.id.tempText);
        descText = (TextView) view.findViewById(R.id.descText);
        humidityText = (TextView) view.findViewById(R.id.humidity);
//        lattitude = (TextView) view.findViewById(R.id.latitude);
//        longitude = (TextView) view.findViewById(R.id.longitude);
//        loactionTxt = (TextView) view.findViewById(R.id.locationText);
//        country = (TextView) view.findViewById(R.id.country);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocation();
        //getWeatherData(name);


//        getInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getWeatherData(city.getText().toString().trim());
//                showLocation();
//            }
//        });

        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
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
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
//                        lattitude.setText("Lattitude : " + addressList.get(0).getLatitude());
//                        longitude.setText("Longitude : " + addressList.get(0).getLongitude());
                        cityText.setText("Location : " + addressList.get(0).getLocality());
//                        country.setText("Location : " + addressList.get(0).getCountryName());
                        name = addressList.get(0).getLocality();
                        getWeatherData(name);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Location null error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getWeatherData(String name) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getWeatherData(name);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                tempText.setText(response.body().getMain().getTemp());
                descText.setText(response.body().getMain().getFeels_like());
                humidityText.setText(response.body().getMain().getHumidity());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }

}