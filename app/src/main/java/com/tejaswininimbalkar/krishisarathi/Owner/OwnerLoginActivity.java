package com.tejaswininimbalkar.krishisarathi.Owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.R;

public class OwnerLoginActivity extends AppCompatActivity {


    Intent intent;
    Button  forgot_pass, btn_login;
    TextInputLayout owner_id, password;
    String ownerId, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_owner_login );

        forgot_pass = findViewById(R.id.btn_forgot_pass);
        btn_login = findViewById(R.id.btn_login);
        owner_id = findViewById(R.id.owner_id);
        password = findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validOwner() | !validPass()) {
                    return;
                }

                signInAuth();
            }
        });


    }

    private void signInAuth() {
        Query checkOwner = FirebaseDatabase.getInstance().getReference("Owner").orderByChild("userName").equalTo(ownerId);

        checkOwner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    owner_id.setError(null);
                    owner_id.setErrorEnabled(false);

                    String systemPass = snapshot.child(ownerId).child("password").getValue(String.class);

                    if (systemPass.equals(pass)) {
                        password.setError(null);
                        password.setErrorEnabled(false);
                        Toast.makeText(OwnerLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        //moveToContainerActivity();
                    } else {
                        Toast.makeText(OwnerLoginActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    }

                } else
                    Toast.makeText(OwnerLoginActivity.this, "Username or Password does not match", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OwnerLoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validOwner() {
        String val = owner_id.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            owner_id.setError("Field can not be empty");
            return false;
        } else {
            owner_id.setError(null);
            owner_id.setErrorEnabled(false);
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
}