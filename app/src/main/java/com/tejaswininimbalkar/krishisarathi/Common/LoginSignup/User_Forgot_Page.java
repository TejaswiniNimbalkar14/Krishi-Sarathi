//Jayesh pravin borase

package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


public class User_Forgot_Page extends AppCompatActivity {

    Button next;
    TextInputLayout email;
    ImageView back_btn;
    String emailId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__forgot__page);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        next = findViewById(R.id.forgot_next_page);
        email = findViewById(R.id.check_email_id);
        back_btn = findViewById(R.id.forgot_back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserSignIn_page.class));
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validEmail()) {
                    return;
                }

                checkDatabase();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), UserSignIn_page.class));
        finish();
    }

    private void checkDatabase() {
        emailId = email.getEditText().getText().toString().trim();

        try {
            Query checkEmail = FirebaseDatabase.getInstance().getReference("User").orderByChild("email_id").equalTo(emailId);

            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        email.setError(null);
                        email.setErrorEnabled(false);

                        String uid = emailId.substring(0, emailId.indexOf('@'));
                        String phoneNo = snapshot.child(uid).child("phone_num").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), Forgot_Selection.class);
                        intent.putExtra("phone", phoneNo);
                        intent.putExtra("email", emailId);
                        startActivity(intent);
                        finish();

                    } else
                        Toast.makeText(User_Forgot_Page.this, "User Not Exist!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validEmail() {
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