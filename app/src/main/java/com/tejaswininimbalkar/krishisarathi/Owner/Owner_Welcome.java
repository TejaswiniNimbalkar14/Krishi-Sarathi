package com.tejaswininimbalkar.krishisarathi.Owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.OwnerData;
import com.tejaswininimbalkar.krishisarathi.R;

public class Owner_Welcome extends AppCompatActivity {

    TextView user_name;
    Button reg_btn;
    ImageView back_btn;

    DatabaseReference reference;

    String name = null;
    String uid;


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        OwnerData data = snapshot.getValue(OwnerData.class);
                        user_name.setText(data.getFullName());
                        //name = data.getFullName();
                        //System.out.println(data.getFullName());
                        //Toast.makeText(SplashScreenActivity.this, name, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Owner_Welcome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Owner_Welcome.this, "no Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(Owner_Welcome.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        //Make Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_owner_welcome);

        user_name = findViewById(R.id.user_name);
        reg_btn = findViewById(R.id.register);
        back_btn = findViewById(R.id.back_btn);

        user_name.setText(name);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Owner_Welcome.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Owner_RegistrationActivity.class));
                finish();
            }
        });

        //Start login Activity after 2 seconds
//        new Handler (  ).postDelayed ( new Runnable ( ) {
//            @Override
//            public void run() {
//                startActivity ( new Intent ( SplashScreenActivity.this,LoginActivity.class ) );
//                finish ();
//            }
//        }, 2000 );


    }
}