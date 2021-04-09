package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tejaswininimbalkar.krishisarathi.R;

/*
 * @author Tejaswini Nimbalkar
 */

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
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