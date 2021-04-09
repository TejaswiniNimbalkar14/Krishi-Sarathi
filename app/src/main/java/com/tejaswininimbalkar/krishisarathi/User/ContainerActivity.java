package com.tejaswininimbalkar.krishisarathi.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityContainerBinding;

import java.net.URISyntaxException;

/*
 * @author Tejaswini Nimbalkar
 */

public class ContainerActivity extends AppCompatActivity {

    ActivityContainerBinding activityContainerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContainerBinding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(activityContainerBinding.getRoot());

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Add fragment to frame of container activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new UserDashboardFragment()).commit();

        activityContainerBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.homeIcon:
                        selectedFragment = new UserDashboardFragment();
                        break;
                    case R.id.weatherIcon:
                        selectedFragment = new WeatherFragment();
                        break;
                    case R.id.agriIcon:
                        selectedFragment = new AgriEquipmentFragment();
                        break;
                    case R.id.profileIcon:
                        selectedFragment = new UserProfileFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

                return true;
            }
        });

    }
}