package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;


//Jayesh pravin borase

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//import com.swami.login.R;
import com.tejaswininimbalkar.krishisarathi.R;

public class Send_Otp_Page extends AppCompatActivity {

    Button sent_otp,already_acc;
    TextInputLayout mobile_no;
    Intent intent;
    boolean flg=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__otp__page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mobile_no = findViewById(R.id.mobile_number);

        sent_otp = findViewById(R.id.btn_send_otp);
        already_acc = findViewById(R.id.btn_already_account);

        findViewById(R.id.send_otp_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), UserSignIn_page.class);
                startActivity(intent);
                finish();
            }
        });

        sent_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validNumber()){
                    return;
                }

                checkUser();
            }
        });

        already_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(),UserSignIn_page.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //Here check user is already available or not
    private void checkUser() {
        String val = "+91"+mobile_no.getEditText().getText().toString();
//        try {
//            Query checkPhoneNo = FirebaseDatabase.getInstance()
//                    .getReference("User").orderByChild("phone_num")
//                    .equalTo(val);
//
//            checkPhoneNo.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (!snapshot.exists()){
//                        intent = new Intent(getApplicationContext(),Verify_Otp_page.class);
//                        intent.putExtra("mobile",mobile_no.getEditText().getText().toString().replace(" ",""));
//                        intent.putExtra("class","User_SignUp");
//                        startActivity(intent);
//                        finish();
//                    }else
//                        Toast.makeText(Send_Otp_Page.this, "Already have account", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(Send_Otp_Page.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                }
//            });
//        }catch (Exception e){
//            Toast.makeText(Send_Otp_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }

    private boolean validNumber(){
        String val = mobile_no.getEditText().getText().toString();
        if (val.isEmpty()) {
            mobile_no.setError("Field can not be empty");
            return false;
        } else if (val.length() != 10){
            mobile_no.setError("Enter Valid Number");
            return false;
        }else {
            mobile_no.setError(null);
            mobile_no.setErrorEnabled(false);
            return true;
        }
    }
}