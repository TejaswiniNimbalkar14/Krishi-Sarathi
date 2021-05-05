package com.tejaswininimbalkar.krishisarathi.User;

/*
 * @author Tejaswini Nimbalkar
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.Common.Localization.SelectLanguage;
import com.tejaswininimbalkar.krishisarathi.R;

public class UserSettings extends AppCompat {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.settingsProgressBar);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void changeLanguage(View view) {
        Intent i = new Intent(this, SelectLanguage.class);
        i.putExtra("changeBtns", "Change Buttons");
        startActivity(i);
        //finish();
    }

    public void editProfile(View view) {
        Intent i = new Intent(this, EditProfileActivity.class);
        i.putExtra("cameFrom1", "userSettings");
        startActivity(i);
        //finish();
    }

    public void deleteAccount(View view) {
        deleteDialog();
    }

    public void logOut(View view) {
        logoutDialog();
    }

    private void deleteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you want to delete your account?");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(getApplicationContext(), ContainerActivity.class));
                finish();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void logoutDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Do you want to logout?");
        dialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logoutUser();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void logoutUser() {
        if (!isConnected(UserSettings.this)) {
            showConnectionDialog();
            progressBar.setVisibility(View.GONE);
        } else progressBar.setVisibility(View.VISIBLE);
        mAuth.signOut();
        Intent i = new Intent(UserSettings.this, ContainerActivity.class);
        startActivity(i);
        finish();
        Toast.makeText(this, "You have been logged out!", Toast.LENGTH_SHORT).show();
    }

    private boolean isConnected(UserSettings userSettings) {
        ConnectivityManager connectivityManager = (ConnectivityManager) userSettings.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected());
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserSettings.this);
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