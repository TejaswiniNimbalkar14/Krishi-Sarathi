package com.tejaswininimbalkar.krishisarathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.User_Forgot_Page;
import com.tejaswininimbalkar.krishisarathi.User.UserSettings;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityResetPassUsingCurrentPassBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class ResetPassUsingCurrentPass extends AppCompatActivity {

    ActivityResetPassUsingCurrentPassBinding resetPassUsingCurrentPassBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPassUsingCurrentPassBinding = ActivityResetPassUsingCurrentPassBinding.inflate(getLayoutInflater());
        setContentView(resetPassUsingCurrentPassBinding.getRoot());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, UserSettings.class));
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void forgotCurrentPass(View view) {
        startActivity(new Intent(this, User_Forgot_Page.class));
        finish();
    }
}