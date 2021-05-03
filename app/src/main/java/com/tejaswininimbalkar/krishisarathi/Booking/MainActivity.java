package com.tejaswininimbalkar.krishisarathi.Booking;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tejaswininimbalkar.krishisarathi.Booking.Model.MyModel;
import com.tejaswininimbalkar.krishisarathi.R;

import java.io.InputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText machineName, machineDescription, machinePrice;
    private Button addInfo, showBtn, browse;
    private ImageView imageView;
    private Uri imageUri;
    private Bitmap bitmap;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_d);

        imageView = findViewById(R.id.uploadImg);
        browse = findViewById(R.id.bImgBtn);
        machineName = findViewById(R.id.nameOfMachine);
        machinePrice = findViewById(R.id.priceOfMachine);
        machineDescription = findViewById(R.id.descriptionOfMachine);
        addInfo = findViewById(R.id.addInfobtn);
        showBtn = findViewById(R.id.showbtn);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(MainActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        addInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowActivity.class));
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            imageUri = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {

            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void uploadToFirebase() {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Image Uploader");
        progressDialog.show();

        machineName = findViewById(R.id.nameOfMachine);
        machinePrice = findViewById(R.id.priceOfMachine);
        machineDescription = findViewById(R.id.descriptionOfMachine);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(50));
        uploader.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                progressDialog.dismiss();
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference("Equipment");

                                MyModel myModel = new MyModel(machineName.getText().toString(), uri.toString());

                                root.push().setValue(myModel);

                                machineName.setText("");
                                machinePrice.setText("");
                                machineDescription.setText("");
                                imageView.setImageResource(R.drawable.ic_launcher_background_d);
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded :" + (int) percent + "%");
                    }
                });

        System.out.println("Hello");
    }


}

