package com.tejaswininimbalkar.krishisarathi.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.HashMap;

public class UserDashboardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        TextView wlcmMsg = (TextView) view.findViewById(R.id.wlcmMsg);

        //Get access to login session
        SessionManager sessionManager = new SessionManager(getActivity());
        //Get String type data stored in the session
        HashMap<String, String> stringUserData = sessionManager.getStringDataFromSession();

        String fullName = stringUserData.get(SessionManager.KEY_FULLNAME);

        String name = fullName.substring(0, fullName.indexOf(" "));

        wlcmMsg.setText("Welcome " + name + "!");

        return view;
    }
}