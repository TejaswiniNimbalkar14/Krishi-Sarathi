//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.Common.GetAddressFormLocation;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.Owner.Owner_Welcome;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.UserSettings;

public class Successful_create extends AppCompat {

    TextView textUpdate, massage;
    String forMustLogin = "no";
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_create);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        textUpdate = findViewById(R.id.textUpdate);
        massage = findViewById(R.id.message);

        textUpdate.setText(getIntent().getStringExtra("textUpdate"));
        massage.setText(getIntent().getStringExtra("massage"));

        forMustLogin = getIntent().getStringExtra("mustLoginFirst");
        if (forMustLogin == null) {
            forMustLogin = "no";
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If user is signing up for first time for being equipment owner
                if (forMustLogin.equals("mustLoginForOwner") && textUpdate.getText().toString().equals("Account Create")) {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Owner");  // make a new Database Reference
                        Query query = root.orderByChild("owner_ID").equalTo(user.getUid());  // here check this login user present in owner child

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Intent intent = new Intent(getApplicationContext(), OwnerLoginActivity.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), Owner_Welcome.class);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else if (textUpdate.getText().toString().equals("Account Create")) {
                    Intent intent = new Intent(getApplicationContext(), GetAddressFormLocation.class);
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