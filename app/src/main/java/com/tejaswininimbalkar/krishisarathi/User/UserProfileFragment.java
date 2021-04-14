package com.tejaswininimbalkar.krishisarathi.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.HashMap;

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
        TextView fullName = (TextView) view.findViewById(R.id.profileFullName);
        TextView phoneNo = (TextView) view.findViewById(R.id.profileContact);

        String email = getActivity().getIntent().getStringExtra("email");

        //Get access to login session
        SessionManager sessionManager = new SessionManager(getActivity());

        if(sessionManager.checkLogin()) {
            //Get String type data from login session
            HashMap<String, String> stringUserData = sessionManager.getStringDataFromSession();

            String name = stringUserData.get(SessionManager.KEY_FULLNAME);
            String phone = stringUserData.get(SessionManager.KEY_PHONE_NO);

            fullName.setText(name);
            phoneNo.setText(phone);
        }


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
                i.putExtra("cameFrom", "userProfile");
                i.putExtra("email", email);
                startActivity(i);
            }
        });

        return view;
    }

}