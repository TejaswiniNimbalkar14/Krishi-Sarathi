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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.R;


public class UserSignIn_page extends AppCompatActivity {

    Intent intent;
    Button btnCreateAccount, forgot_pass, login;
    TextInputLayout email, password;
    String emailId, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in_page);

        //set a light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        btnCreateAccount = findViewById(R.id.btn_create_account);
        forgot_pass = findViewById(R.id.btn_forgot_pass);
        login = findViewById(R.id.btn_login);
        email = findViewById(R.id.sign_in_username);
        password = findViewById(R.id.sign_in_password);

        //Login Button to check user
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check internet connection
                if (!isConnected(UserSignIn_page.this)) {
                    showConnectionDialog();
                }
                if (!validEmail() | !validPass()) {
                    return;
                }
                emailId = email.getEditText().getText().toString();
                pass = password.getEditText().getText().toString();

                signInAuth();


            }
        });

        findViewById(R.id.sign_in_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this page move on Dashboard
                intent = new Intent(getApplicationContext(), ContainerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this page move on Forgot page
                intent = new Intent(getApplicationContext(), User_Forgot_Page.class);
                startActivity(intent);
                finish();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this page move on Send otp page
                intent = new Intent(getApplicationContext(), Send_Otp_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //user pass data to check from database
    private void signInAuth() {
        try {
            Query checkEmail = FirebaseDatabase.getInstance().getReference("User").orderByChild("email_id").equalTo(emailId);

            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        email.setError(null);
                        email.setErrorEnabled(false);

                        emailId = emailId.substring(0, emailId.indexOf('@'));
                        String systemPass = snapshot.child(emailId).child("password").getValue(String.class);

                        if (systemPass.equals(pass)) {
                            password.setError(null);
                            password.setErrorEnabled(false);
                            Toast.makeText(UserSignIn_page.this, "Login successful", Toast.LENGTH_SHORT).show();

//                            //Get user's data from firebase database
//                            String _emailId = snapshot.child(emailId).child("email_id").getValue(String.class);
//                            String _phoneNo = snapshot.child(emailId).child("phone_num").getValue(String.class);
//                            String _gender = snapshot.child(emailId).child("gender").getValue(String.class);
//                            String _fullName = snapshot.child(emailId).child("fullName").getValue(String.class);
//                            Boolean _equiOwner = snapshot.child(emailId).child("equipment_owner").getValue(Boolean.class);
//
//                            //User_Data userData = new User_Data(_fullName, _emailId, systemPass, _phoneNo, _gender, _equiOwner);
//
//                            //Create a login session
//                            SessionManager sessionManager = new SessionManager(UserSignIn_page.this);
//                            sessionManager.createLoginSession(_fullName, _emailId, _phoneNo, _gender, systemPass, _equiOwner);
                            //sessionManager.createLoginSession(userData);

                            // when user login successful then move to ContainerActivity
                            moveToContainerActivity();
                        } else {
                            Toast.makeText(UserSignIn_page.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        }

                    } else
                        Toast.makeText(UserSignIn_page.this, "Username or Password does not match", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(UserSignIn_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(UserSignIn_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void moveToContainerActivity() {
        Intent i = new Intent(getApplicationContext(), ContainerActivity.class);
        i.putExtra("email", emailId);
        startActivity(i);
        finish();
    }

    private boolean validEmail() {
        String val = email.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validPass() {
        String val = password.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    //Method to check internet connection
    private boolean isConnected(UserSignIn_page userSignInPage) {
        ConnectivityManager connectivityManager = (ConnectivityManager) userSignInPage.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserSignIn_page.this);
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
}