//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.R;

public class Reset_Password extends AppCompatActivity {

    Button reset;
    ImageView back_btn;
    TextInputLayout newPass, conPass;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        newPass = findViewById(R.id.rest_new_password);
        conPass = findViewById(R.id.rest_con_password);
        back_btn = findViewById(R.id.reset_back_btn);

        reset = findViewById(R.id.reset_next_page);
        uid = getIntent().getStringExtra("uid");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_Forgot_Page.class));
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePassword() | !validateConPassword()) {
                    return;
                }
                updateData();

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), User_Forgot_Page.class));
        finish();
    }

    private void updateData() {
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
            reference.child(uid).child("password").setValue(newPass.getEditText().getText().toString());
            Intent intent = new Intent(getApplicationContext(), Successful_create.class);
            intent.putExtra("textUpdate", "Password\nUpdated");
            intent.putExtra("massage", "Your password has been updated");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validatePassword() {
        String val = newPass.getEditText().getText().toString().trim();
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            newPass.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            newPass.setError("Password should contain 6 characters!");
            return false;
        } else {
            newPass.setError(null);
            newPass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConPassword() {
        if (!newPass.getEditText().getText().toString().equals(conPass.getEditText().getText().toString())) {
            conPass.setError("Can't match password");
            return false;
        } else {
            conPass.setError(null);
            conPass.setErrorEnabled(false);
            return true;
        }
    }
}