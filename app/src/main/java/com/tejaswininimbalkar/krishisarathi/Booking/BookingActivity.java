package com.tejaswininimbalkar.krishisarathi.Booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.OrderModel;
import com.tejaswininimbalkar.krishisarathi.R;

public class BookingActivity extends AppCompatActivity {


    TextView name, address, phone;
    TextView order_name;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_d);

        Intent i = getIntent();
        String s = i.getStringExtra("key");

        order_name = findViewById(R.id.order_name);
        order_name.setText(s);
        name = findViewById(R.id.pname);
        address = findViewById(R.id.paddress);
        phone = findViewById(R.id.pphone);


        btn = findViewById(R.id.book);


    }
}