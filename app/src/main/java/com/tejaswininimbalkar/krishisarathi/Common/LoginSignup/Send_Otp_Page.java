package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;


//Jayesh pravin borase

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences.IntroPref;
import com.tejaswininimbalkar.krishisarathi.R;

public class Send_Otp_Page extends AppCompat {

    Intent intent;
    boolean isFirstTime;
    private Button sent_otp, skip, backBtn;
    private TextInputLayout mobile_no;
    private ProgressBar progressBar;
    String extraForMustLogin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntroPref pref = new IntroPref(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__otp__page);

        mobile_no = findViewById(R.id.mobile_number);
        sent_otp = findViewById(R.id.btn_send_otp);
        skip = findViewById(R.id.skipSignUpBtn);
        backBtn = findViewById(R.id.send_otp_back_btn);
        progressBar = findViewById(R.id.otpProgressBar);

        isFirstTime = pref.isFirstTimeSendOtp();

        if (getIntent() != null) {
            extraForMustLogin = getIntent().getStringExtra("mustLoginFirst");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ContainerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sent_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(Send_Otp_Page.this)) {
                    showConnectionDialog();
                    progressBar.setVisibility(View.GONE);
                }
                if (!validNumber()) {
                    return;
                }
                checkUser();
            }
        });

        if (isFirstTime) {
            skip.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.GONE);
            pref.setIsFirstTimeSendOtp(false);
        } else {
            skip.setVisibility(View.GONE);
            backBtn.setVisibility(View.VISIBLE);
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContainerActivity.class));
                finish();
            }
        });
    }

    private boolean isConnected(Send_Otp_Page send_otp_page) {
        ConnectivityManager connectivityManager = (ConnectivityManager) send_otp_page.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Send_Otp_Page.this);
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

    //Here check user if is already registered or not
    private void checkUser() {
        if (!isConnected(Send_Otp_Page.this)) {
            progressBar.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.VISIBLE);
        String val = "+91" + mobile_no.getEditText().getText().toString();
        try {
            Query checkPhoneNo = FirebaseDatabase.getInstance()
                    .getReference("User").orderByChild("phone_num")
                    .equalTo(val);

            checkPhoneNo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    progressBar.setVisibility(View.GONE);
                    if (!snapshot.exists()) {
                        intent = new Intent(getApplicationContext(), Verify_Otp_page.class);
                        intent.putExtra("mobile", mobile_no.getEditText().getText().toString().replace(" ", ""));
                        intent.putExtra("class", "User_SignUp");
                        intent.putExtra("mustLoginFirst", extraForMustLogin);
                        startActivity(intent);
                        finish();
                    } else {
                        intent = new Intent(getApplicationContext(), Verify_Otp_page.class);
                        intent.putExtra("mobile", mobile_no.getEditText().getText().toString().replace(" ", ""));
                        intent.putExtra("class", "Login");
                        intent.putExtra("mustLoginFirst", extraForMustLogin);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Send_Otp_Page.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            Toast.makeText(Send_Otp_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validNumber() {
        String val = mobile_no.getEditText().getText().toString();
        if (val.isEmpty()) {
            mobile_no.setError("Field can not be empty");
            return false;
        } else if (val.length() != 10) {
            mobile_no.setError("Enter Valid Number");
            return false;
        } else {
            mobile_no.setError(null);
            mobile_no.setErrorEnabled(false);
            return true;
        }
    }
}