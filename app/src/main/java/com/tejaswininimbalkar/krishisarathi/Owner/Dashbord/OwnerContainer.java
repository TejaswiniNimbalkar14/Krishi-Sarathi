package com.tejaswininimbalkar.krishisarathi.Owner.Dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.UserDashboardFragment;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.AgriEquipmentFragment;
import com.tejaswininimbalkar.krishisarathi.Common.Weather.WeatherFragment;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.Equipment_Menu;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.Equipment_Request_To_Owner;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.History_Of_Working;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.Income_status;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.OwnerSettingsFragment;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment.Req_accpt_com;
import com.tejaswininimbalkar.krishisarathi.Owner.MyEquipmentFragment;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.UserProfileFragment;

public class OwnerContainer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_container);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new Req_accpt_com()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.equipment_req:
                selectedFragment = new Req_accpt_com();
                break;
            case R.id.equipment_add:
                selectedFragment = new MyEquipmentFragment();
                break;
            case R.id.total_income:
                selectedFragment = new Income_status();
                break;
            case R.id.history:
                selectedFragment = new History_Of_Working();
                break;
            case R.id.settings:
                selectedFragment = new OwnerSettingsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

        return true;
    }

}