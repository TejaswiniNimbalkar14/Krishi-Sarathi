package com.tejaswininimbalkar.krishisarathi.Owner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.User_SignUp;
import com.tejaswininimbalkar.krishisarathi.Owner.Equipment.Equipment_Add;
import com.tejaswininimbalkar.krishisarathi.Owner.Model.OwnerData;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.Calendar;
import java.util.HashMap;

public class Owner_RegistrationActivity extends AppCompatActivity {

    ImageView back_btn;
    TextInputLayout user_name, password;
    RadioButton trac_yes, equi_yes;
    CardView trac_increase, trac_decrease, equi_increase, equi_decrease;
    TextView tractor_count, equipment_count;
    RelativeLayout trac_info, equi_info;
    Button next;
    RadioGroup trac_r, equi_r;
    DatePicker dob;
    ProgressBar progressBar;

    DatabaseReference reference;

    int t_no = 0, e_no = 0;
    String date, uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_registration);

        back_btn = findViewById(R.id.back_btn);
        user_name = findViewById(R.id.username);
        dob = findViewById(R.id.dob);
        trac_yes = findViewById(R.id.trac_yes);
        equi_yes = findViewById(R.id.equi_yes);
        trac_r = findViewById(R.id.trac_radio);
        equi_r = findViewById(R.id.equi_radio);
        next = findViewById(R.id.next);
        trac_increase = findViewById(R.id.trac_increase);
        trac_decrease = findViewById(R.id.trac_decrease);
        equi_increase = findViewById(R.id.equi_increase);
        equi_decrease = findViewById(R.id.equi_decrease);
        tractor_count = findViewById(R.id.no_of_trac);
        equipment_count = findViewById(R.id.no_of_equi);
        trac_info = findViewById(R.id.trac_info);
        equi_info = findViewById(R.id.equi_info);
        next = findViewById(R.id.next);
        password = findViewById(R.id.password);
        progressBar.findViewById(R.id.ownerRegisterProgress);

        reference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        tractorInfo();
        equipmentInfo();

//        boolean flg = validateTractor();
//        boolean flg1 = validateEquipment();

        trac_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trac_info.setVisibility(View.VISIBLE);
                trac_yes.setError(null);
            }
        });

        equi_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equi_info.setVisibility(View.VISIBLE);
                equi_yes.setError(null);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(Owner_RegistrationActivity.this)) {
                    showConnectionDialog();
                    progressBar.setVisibility(View.GONE);
                }
                if (!validateUserName() | !validateTractor() | !validateEquipment() | !validateDateOfBirth() | !validatePassword()) {
                    return;
                }

                int month = dob.getMonth() + 1;

                date = dob.getDayOfMonth() + "/" + Integer.toString(month) + "/" + dob.getYear();

                //OwnerData data = new OwnerData(true);
                reference.child("User").child(uid).child("equipment_owner").setValue(true);
                createOwner();

            }
        });
    }

    private void equipmentInfo() {
        equi_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_no = e_no + 1;
                equipment_count.setText(Integer.toString(e_no));
            }
        });

        equi_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e_no != 1) {
                    e_no = e_no - 1;
                    equipment_count.setText(Integer.toString(e_no));
                }
            }
        });
    }

    private void tractorInfo() {
        trac_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t_no = t_no + 1;
                tractor_count.setText(Integer.toString(t_no));
            }
        });

        trac_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t_no != 1) {
                    t_no = t_no - 1;
                    tractor_count.setText(Integer.toString(t_no));
                }
            }
        });
    }

    private void createOwner() {
        //OwnerData data1 = ;
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();

        root.child("Owner").child(uid).setValue(new OwnerData(
                true,
                user_name.getEditText().getText().toString(),
                date,
                tractor_count.getText().toString().trim(),
                equipment_count.getText().toString().trim(),
                password.getEditText().getText().toString().trim(),
                /*here add user uid*/ uid)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("owner_Id", user_name.getEditText().getText().toString());
                root.child("User").child(uid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Owner_RegistrationActivity.this, "To Equipment Add", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Owner_RegistrationActivity.this, Equipment_Add.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Owner_RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });
    }

    private boolean validateUserName() {
        String val = user_name.getEditText().getText().toString().trim();
        String checkEmail = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            user_name.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail) && !val.equals("Krishi@120")) {
            user_name.setError("Invalid User! [eg. Krishi@120 ]");
            return false;
        } else {
            user_name.setError(null);
            user_name.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 6) {
            password.setError("At least 6 Number");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean validateDateOfBirth() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = dob.getYear();

        if ((currentYear - userAge) < 18) {
            Toast.makeText(this, (currentYear - userAge) + " You are not Elligible", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }

    private boolean validateTractor() {
        if (trac_yes.isChecked()) {
            return true;
        }
        trac_yes.setError("Please Check");
        return false;
    }

    private boolean validateEquipment() {
        if (equi_yes.isChecked()) {
            return true;
        }
        equi_yes.setError("Please Check");
        return false;
    }

    private boolean isConnected(Owner_RegistrationActivity ownerRegistrationActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ownerRegistrationActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Owner_RegistrationActivity.this);
        alertDialog.setMessage("Please connect to the internet to move further!");
        alertDialog.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }
}