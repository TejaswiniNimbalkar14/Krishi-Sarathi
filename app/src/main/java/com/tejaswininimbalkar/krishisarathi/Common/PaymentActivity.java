package com.tejaswininimbalkar.krishisarathi.Common;
/*
 * @author Devendra B Kharatmal
 */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tejaswininimbalkar.krishisarathi.R;

public class PaymentActivity extends AppCompatActivity {
    Button book_Equip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        book_Equip = (Button)findViewById(R.id.book_Equipment);

        book_Equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Equipment is Booked",Toast.LENGTH_LONG).show();
            }
        });







    }
}