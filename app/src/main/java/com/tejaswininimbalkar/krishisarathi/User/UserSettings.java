package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tejaswininimbalkar.krishisarathi.R;

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
}