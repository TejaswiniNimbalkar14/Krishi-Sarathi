package com.tejaswininimbalkar.krishisarathi.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityEditProfileBinding;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/*
 * @author Tejaswini Nimbalkar
 */

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding activityEditProfileBinding;
    String _NAME, email;
    StorageReference storageReference;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEditProfileBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(activityEditProfileBinding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        loadUserInformation();

        reference = FirebaseDatabase.getInstance().getReference("User");
    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null) {
            if(user.getDisplayName() != null) {
                activityEditProfileBinding.editFullName.getEditText().setText(user.getDisplayName());
            }
        }
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
        if(!validateFullName()) {
            return;
        }
        updateData();
    }

    public void updateData() {
        if(isNameChanged()) {
            String updatedName = activityEditProfileBinding.editFullName.getEditText().getText().toString();
            String udatedEmail = activityEditProfileBinding.editEmail.getEditText().getText().toString();
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Data is same and cannot be updated!", Toast.LENGTH_SHORT).show();
    }

    private boolean isNameChanged() {
        String updatedName = activityEditProfileBinding.editFullName.getEditText().getText().toString();
        if(!_NAME.equals(updatedName)) {
            reference.child(email).child("fullName").setValue(updatedName);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean validateFullName() {
        if (_NAME.isEmpty()) {
            activityEditProfileBinding.editFullName.setError("Field can not be empty");
            return false;
        } else {
            activityEditProfileBinding.editFullName.setError(null);
            activityEditProfileBinding.editFullName.setErrorEnabled(false);
            return true;
        }
    }

//    private boolean validateEmail() {
//        String val = emailId.getEditText().getText().toString().trim();
//        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
//
//        if (val.isEmpty()) {
//            emailId.setError("Field can not be empty");
//            return false;
//        } else if (!val.matches(checkEmail)) {
//            emailId.setError("Invalid Email!");
//            return false;
//        } else {
//            emailId.setError(null);
//            emailId.setErrorEnabled(false);
//            return true;
//        }
//    }

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
                    activityEditProfileBinding.editProfileImage.setImageURI(selectedImageUri);
                    //uploadImagetoFirebase(selectedImageUri);
                }
                break;
            case 2:     //from camera
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    activityEditProfileBinding.editProfileImage.setImageBitmap(bitmap);
                    uploadImageBitmap(bitmap);
                }
        }
    }

    //manual setting of camera permission for some sdk versions
    private boolean checkAndRequestPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            //for camera permission
            int cameraPermission = ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED) {
                //if permision is denied request for it
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

    private void uploadImagetoFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("userProfileImages");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfileActivity.this, "Picture updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImageBitmap(Bitmap bitmap) {
        //The bitmap is thumbnail of image, no actual image
        //so to maintain the quality we need to pass quality as 100
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("userProfileImages")
                .child(email + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.e(TAG, "onFailure: ", e.getCause());
                    }
                });
    }

    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Log.d(TAG, "onSuccess: " + uri);
                        setUserProfileUrl(uri);
                    }
                });
        //no failure listener as it is a task
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Profile image failde...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}