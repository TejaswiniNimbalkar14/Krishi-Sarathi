package com.tejaswininimbalkar.krishisarathi.Common.Weather;

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
import android.widget.Button;
import android.widget.EditText;
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
import com.tejaswininimbalkar.krishisarathi.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    ImageView weatherImage;
    TextView locality, desc, minTemp, maxTemp, humidity, wind;
    ProgressBar progressBar;
    Button reload;
    String cityName = "";
    int id = 0;

    FusedLocationProviderClient fusedLocationProviderClient;

    public static String updateWeatherInfo(int id) {

        if (id >= 200 && id <= 232) {
            return "icon_weather_thunderstorm2";
        } else if (id >= 300 && id <= 321) {
            return "icon_weather_drizzle";
        } else if (id >= 500 && id <= 531) {
            return "icon_weather_lightrain";
        } else if (id >= 600 && id <= 622) {
            return "icon_weather_snow1";
        } else if (id >= 701 && id <= 781) {
            return "icon_weather_foggy";
        }  else if (id == 800) {
            return "icon_weather_clear";
        } else if (id >= 801 && id <= 804) {
            return "icon_weather_cloudy";
        } else {
            return "icon_weather";
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocation();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherImage = view.findViewById(R.id.weatherImg);
        locality = view.findViewById(R.id.cityText);
        desc = view.findViewById(R.id.descText);
        minTemp = view.findViewById(R.id.minTempText);
        maxTemp = view.findViewById(R.id.maxTempText);
        humidity = view.findViewById(R.id.humidityText);
        wind = view.findViewById(R.id.windTxt);
        progressBar = view.findViewById(R.id.weatherProgress);
        reload = view.findViewById(R.id.weatherReload);



        if (!isConnected(getActivity())) {
            showConnectionDialog();
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });

        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                progressBar.setVisibility(View.VISIBLE);
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                                location.getLongitude(), 1);
                        locality.setText(addressList.get(0).getLocality() + ", " + addressList.get(0).getCountryName());
                        cityName = addressList.get(0).getLocality();
                        getWeatherData(cityName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Please turn on location service", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getWeatherData(String city) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call = apiInterface.getWeatherData(city);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    minTemp.setText(response.body().getMain().getTemp_min() + "\u00B0" + "C");
                    maxTemp.setText(response.body().getMain().getTemp_max() + "\u00B0" + "C");
                    wind.setText(response.body().getWind().getWind() + " m/s");
                    humidity.setText(response.body().getMain().getHumidity() + " %");

                    id = response.body().getWeatherList().get(0).getId();
                    String updateWeatherInfo = updateWeatherInfo(id);
                    updateWeatherImage(updateWeatherInfo);

                    desc.setText(response.body().getWeatherList().get(0).getDescription());
                    progressBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void updateWeatherImage(String updateWeatherInfo) {
        int resourceId = getResources().getIdentifier(updateWeatherInfo, "drawable", getActivity().getPackageName());
        weatherImage.setImageResource(resourceId);
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