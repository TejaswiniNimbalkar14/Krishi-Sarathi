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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.Owner.Owner_Welcome;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.concurrent.TimeUnit;

public class Verify_Otp_page extends AppCompat {

    String phone_no, codeBySystem, p, forMustLogin;
    Intent intent;
    // Here declare global variable
    private TextView get_no;
    private PinView pinView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
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

        if (getIntent() != null) {
            phone_no = "+91" + getIntent().getStringExtra("mobile");
            get_no.setText(phone_no);

            //here get access to class name through intent to manage screen
            p = getIntent().getStringExtra("class");
            forMustLogin = getIntent().getStringExtra("mustLoginFirst");
            if (forMustLogin == null) {
                forMustLogin = "no";
            }
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
                if (!isConnected(Verify_Otp_page.this)) {
                    showConnectionDialog();
                }
                sendVerificationCodeToUser(phone_no);
            }
        });

        sendVerificationCodeToUser(phone_no);
    }

    private boolean isConnected(Verify_Otp_page verify_otp_page) {
        ConnectivityManager connectivityManager = (ConnectivityManager) verify_otp_page.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Verify_Otp_page.this);
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

    private void verifyCode(String code) {
        if (!isConnected(Verify_Otp_page.this)) {
            showConnectionDialog();
            progressBar.setVisibility(View.GONE);
        }
        try {
            progressBar.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
            signInWithPhoneAuthCredential(credential);

        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(Verify_Otp_page.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                    if (p.equals("User_SignUp")) {
                        intent = new Intent(getApplicationContext(), User_SignUp.class);
                        intent.putExtra("phone_No", phone_no);
                        intent.putExtra("mustLoginFirst", forMustLogin);
                        startActivity(intent);
                        finish();
                    } else if (p.equals("Login")) {
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();

                        if(forMustLogin.equals("mustLoginForOwner")) {
                            if (user != null) {
                                DatabaseReference root = FirebaseDatabase.getInstance().getReference("Owner");  // make a new Database Reference
                                Query query = root.orderByChild("owner_ID").equalTo(user.getUid());  // here check this login user present in owner child

                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists()) {
                                            Intent intent = new Intent(Verify_Otp_page.this, OwnerLoginActivity.class);
                                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(Verify_Otp_page.this, Owner_Welcome.class);
                                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Verify_Otp_page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            intent = new Intent(getApplicationContext(), ContainerActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(Verify_Otp_page.this, "Verification is failed! Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void btnVerify(View view) {
        if (!isConnected(Verify_Otp_page.this)) {
            showConnectionDialog();
            progressBar.setVisibility(View.GONE);
        }
        String code = pinView.getText().toString();
        if (!code.isEmpty()) {
            verifyCode(code);
        }
    }
}