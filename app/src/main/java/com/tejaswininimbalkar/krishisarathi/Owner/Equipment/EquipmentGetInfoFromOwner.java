package com.tejaswininimbalkar.krishisarathi.Owner.Equipment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_info;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.Calendar;
import java.util.HashMap;

public class EquipmentGetInfoFromOwner extends AppCompatActivity {

    NumberPicker numberPicker;
    TextView num_title;
    ImageView equipImg, back_btn;
    TextView equipName;
    int modelYear;
    TextInputLayout companyName;
    Button submit_data;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_get_info_from_owner);

        equipImg = findViewById(R.id.equipment_image);
        equipName = findViewById(R.id.equipment_Name);
        back_btn = findViewById(R.id.back_btn);
        companyName = findViewById(R.id.model_company_name);
        submit_data = findViewById(R.id.submit);

        numberPicker = findViewById(R.id.num_picker);
        num_title = findViewById(R.id.year);

        //get Image
        //equipImg.setImageResource();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        Picasso.get().load(getIntent().getStringExtra("Equip_img")).into(equipImg);
        equipName.setText(getIntent().getStringExtra("Equip_name"));

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        numberPicker.setMinValue(1960);
        numberPicker.setMaxValue(year);

        num_title.setText("Which year of model: 1960");

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                num_title.setText("Which year of model: " + Integer.toString(newVal));
                modelYear = newVal;
            }
        });

        submit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateCompany() | !validateYear()) {
                    return;
                }

                storeEquipment();

            }
        });

    }

    private void storeEquipment() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Equipment_info data = new Equipment_info(equipName.getText().toString(), companyName.getEditText().getText().toString(), Integer.toString(modelYear));

        reference.child("Owner").child(uid).child("Equipment_Details").child(data.getEquipment_name()).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //OwnerData ownerData = new OwnerData("Deva@2000");
                HashMap<String, String> map = new HashMap<>();
                map.put("owner_Name", uid);
                reference.child("Equipment").child(data.getEquipment_name()).child("Owner Name").child(uid).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(EquipmentGetInfoFromOwner.this, Equipment_Add.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
    }

    public boolean validateCompany() {
        String val = companyName.getEditText().getText().toString();

        if (val.isEmpty()) {
            companyName.setError("Field can not be empty");
            return false;
        }
        companyName.setError(null);
        return true;
    }

    public boolean validateYear() {
        String val = Integer.toString(modelYear);

        if (val.isEmpty()) {
            num_title.setError("Field can not be empty");
            return false;
        }
        return true;
    }
}