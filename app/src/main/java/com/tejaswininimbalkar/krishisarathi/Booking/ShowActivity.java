package com.tejaswininimbalkar.krishisarathi.Booking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.tejaswininimbalkar.krishisarathi.Booking.Fragment.recFragment;
import com.tejaswininimbalkar.krishisarathi.R;

public class ShowActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_d);

        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new recFragment()).commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.ownerLayout, new OwnerRecFragment()).commit();

    }
}