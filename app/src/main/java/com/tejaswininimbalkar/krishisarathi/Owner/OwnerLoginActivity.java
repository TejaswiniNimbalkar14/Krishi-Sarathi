package com.tejaswininimbalkar.krishisarathi.Owner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences.IntroPref;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.OwnerContainer;
import com.tejaswininimbalkar.krishisarathi.R;

public class OwnerLoginActivity extends AppCompatActivity {


    Intent intent;
    Button forgot_pass, btn_login;
    TextInputLayout owner_id, owner_pass;
    ProgressBar progressBar;
    ImageView backBtn;
    String ownerId, pass, uid;
    private IntroPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = new IntroPref(this);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        forgot_pass = findViewById(R.id.btn_forgot_pass);
        btn_login = findViewById(R.id.btn_login);
        owner_id = findViewById(R.id.owner_id);
        owner_pass = findViewById(R.id.password);
        progressBar = findViewById(R.id.ownerLoginProgress);
        backBtn = findViewById(R.id.back_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(OwnerLoginActivity.this)) {
                    showConnectionDialog();
                    progressBar.setVisibility(View.GONE);
                }
                if (!validOwner() | !validPass()) {
                    return;
                }
                userOwnerChoiceDialog();
                //signInAuth();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void signInAuth() {
        progressBar.setVisibility(View.VISIBLE);
        Query checkOwner = FirebaseDatabase.getInstance().getReference("Owner").orderByChild("userName").equalTo(ownerId);

        checkOwner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    owner_id.setError(null);
                    owner_id.setErrorEnabled(false);

                    String systemPass = snapshot.child(uid).child("password").getValue(String.class);

                    if (systemPass.equals(pass)) {
                        owner_pass.setError(null);
                        owner_pass.setErrorEnabled(false);
                        Toast.makeText(OwnerLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        //moveToContainerActivity();
                        Intent intent = new Intent(OwnerLoginActivity.this, OwnerContainer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OwnerLoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }

                } else
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OwnerLoginActivity.this, "Username or Password does not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OwnerLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validOwner() {
        ownerId = owner_id.getEditText().getText().toString().trim();
        if (ownerId.isEmpty()) {
            owner_id.setError("Field can not be empty");
            return false;
        } else {
            owner_id.setError(null);
            owner_id.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validPass() {
        try {
            pass = owner_pass.getEditText().getText().toString();
            if (pass.isEmpty()) {
                owner_pass.setError("Field can not be empty");
                return false;
            } else {
                owner_pass.setError(null);
                owner_pass.setErrorEnabled(false);
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean isConnected(OwnerLoginActivity ownerLoginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ownerLoginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OwnerLoginActivity.this);
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

    private void userOwnerChoiceDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OwnerLoginActivity.this);
        alertDialog.setMessage("Do you want to stay logged in as Owner?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pref.setIsOwner(true);
                signInAuth();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pref.setIsOwner(false);
                signInAuth();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}