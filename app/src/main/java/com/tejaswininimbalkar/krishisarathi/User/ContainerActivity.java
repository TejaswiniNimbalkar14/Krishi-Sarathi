package com.tejaswininimbalkar.krishisarathi.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityContainerBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class ContainerActivity extends AppCompatActivity {

    ActivityContainerBinding activityContainerBinding;
    String extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContainerBinding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(activityContainerBinding.getRoot());

//        if(getIntent() != null) {
//            extra = getIntent().getStringExtra("settings");
//        }
//        System.out.print(extra);
//
//        if(extra == "Settings") {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserProfileFragment()).commit();
//        } else {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserDashboardFragment()).commit();
//        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new UserDashboardFragment()).commit();

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