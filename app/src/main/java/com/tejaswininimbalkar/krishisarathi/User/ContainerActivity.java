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
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityContainerBinding;

import java.net.URISyntaxException;

/*
 * @author Tejaswini Nimbalkar
 */

public class ContainerActivity extends AppCompatActivity {

    ActivityContainerBinding activityContainerBinding;
    FirebaseUser mAuth;
    boolean isPresent = false;

    @Override
    protected void onStart() {
        super.onStart();
//        try {
//            mAuth = FirebaseAuth.getInstance();
//            if (mAuth != null){
//                isPresent = true;
//            }
//        }catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContainerBinding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(activityContainerBinding.getRoot());

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (mAuth != null){
            activityContainerBinding.logOut.setVisibility(View.VISIBLE);
            activityContainerBinding.logIn.setVisibility(View.GONE);
        }else {
            activityContainerBinding.logOut.setVisibility(View.GONE);
            activityContainerBinding.logIn.setVisibility(View.VISIBLE);
        }

        activityContainerBinding.logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Send_Otp_Page.class);
                startActivity(intent);
            }
        });

        activityContainerBinding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                activityContainerBinding.logOut.setVisibility(View.GONE);
                activityContainerBinding.logIn.setVisibility(View.VISIBLE);
            }
        });

//        SessionManager sessionManager = new SessionManager(this);
//
//        if(!sessionManager.checkLogin()) {
//            activityContainerBinding.bottomNavigation.inflateMenu(R.menu.bottom_navigation_logged_out);
//        }
//        else {
//            activityContainerBinding.bottomNavigation.inflateMenu(R.menu.bottom_navigation);
//        }

        activityContainerBinding.bottomNavigation.inflateMenu(R.menu.bottom_navigation);

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