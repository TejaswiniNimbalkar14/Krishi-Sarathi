package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;


//Jayesh pravin borase


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.R;


public class UserSignIn_page extends AppCompatActivity {

    Intent intent;
    Button btnCreateAccount, forgot_pass, login;
    TextInputLayout email, password;
    String emailId,pass;

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
                if (!validEmail() | !validPass()){
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
//                intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this page move on Forgot page
//                intent = new Intent(getApplicationContext(), User_Forgot_Page.class);
//                startActivity(intent);
//                finish();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //this page move on Send otp page
                intent = new Intent(getApplicationContext(),Send_Otp_Page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //user pass data to check from database
    private void signInAuth() {
//        try {
//            Query checkEmail = FirebaseDatabase.getInstance().getReference("User").orderByChild("email_id").equalTo(emailId);
//
//            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        email.setError(null);
//                        email.setErrorEnabled(false);
//
//                        emailId = emailId.substring(0,emailId.indexOf('@'));
//                        String systemPass = snapshot.child(emailId).child("password").getValue(String.class);
//
//                        if (systemPass.equals(pass)){
//                            password.setError(null);
//                            password.setErrorEnabled(false);
//                            intent = new Intent(getApplicationContext(),MainActivity.class);
//                            startActivity(intent);
//                        }else {
//                            Toast.makeText(UserSignIn_page.this, "Password does not match", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }else
//                        Toast.makeText(UserSignIn_page.this, "Username or Password does not match", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(UserSignIn_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }catch (Exception e){
//            Toast.makeText(UserSignIn_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean validEmail(){
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

    private boolean validPass(){
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
}