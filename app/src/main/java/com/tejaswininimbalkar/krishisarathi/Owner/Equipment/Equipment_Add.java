package com.tejaswininimbalkar.krishisarathi.Owner.Equipment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Owner.Adapter.Equip_add_adapter;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.Equipment_add_model;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.ArrayList;

public class Equipment_Add extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Equipment_add_model> list = new ArrayList<>();
    Equip_add_adapter adapter;
    Query checkEquip;
    boolean flg = true;
    String uid, owner_id;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_add);

        recyclerView = findViewById(R.id.recycler);

        adapter = new Equip_add_adapter(getApplicationContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        reference = FirebaseDatabase.getInstance().getReference("Equipment");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        //owner_id = FirebaseDatabase.getInstance().getReference("Owner").child(uid).child("username");
        DatabaseReference name = FirebaseDatabase.getInstance().getReference("Owner").child(uid);

        name.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                owner_id = snapshot.child("userName").getValue(String.class);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Equipment_add_model model = data.getValue(Equipment_add_model.class);
                            //Toast.makeText(Equipment_Add.this, model.getEquipment_name(), Toast.LENGTH_SHORT).show();
                            try {
                                if (!data.child("Owner Name").child(uid).child("owner_Name").getValue(String.class).equalsIgnoreCase(owner_id)) {
                                }

                            } catch (Exception e) {
                                list.add(model);
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Equipment_Add.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Equipment_Add.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}