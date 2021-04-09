package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.ResetPassUsingCurrentPass;

/*
 * @author Tejaswini Nimbalkar
 */

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void changeLanguage(View view) {
        Intent i = new Intent(this, SelectLanguage.class);
        i.putExtra("changeBtns", "Change Buttons");
        startActivity(i);
        finish();
    }

    public void editProfile(View view) {
        Intent i = new Intent(this, EditProfileActivity.class);
        i.putExtra("cameFrom1", "userSettings");
        startActivity(i);
        finish();
    }

    public void resetPassword(View view) {
        startActivity(new Intent(this, ResetPassUsingCurrentPass.class));
        finish();
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
}