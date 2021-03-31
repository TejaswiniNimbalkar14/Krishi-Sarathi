
//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Model.User_Data;
import com.tejaswininimbalkar.krishisarathi.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;



public class User_SignUp extends AppCompatActivity {

    TextInputLayout fullName,emailId,password,conPassword;
    RadioButton rMale,rFemale;
    Button submitToLogin;
    ImageView backBtn;
    String phoneNo,gender;
    User_Data userData;
    String email;
    Intent intent;
    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        backBtn = findViewById(R.id.back_btn);
        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        conPassword = findViewById(R.id.con_password);
        radioGroup = findViewById(R.id.radioGroup);
        rMale = findViewById(R.id.r_male);
        rFemale = findViewById(R.id.r_female);
        submitToLogin = findViewById(R.id.btn_sign_up);

        phoneNo = getIntent().getStringExtra("phone_No");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Send_Otp_Page.class);
                startActivity(intent);
                finish();
            }
        });

        submitToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateFullName() | !validateEmail() | !validatePassword() | !validateConPassword() | !validateGender()) {
                    return;
                }
                email = emailId.getEditText().getText().toString();
                storeNewData();
            }
        });

    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailId.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            emailId.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailId.setError("Invalid Email!");
            return false;
        } else {
            emailId.setError(null);
            emailId.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
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
            password.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkPassword)) {
            password.setError("Password should contain 6 characters!");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConPassword() {
        if (!password.getEditText().getText().toString().equals(conPassword.getEditText().getText().toString())) {
            conPassword.setError("Can't match password");
            return false;
        } else {
            conPassword.setError(null);
            conPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (rMale.isChecked()){
                gender = "Male";
            }else if (rFemale.isChecked()){
                gender = "Female";
            }
            return true;
        }
    }

    //here store data on firebase database
    private void storeNewData() {

//        try {
//            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
//            DatabaseReference reference = rootNode.getReference();
//
//            email = email.substring(0,email.indexOf('@'));
//
//            Query checkEmail = FirebaseDatabase.getInstance()
//                    .getReference("User").orderByChild("email_id")
//                    .equalTo(emailId.getEditText().getText().toString());
//
//            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (!snapshot.exists()){
//                        userData = new User_Data(
//                                fullName.getEditText().getText().toString(),
//                                emailId.getEditText().getText().toString(),
//                                password.getEditText().getText().toString(),
//                                phoneNo,
//                                gender,
//                                false
//                        );
//
//
//                        reference.child("User").child(email).setValue(userData);
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                intent = new Intent(getApplicationContext(), Successful_create.class);
//                                intent.putExtra("textUpdate","Account Create");
//                                intent.putExtra("massage", "Your account has been created");
//                                startActivity(intent);
//                                finish();
//                            }
//                        },2000);
//                    }else
//                        Toast.makeText(User_SignUp.this, "Already have this Email Id", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(User_SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }

    }
}