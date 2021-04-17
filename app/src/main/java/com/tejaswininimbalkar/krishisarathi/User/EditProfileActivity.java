package com.tejaswininimbalkar.krishisarathi.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Model.User_Data;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityEditProfileBinding;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/*
 * @author Tejaswini Nimbalkar
 */

public class EditProfileActivity extends AppCompatActivity {

    TextInputLayout fullName, phoneNo, emailId;
    ImageView profileImage;

    String name, email, phone, uid, updatedName, updatedEmail;

    private StorageReference storageReference;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        fullName = (TextInputLayout) findViewById(R.id.editFullName);
        phoneNo = (TextInputLayout) findViewById(R.id.editPhone);
        emailId = (TextInputLayout) findViewById(R.id.editEmail);
        profileImage = findViewById(R.id.editProfileImage);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            uid = user.getUid();
        } else {
            Toast.makeText(this, "not working", Toast.LENGTH_SHORT).show();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("User").child(uid);

        storageReference = FirebaseStorage.getInstance().getReference();

        loadUserInformation();
    }

    private void loadUserInformation() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = (String) snapshot.child("fullName").getValue();
                phone = (String) snapshot.child("phone_num").getValue();
                email = (String) snapshot.child("email_id").getValue();

                fullName.getEditText().setText(name);
                phoneNo.getEditText().setText(phone);
                emailId.getEditText().setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        if(!validateFullName() || !validateEmail()) {
            return;
        }
        updateData();
    }

    public void updateData() {
        if(isNameChanged() || isEmailChanged()) {
            reference.child("fullName").setValue(fullName.getEditText().getText().toString());
            reference.child("email_id").setValue(emailId.getEditText().getText().toString());
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Data is same and cannot be updated!", Toast.LENGTH_SHORT).show();
    }

    private boolean isNameChanged() {
        updatedName = fullName.getEditText().getText().toString();
        if(!updatedName.equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        updatedEmail = emailId.getEditText().getText().toString();
        if(!updatedEmail.equals(email)) {
            return true;
        } else {
            return false;
        }
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
        pictureSourceDialog();
    }

    //Dialog to select picture source, camera, gallery or delete picture
    private void pictureSourceDialog() {
        //inflate the design on dialog
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.profile_picture_source, null);
        ImageView cameraAsSource = (ImageView) dialogView.findViewById(R.id.cameraPictureSource);
        ImageView libraryAsSource = (ImageView) dialogView.findViewById(R.id.libraryPictureSource);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setView(dialogView);

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        cameraAsSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermission()){
                    takeFromCamera();
                    alertDialog.cancel();
                }
            }
        });

        libraryAsSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFromLibrary();
                alertDialog.cancel();
            }
        });
    }

    public void selectFromLibrary(){
        Intent pickPicture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPicture, 1);
    }

    private void takeFromCamera() {
        Intent takePicture = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(takePicture.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePicture, 2);
        }
    }

    //result by an activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:     //from library
                if(resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    profileImage.setImageURI(selectedImageUri);
                }
                break;
            case 2:     //from camera
                if(resultCode == Activity.RESULT_OK){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    profileImage.setImageBitmap(bitmap);
                }
        }
    }

    //manual setting of camera permission for some sdk versions
    private boolean checkAndRequestPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            //for camera permission
            int cameraPermission = ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED) {
                //if permission is denied request for it
                ActivityCompat.requestPermissions(EditProfileActivity.this, new String[] { Manifest.permission.CAMERA }, 20);
                return false;
            }
        }
        return true;
    }

    //request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takeFromCamera();
        }
        else Toast.makeText(EditProfileActivity.this, "Permission not granted!", Toast.LENGTH_SHORT).show();
    }
}