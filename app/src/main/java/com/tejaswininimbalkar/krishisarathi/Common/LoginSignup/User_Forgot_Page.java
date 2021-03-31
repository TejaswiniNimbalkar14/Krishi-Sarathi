
//Jayesh pravin borase

package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tejaswininimbalkar.krishisarathi.R;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;


public class User_Forgot_Page extends AppCompatActivity {

    Button next;
    TextInputLayout email;
    String emailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__forgot__page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        next = findViewById(R.id.forgot_next_page);
        email = findViewById(R.id.check_email_id);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validEmail()){
                    return;
                }

                checkDatabase();

            }
        });
    }

    private void checkDatabase() {
        emailId = email.getEditText().getText().toString().trim();

//        try {
//            Query checkEmail = FirebaseDatabase.getInstance().getReference("User").orderByChild("email_id").equalTo(emailId);
//
//            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        email.setError(null);
//                        email.setErrorEnabled(false);

//                        String uid = emailId.substring(0,emailId.indexOf('@'));
//                        String phoneNo = snapshot.child(uid).child("phone_num").getValue(String.class);
//
//                        Intent intent = new Intent(getApplicationContext(), Forgot_Selection.class);
//                        intent.putExtra("phone",phoneNo);
//                        intent.putExtra("email",emailId);
//                        startActivity(intent);
//                        finish();

//                    }else
//                        Toast.makeText(User_Forgot_Page.this, "User Not Exist!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
}