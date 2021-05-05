//Jayesh pravin borase
package com.tejaswininimbalkar.krishisarathi.Common.LoginSignup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Model.User_Data;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.UUID;

public class User_SignUp extends AppCompat {

    TextInputLayout fullName, emailId;
    RadioButton rMale, rFemale;
    Button submitToLogin, backBtn;
    ImageView profileImage;
    String phoneNo, gender, imageUrl;
    String forMustLogin = "";
    User_Data userData;
    Intent intent;
    RadioGroup radioGroup;
    Uri galleryImageUri;
    FirebaseAuth mAuth;
    private ProgressBar progressBar, imageProgressBar;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__sign_up);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        backBtn = findViewById(R.id.back_btn);
        fullName = findViewById(R.id.fullName);
        emailId = findViewById(R.id.email_id);
        radioGroup = findViewById(R.id.radioGroup);
        rMale = findViewById(R.id.r_male);
        rFemale = findViewById(R.id.r_female);
        submitToLogin = findViewById(R.id.btn_sign_up);
        profileImage = findViewById(R.id.signUpProfileImage);
        progressBar = findViewById(R.id.signUpProgressBar);
        imageProgressBar = findViewById(R.id.signUpImageProgress);

        phoneNo = getIntent().getStringExtra("phone_No");
        forMustLogin = getIntent().getStringExtra("mustLoginFirst");

        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), Send_Otp_Page.class);
                startActivity(intent);
                finish();
            }
        });

        submitToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isConnected(User_SignUp.this)) {
                    showConnectionDialog();
                    progressBar.setVisibility(View.GONE);
                }
                if (!validateFullName() | !validateEmail() | !validateGender()) {
                    return;
                }
                storeNewData();
            }
        });
    }

    private boolean isConnected(User_SignUp user_signUp) {
        ConnectivityManager connectivityManager = (ConnectivityManager) user_signUp.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected())) {
            return true;
        } else {
            return false;
        }
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(User_SignUp.this);
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

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullName.setError("Field can not be empty");
            return false;
        } else {
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = emailId.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            emailId.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            emailId.setError("Invalid Email!");
            return false;
        } else {
            emailId.setError(null);
            emailId.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (rMale.isChecked()) {
                gender = "Male";
            } else if (rFemale.isChecked()) {
                gender = "Female";
            }
            return true;
        }
    }

    public void selectPicture(View view) {
        Intent pickPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPicture, 1);
    }

    //result by an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                galleryImageUri = data.getData();
                profileImage.setImageURI(galleryImageUri);
                uploadGalleryImage();
            }
        }
    }

    private void uploadGalleryImage() {
        final String randaomKey = UUID.randomUUID().toString();
        StorageReference stoRef = storageReference.child("userProfileImages/" + randaomKey);

        stoRef.putFile(galleryImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(User_SignUp.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        stoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageProgressBar.setVisibility(View.GONE);
                                imageUrl = uri.toString();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageProgressBar.setVisibility(View.GONE);
                Toast.makeText(User_SignUp.this, "not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                if (!isConnected(User_SignUp.this)) {
                    showConnectionDialog();
                }
                imageProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    //here store data on firebase database
    private void storeNewData() {
        progressBar.setVisibility(View.VISIBLE);
        String profile_image;
        try {
            FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
            DatabaseReference reference = rootNode.getReference();

            Query checkEmail = FirebaseDatabase.getInstance()
                    .getReference("User").orderByChild("email_id")
                    .equalTo(emailId.getEditText().getText().toString());
            if (imageUrl != null) {
                profile_image = imageUrl;
            } else {
                profile_image = "";
            }

            checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()) {
                        userData = new User_Data(
                                fullName.getEditText().getText().toString(),
                                emailId.getEditText().getText().toString(),
                                phoneNo,
                                gender,
                                false, profile_image
                        );

                        reference.child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userData);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                intent = new Intent(getApplicationContext(), Successful_create.class);
                                intent.putExtra("textUpdate", "Account Create");
                                intent.putExtra("massage", "Your account has been created");
                                intent.putExtra("mustLoginFirst", forMustLogin);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(User_SignUp.this, "Already have this Email Id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(User_SignUp.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}