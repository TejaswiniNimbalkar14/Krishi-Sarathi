package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;


//Jayesh pravin borase

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.IntroPref;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.ContainerActivity;

public class Send_Otp_Page extends AppCompatActivity {

    Button sent_otp, already_acc, skip;
    ImageView backBtn;
    TextInputLayout mobile_no;
    TextView cre_massage,log_massage;
    Intent intent;
    boolean flg=true;

    boolean isFirstTime;

   // DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IntroPref pref = new IntroPref(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__otp__page);


        mobile_no = findViewById(R.id.mobile_number);
        sent_otp = findViewById(R.id.btn_send_otp);
        //already_acc = findViewById(R.id.btn_already_account);
        skip = findViewById(R.id.skipSignUpBtn);
        backBtn = findViewById(R.id.send_otp_back_btn);

        cre_massage = findViewById(R.id.create_massage);
        //log_massage = findViewById(R.id.login_massage);

        isFirstTime = pref.isFirstTimeSendOtp();


        backBtn.setOnClickListener(new View.OnClickListener() {
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

//        already_acc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(getApplicationContext(), UserSignIn_page.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        if(isFirstTime) {
            skip.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.GONE);
            //cre_massage.setVisibility(View.VISIBLE);
            //log_massage.setVisibility(View.GONE);
            pref.setIsFirstTimeSendOtp(false);
        }else {
            skip.setVisibility(View.GONE);
            backBtn.setVisibility(View.VISIBLE);
            //cre_massage.setVisibility(View.GONE);
            //log_massage.setVisibility(View.VISIBLE);
        }

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ContainerActivity.class));
                finish();
            }
        });
    }

    //Here check user is already available or not
    private void checkUser() {
        String val = "+91"+mobile_no.getEditText().getText().toString();
        try {
            Query checkPhoneNo = FirebaseDatabase.getInstance()
                    .getReference("User").orderByChild("phone_num")
                    .equalTo(val);

            checkPhoneNo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        intent = new Intent(getApplicationContext(),Verify_Otp_page.class);
                        intent.putExtra("mobile",mobile_no.getEditText().getText().toString().replace(" ",""));
                        intent.putExtra("class","User_SignUp");
                        startActivity(intent);
                        finish();
                    }else {
                        intent = new Intent(getApplicationContext(),Verify_Otp_page.class);
                        intent.putExtra("mobile",mobile_no.getEditText().getText().toString().replace(" ",""));
                        intent.putExtra("class","Login");
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Send_Otp_Page.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            Toast.makeText(Send_Otp_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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