package com.tejaswininimbalkar.krishisarathi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.User_Forgot_Page;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityResetPassUsingOldPassBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class ResetPassUsingOldPass extends AppCompatActivity {

    ActivityResetPassUsingOldPassBinding resetPassUsingOldPassBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPassUsingOldPassBinding = ActivityResetPassUsingOldPassBinding.inflate(getLayoutInflater());
        setContentView(resetPassUsingOldPassBinding.getRoot());


    }

    public void forgotOldPass(View view) {
        startActivity(new Intent(this, User_Forgot_Page.class));
        finish();
    }
}