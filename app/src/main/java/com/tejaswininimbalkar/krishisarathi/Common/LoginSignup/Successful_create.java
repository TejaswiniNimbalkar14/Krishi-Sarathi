//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;

import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.UserSettings;


public class Successful_create extends AppCompat {

    TextView textUpdate, massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_create);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        textUpdate = findViewById(R.id.textUpdate);
        massage = findViewById(R.id.message);

        textUpdate.setText(getIntent().getStringExtra("textUpdate"));
        massage.setText(getIntent().getStringExtra("massage"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (textUpdate.getText().toString().equals("Account Create")) {
                    Intent intent = new Intent(getApplicationContext(), ContainerActivity.class);
                    startActivity(intent);
                    finish();
                } else if (textUpdate.getText().toString().equals("Password\nChanged")) {
                    Intent intent = new Intent(getApplicationContext(), UserSettings.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), UserSignIn_page.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);
    }
}