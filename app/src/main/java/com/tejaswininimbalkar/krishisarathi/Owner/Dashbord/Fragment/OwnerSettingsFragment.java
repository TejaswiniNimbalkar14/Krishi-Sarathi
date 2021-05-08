package com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tejaswininimbalkar.krishisarathi.Common.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.R;

public class OwnerSettingsFragment extends Fragment {

    private Button moveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_owner_settings, container, false);

        moveBtn = (Button) view.findViewById(R.id.moveToUserActivity);

        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ContainerActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}