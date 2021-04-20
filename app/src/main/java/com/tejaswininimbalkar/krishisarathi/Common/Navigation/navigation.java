
//Leena Bhadane
package com.tejaswininimbalkar.krishisarathi.Common.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.tejaswininimbalkar.krishisarathi.R;

public class navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.activity_navigation );

        drawerLayout = findViewById ( R.id.nav_view );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        NavigationView navigationView = findViewById ( R.id.navigation_view );
        navigationView.setNavigationItemSelectedListener ( this );
        getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new homefragment ( ) );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawerLayout, toolbar, R.string.navigation_draw_open, R.string.navigation_draw_close );

        drawerLayout.addDrawerListener ( toggle );
        toggle.syncState ( );

        if (savedInstanceState == null) {
            getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new homefragment ( ) ).commit ( );
            navigationView.setCheckedItem ( R.id.home );
        }

    }


    @SuppressLint({"NonConstantResourceId"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId ( )) {
//            case R.id.home:
//                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new homefragment ( ) ).commit ( );
//                break;
//            case R.id.profile:
//                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new profilefragment ( ) ).commit ( );
//                break;
            case R.id.government_scheme:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new government_schemefragment ( ) ).commit ( );
                break;
            case R.id.farmer_guide:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new farmer_guidefragment ( ) ).commit ( );
                break;

//            case R.id.location:
//                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new locationfragment ( ) ).commit ( );
//                break;
//            case R.id.orders:
//                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new ordersfragment ( ) ).commit ( );
//                break;
            case R.id.help:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new helpfragment ( ) ).commit ( );
                break;
            case R.id.about_us:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new about_usfragment ( ) ).commit ( );
                break;
            case R.id.rate_us:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new rate_usfragment ( ) ).commit ( );
                break;
            case R.id.feedback:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new feedbackfragment ( ) ).commit ( );
                break;
            case R.id.share:
                getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new sharefragment ( ) ).commit ( );
                break;
            //case R.id.equipment_owner:
              //  getSupportFragmentManager ( ).beginTransaction ( ).replace ( R.id.Fragment_container, new Fragment () ).commit ( );
             //  break;
              case R.id.equipment_owner:
              Intent intent=new Intent ( this,Equipment_owner.class );
               startActivity (intent);
              break;

        }
            drawerLayout.closeDrawer ( GravityCompat.START );

            return true;
        }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen ( GravityCompat.START )) {
            drawerLayout.closeDrawer ( GravityCompat.START );
        } else {

            super.onBackPressed ( );
        }


    }
}