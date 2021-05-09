package com.tejaswininimbalkar.krishisarathi.User.Booking;
/*
 * @author Devendra Kharatmal
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.YourOrders.PendingFragment;

import java.util.HashMap;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {
    Button book_Equip;
    TextView User_Name,User_Phone;
    EditText return_Date,working_Date,working_Time;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String Booking_Id,Equipment_name,Requester_Id,Working_Date,Working_Time,owner_Id,Return_Date;

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    PendingFragment pendingFragment = new PendingFragment();

    Bundle bundle = new Bundle();

    int i= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Requester_Id = auth.getUid();
        Intent intent = getIntent();
        owner_Id = intent.getStringExtra("key");
        Equipment_name = intent.getStringExtra("name");

        book_Equip = (Button)findViewById(R.id.book_Equipment);
        User_Name = (TextView)findViewById(R.id.U_Name);
        User_Phone = (TextView)findViewById(R.id.U_Phone);
        return_Date = (EditText)findViewById(R.id.return_Date);
        working_Date = (EditText)findViewById(R.id.working_date);
        working_Time = (EditText)findViewById(R.id.working_time);



        Booking_Id = "KRISHI0000"+ new Random().nextInt(4000);



        ref.child("User").child(Requester_Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name= snapshot.child("fullName").getValue().toString();
                String phone = snapshot.child("phone_num").getValue().toString();

                User_Name.setText(name);
                User_Phone.setText(phone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



        book_Equip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String order_no = "Order No "+ i++;

                i=i++;

                Working_Date = working_Date.getText().toString();
                Working_Time = working_Time.getText().toString();
                Return_Date = return_Date.getText().toString();


                HashMap<String, String> orderMap = new HashMap<>();

                orderMap.put("Requester_Id",Requester_Id);
                orderMap.put("Equipment_name",Equipment_name);
                orderMap.put("Booking_Id",Booking_Id);
                orderMap.put("Working_Date",Working_Date);
                orderMap.put("Working_Time",Working_Time);
                orderMap.put("Return_Date",Return_Date);



                ref.child("Owner").child(owner_Id).child("Booking_Request").child(Booking_Id)
                        .setValue(orderMap);


                HashMap<String, String> pendingMap = new HashMap<>();

                pendingMap.put("Owner_ID",owner_Id);
                pendingMap.put("Equipment_name",Equipment_name);
                pendingMap.put("Booking_Id",Booking_Id);
                pendingMap.put("Working_Date",Working_Date);
                pendingMap.put("Working_Time",Working_Time);
                pendingMap.put("Return_Date",Return_Date);




                ref.child("User").child(Requester_Id).child("Pending Request").child(Booking_Id)
                        .setValue(pendingMap);


                Toast.makeText(getApplicationContext(),"You Requested for Equipment",Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser mFirebaseUser = auth.getCurrentUser();
        if(mFirebaseUser!= null){
            //User is Login
        }else {
            startActivity(new Intent(PaymentActivity.this, Send_Otp_Page.class));
            finish();
        }
    }


}