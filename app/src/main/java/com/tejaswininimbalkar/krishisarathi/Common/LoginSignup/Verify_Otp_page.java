package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

//Jayesh pravin borase

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.tejaswininimbalkar.krishisarathi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tejaswininimbalkar.krishisarathi.User.ContainerActivity;


import java.util.concurrent.TimeUnit;

public class Verify_Otp_page extends AppCompatActivity {

    String phone_no, codeBySystem, p;
    // Here declare global variable
    private TextView get_no;
    private PinView pinView;
    private ProgressBar progressBar;

    Intent intent;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify__otp_page);

        //The remove night mode and system default mode By this method
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        //Declare xml id in global variable
        get_no = findViewById(R.id.get_no);
        pinView = findViewById(R.id.otp_code);
        progressBar = findViewById(R.id.verifyOtpProgress);

        mAuth = FirebaseAuth.getInstance();

        if (getIntent() != null){
            phone_no = "+91" + getIntent().getStringExtra("mobile");
            get_no.setText(phone_no);

            //here get access to class name through intent to manage screen
            p = getIntent().getStringExtra("class");
        }

        findViewById(R.id.clear_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Send_Otp_Page.class);
                startActivity(intent);
                finish();
            }
        });

        //To perform resend otp action
        findViewById(R.id.resend_otp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodeToUser(phone_no);
            }
        });

        sendVerificationCodeToUser(phone_no);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinView.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(Verify_Otp_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    codeBySystem = s;
                }
            };

    private void verifyCode(String code) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
            signInWithPhoneAuthCredential(credential);

        } catch (Exception e) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Verify_Otp_page.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                    if (p.equals("User_SignUp")){
                        intent = new Intent(getApplicationContext(), User_SignUp.class);
                        intent.putExtra("phone_No", phone_no);
                        startActivity(intent);
                        finish();
                    }
                    else if (p.equals("Login")){
                        intent = new Intent(getApplicationContext(), ContainerActivity.class);
                        startActivity(intent);
                        finish();}
                }
                else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(Verify_Otp_page.this, "Verification is failed! Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void btnVerify(View view) {
        String code = pinView.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}