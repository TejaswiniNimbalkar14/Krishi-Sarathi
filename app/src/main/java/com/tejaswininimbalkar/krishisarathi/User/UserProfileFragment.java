package com.tejaswininimbalkar.krishisarathi.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tejaswininimbalkar.krishisarathi.EditProfileActivity;
import com.tejaswininimbalkar.krishisarathi.R;

/*
 * @author Tejaswini Nimbalkar
 */

public class UserProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        Button settings = (Button) view.findViewById(R.id.settingsBtn);
        Button editProfile = (Button) view.findViewById(R.id.profileEditBtn);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserSettings.class));
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        return view;
    }

}