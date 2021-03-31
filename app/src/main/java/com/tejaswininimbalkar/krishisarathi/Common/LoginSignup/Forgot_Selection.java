
//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tejaswininimbalkar.krishisarathi.R;

public class Forgot_Selection extends AppCompatActivity {

    Button phone_auth,email_auth;
    Intent intent;
    String phoneNo,emailId;
   TextView p,e;
    //User_Data userData = new User_Data();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_selection);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        phone_auth = findViewById(R.id.forgot_via_sms);
        email_auth = findViewById(R.id.forgot_via_mail);
        p = findViewById(R.id.phone);
        e = findViewById(R.id.emailId);

        phoneNo = getIntent().getStringExtra("phone");
        emailId = getIntent().getStringExtra("email");

        //Toast.makeText(this, emailId.substring(emailId.indexOf('@')-2), Toast.LENGTH_SHORT).show();



        try {
            //+917709608138
           p.setText("+91"+"xxxxxx"+phoneNo.substring(9));
           e.setText(emailId.substring(0,2)+"xxxx"+emailId.substring(emailId.indexOf('@')-2));
            //textEmail.setText(emailId.substring(0,2)+"xxxx"+emailId.substring(emailId.indexOf('@')-2));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(this, userData.getEmail_id(), Toast.LENGTH_SHORT).show();

        phone_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),Verify_Otp_page.class);
                intent.putExtra("mobile",phoneNo.substring(3));
                intent.putExtra("uid",emailId.substring(0,emailId.indexOf('@')));
                intent.putExtra("class","Reset_Password");
                startActivity(intent);
                finish();
            }
        });
    }
}