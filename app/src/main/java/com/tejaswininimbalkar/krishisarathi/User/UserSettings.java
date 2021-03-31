package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tejaswininimbalkar.krishisarathi.EditProfileActivity;
import com.tejaswininimbalkar.krishisarathi.R;

/*
 * @author Tejaswini Nimbalkar
 */

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
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
        startActivity(new Intent(this, EditProfileActivity.class));
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