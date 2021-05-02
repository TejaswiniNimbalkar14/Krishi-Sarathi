package com.tejaswininimbalkar.krishisarathi.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.UUID;

/*
 * @author Tejaswini Nimbalkar
 */

public class EditProfileActivity extends AppCompat {

    String name, email, phone, uid, updatedName, updatedEmail, url;
    StorageReference storageReference;
    Uri galleryImageUri;
    private TextInputLayout fullName, phoneNo, emailId;
    private ImageView profileImage;
    private ProgressBar imageProgressBar, progressBar;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = (TextInputLayout) findViewById(R.id.editFullName);
        phoneNo = (TextInputLayout) findViewById(R.id.editPhone);
        emailId = (TextInputLayout) findViewById(R.id.editEmail);
        profileImage = findViewById(R.id.editProfileImage);
        imageProgressBar = findViewById(R.id.editImageProgress);
        progressBar = findViewById(R.id.editProgressBar);

        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        } else {
            Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        loadUserInformation();
    }

    private void loadUserInformation() {
        if (!isConnected(EditProfileActivity.this)) {
            showConnectionDialog();
        }
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = (String) snapshot.child("fullName").getValue();
                phone = (String) snapshot.child("phone_num").getValue();
                email = (String) snapshot.child("email_id").getValue();
                url = (String) snapshot.child("profile_img").getValue();

                fullName.getEditText().setText(name);
                phoneNo.getEditText().setText(phone);
                emailId.getEditText().setText(email);
                Glide.with(EditProfileActivity.this).load(url).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected(EditProfileActivity editProfileActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) editProfileActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected());
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(EditProfileActivity.this);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void cancelEdit(View view) {
        onBackPressed();
    }

    public void updateProfile(View view) {
        if (!isConnected(EditProfileActivity.this)) {
            showConnectionDialog();
            progressBar.setVisibility(View.GONE);
        }
        if (!validateFullName() || !validateEmail()) {
            return;
        }
        updateData();
    }

    public void updateData() {
        if (isConnected(EditProfileActivity.this)) {
            progressBar.setVisibility(View.VISIBLE);
            if (isNameChanged() || isEmailChanged()) {
                reference.child("fullName").setValue(fullName.getEditText().getText().toString());
                reference.child("email_id").setValue(emailId.getEditText().getText().toString());
                Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Data is same and cannot be updated!", Toast.LENGTH_SHORT).show();
            }
        }
        progressBar.setVisibility(View.GONE);
    }

    private boolean isNameChanged() {
        updatedName = fullName.getEditText().getText().toString();
        return !updatedName.equals(name);
    }

    private boolean isEmailChanged() {
        updatedEmail = emailId.getEditText().getText().toString();
        return !updatedEmail.equals(email);
    }

    private boolean validateFullName() {
        String val = fullName.getEditText().getText().toString();
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
                        Toast.makeText(EditProfileActivity.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        stoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageProgressBar.setVisibility(View.GONE);
                                reference.child("profile_img").setValue(uri.toString());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                imageProgressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this, "not uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                imageProgressBar.setVisibility(View.VISIBLE);
                if (!isConnected(EditProfileActivity.this)) {
                    showConnectionDialog();
                }
            }
        });
    }
}