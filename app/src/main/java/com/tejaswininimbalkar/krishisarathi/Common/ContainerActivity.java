package com.tejaswininimbalkar.krishisarathi.Common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.Dashboard.UserDashboardFragment;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.YourOrdersActivity;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.about_usfragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.farmer_guidefragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.feedbackfragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.government_schemefragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.helpfragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.rate_usfragment;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.sharefragment;
import com.tejaswininimbalkar.krishisarathi.Common.ShowEquipment.AgriEquipmentFragment;
import com.tejaswininimbalkar.krishisarathi.Common.Weather.WeatherFragment;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.Owner.Owner_Welcome;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.UserProfileFragment;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityContainerBinding;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

/*

 * @author Tejaswini Nimbalkar and Leena Bhadane

 */

public class ContainerActivity extends AppCompat implements NavigationView.OnNavigationItemSelectedListener {

    ActivityContainerBinding activityContainerBinding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private FirebaseAuth mAuth;

    private FirebaseUser user;

    private DatabaseReference reference;

//    String address, activityTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        super.onCreate(savedInstanceState);
        activityContainerBinding = ActivityContainerBinding.inflate(getLayoutInflater());
        setContentView(activityContainerBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        if (user != null) {
            activityContainerBinding.bottomNavigation.inflateMenu(R.menu.bottom_navigation);
            reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid());
        } else {
            activityContainerBinding.bottomNavigation.inflateMenu(R.menu.bottom_navigation_logged_out);
        }

        //To hide bottom navigation when onscreen keyboard opens up
        //and display when closes
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        Log.d("tag", "onVisibilityChanged: Keyboard visibility changed");
                        if (isOpen) {
                            Log.d("tag", "onVisibilityChanged: Keyboard is open");
                            activityContainerBinding.bottomNavigation.setVisibility(View.INVISIBLE);
                            Log.d("tag", "onVisibilityChanged: NavBar got Invisible");
                        } else {
                            Log.d("tag", "onVisibilityChanged: Keyboard is closed");
                            activityContainerBinding.bottomNavigation.setVisibility(View.VISIBLE);
                            Log.d("tag", "onVisibilityChanged: NavBar got Visible");
                        }
                    }
                });

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Add fragment to frame of container activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


//        address = getIntent().getStringExtra("address");
//        activityTransaction = getIntent().getStringExtra("activity");
//        if (activityTransaction.equals("GetAddress")){
//            fragmentTransaction.replace(R.id.fragmentContainer, new UserDashboardFragment(address,activityTransaction)).addToBackStack(null).commit();
//            activityContainerBinding.menuBtn.setVisibility(View.VISIBLE);
//        }else if (activityTransaction.equals("Verify")){
//            fragmentTransaction.replace(R.id.fragmentContainer, new UserDashboardFragment()).commit();
//            activityContainerBinding.menuBtn.setVisibility(View.VISIBLE);
//        }

        fragmentTransaction.replace(R.id.fragmentContainer, new UserDashboardFragment()).commit();
        activityContainerBinding.menuBtn.setVisibility(View.VISIBLE);

        activityContainerBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.homeIcon:
                        selectedFragment = new UserDashboardFragment();
                        activityContainerBinding.menuBtn.setVisibility(View.VISIBLE);
                        break;
                    case R.id.weatherIcon:
                        selectedFragment = new WeatherFragment();
                        activityContainerBinding.menuBtn.setVisibility(View.GONE);
                        break;
                    case R.id.agriIcon:
                        selectedFragment = new AgriEquipmentFragment();
                        activityContainerBinding.menuBtn.setVisibility(View.GONE);
                        break;
                    case R.id.profileIcon:
                        selectedFragment = new UserProfileFragment();
                        activityContainerBinding.menuBtn.setVisibility(View.GONE);
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();

                return true;
            }
        });

        activityContainerBinding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.government_scheme:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new government_schemefragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.farmer_guide:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new farmer_guidefragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new helpfragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new about_usfragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.rate_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new rate_usfragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.feedback:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new feedbackfragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;
            case R.id.share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new sharefragment()).commit();
                activityContainerBinding.menuBtn.setVisibility(View.GONE);
                break;

            case R.id.equipment_owner:
                if (user != null) {

                    DatabaseReference root = FirebaseDatabase.getInstance().getReference("Owner");  // make a new Database Reference
                    Query query = root.orderByChild("owner_ID").equalTo(user.getUid());  // here check this login user present in owner child


                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent intent = new Intent(ContainerActivity.this, OwnerLoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(ContainerActivity.this, Owner_Welcome.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ContainerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "First of all Login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, Send_Otp_Page.class);
                    i.putExtra("mustLoginFirst", "mustLoginForOwner");
                    startActivity(i);
                    finish();
                }
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }
}