package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityEditProfileBinding;

import java.util.HashMap;

/*
 * @author Tejaswini Nimbalkar
 */

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding activityEditProfileBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(activityEditProfileBinding.getRoot());

        SessionManager sessionManager = new SessionManager(this);

        if(sessionManager.checkLogin()) {
            HashMap<String, String> stringUserData = sessionManager.getStringDataFromSession();

            String name = stringUserData.get(SessionManager.KEY_FULLNAME);
            String phone = stringUserData.get(SessionManager.KEY_PHONE_NO);

            activityEditProfileBinding.editFullName.getEditText().setText(name);
            activityEditProfileBinding.editPhoneNo.getEditText().setText(phone);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getIntent().getStringExtra("cameFrom") == "userProfile"){
            Intent i = new Intent(this, ContainerActivity.class);
            i.putExtra("fromEditProfile", "FromEditProfile");
            startActivity(i);
            finish();
        }
        else if(getIntent().getStringExtra("cameFrom1") == "userSettings") {
            startActivity(new Intent(this, UserSettings.class));
            finish();
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void cancelEdit(View view) {
        onBackPressed();
    }

    public void selectPicture(View view) {
        pictureResourceDialog();
    }

    private void pictureResourceDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_picture_source, null);
        dialog.setView(dialogView);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
}