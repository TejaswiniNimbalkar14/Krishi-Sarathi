package com.tejaswininimbalkar.krishisarathi.User.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.R;

public class BookingActivity extends AppCompatActivity {


    TextView name,address,phone;
    TextView order_name,owner_ID;
    Button btn;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Owner");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_d);

        Intent i = getIntent();
        String s = i.getStringExtra("key");

        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();;

        order_name = findViewById(R.id.order_name);
        order_name.setText(s);
        name = findViewById(R.id.pname);
        address = findViewById(R.id.paddress);
        phone = findViewById(R.id.pphone);
        owner_ID = findViewById(R.id.owner_userID);

        btn = findViewById(R.id.book);

        root.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ownerID = snapshot.child("userName").getValue().toString();
                owner_ID.setText(ownerID);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}