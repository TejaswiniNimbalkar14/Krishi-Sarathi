package com.tejaswininimbalkar.krishisarathi.User;

/*
 * @author Tejaswini Nimbalkar
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tejaswininimbalkar.krishisarathi.Common.AppCompat;
import com.tejaswininimbalkar.krishisarathi.Common.Navigation.YourOrdersActivity;
import com.tejaswininimbalkar.krishisarathi.Owner.MyEquipmentFragment;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.R;

public class UserProfileFragment extends Fragment {

    String uid;
    private Button settings, editProfile, moveBtn, myOrders;
    private TextView fullName, phoneNo;
    private ImageView profileImage;
    private ProgressBar imageProgress, infoProgress;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        settings = (Button) view.findViewById(R.id.settingsBtn);
        editProfile = (Button) view.findViewById(R.id.profileEditBtn);
        fullName = (TextView) view.findViewById(R.id.profileFullName);
        phoneNo = (TextView) view.findViewById(R.id.profileContact);
        profileImage = (ImageView) view.findViewById(R.id.profileImage);
        imageProgress = (ProgressBar) view.findViewById(R.id.imageProgressBar);
        infoProgress = (ProgressBar) view.findViewById(R.id.infoProgress);
        moveBtn = (Button) view.findViewById(R.id.moveToOwnerActivity);
        myOrders = (Button) view.findViewById(R.id.myOrdersBtn);

        imageProgress.setVisibility(View.VISIBLE);
        infoProgress.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        uploadUserData();

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserSettings.class));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), OwnerLoginActivity.class);
                startActivity(i);
            }
        });

        myOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), YourOrdersActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    private void uploadUserData() {
        if (!isConnected(getActivity())) {
            showConnectionDialog();
        }
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("User").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = (String) snapshot.child("fullName").getValue();
                String phone = (String) snapshot.child("phone_num").getValue();
                String url = (String) snapshot.child("profile_img").getValue();
                try{
                boolean equiOwner = (boolean) snapshot.child("equipment_owner").getValue();
                if (equiOwner) moveBtn.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                fullName.setText(name);
                phoneNo.setText(phone);
                Glide.with(UserProfileFragment.this).load(url).into(profileImage);
                imageProgress.setVisibility(View.GONE);
                infoProgress.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isConnected(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnection = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (wifiConnection != null && wifiConnection.isConnected()) || (mobileConnection != null && mobileConnection.isConnected());
    }

    private void showConnectionDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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