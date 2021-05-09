package com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences.IntroPref;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.R;

public class OwnerSettingsFragment extends Fragment {

    private Button moveBtn;
    private IntroPref pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_settings, container, false);

        moveBtn = (Button) view.findViewById(R.id.moveToUserActivity);
        pref = new IntroPref(getContext());

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userOwnerChoiceDialog();
            }
        });

        return view;
    }

    private void userOwnerChoiceDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Do you want to stay logged in as User?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pref.setIsOwner(false);
                moveToUser();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pref.setIsOwner(true);
                moveToUser();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void moveToUser() {
        Intent i = new Intent(getActivity(), ContainerActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}