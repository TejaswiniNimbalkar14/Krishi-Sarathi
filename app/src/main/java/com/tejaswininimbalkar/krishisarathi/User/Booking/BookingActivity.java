package com.tejaswininimbalkar.krishisarathi.User.Booking;
/*
 * @author Devendra Kharatmal
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


    EditText  user_Name,user_Phone,user_Address,user_City,user_CityCode;
    String u_name,u_address,u_phone,u_City,u_CityCode;
    TextView e_Company,e_modelYear,e_Name;
    TextView order_name,owner_ID;
    Button goto_payment;


    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Owner");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_d);

        Intent i = getIntent();
        String s = i.getStringExtra("key");
        String name = i.getStringExtra("name");

        owner_ID = (TextView)findViewById(R.id.owner_ID);
        e_Company = (TextView)findViewById(R.id.e_Company);
        e_Name = (TextView)findViewById(R.id. e_Name);
        e_modelYear = (TextView)findViewById(R.id.e_modelYear);

        user_Name = (EditText)findViewById(R.id.user_Name);
        user_Phone= (EditText)findViewById(R.id.user_Phone);
        user_Address = (EditText)findViewById(R.id.user_Address);
        user_City = (EditText)findViewById(R.id.user_City);
        user_CityCode = (EditText)findViewById(R.id.user_CityCode);


        Toast.makeText(getApplicationContext(),""+ name , Toast.LENGTH_LONG).show();




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

        root.child(s).child("Equipment_Details").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String equip_Company= snapshot.child("equipment_company_name").getValue().toString();
                String equip_name= snapshot.child("equipment_name").getValue().toString();
                String equip_model_year= snapshot.child("model_year").getValue().toString();

                e_Company.setText(equip_Company);
                e_Name.setText(equip_name);
                e_modelYear.setText(equip_model_year);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        goto_payment = findViewById(R.id.goto_Payment);

        goto_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_name = user_Name.getText().toString();
                u_phone = user_Phone.getText().toString();
                u_address = user_Address.getText().toString();
                u_City = user_City.getText().toString();
                u_CityCode = user_CityCode.getText().toString();

                Toast.makeText(getApplicationContext(),""+ u_name, Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), PaymentActivity.class));
            }
        });



    }
}