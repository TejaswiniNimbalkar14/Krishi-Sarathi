package com.tejaswininimbalkar.krishisarathi.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.HashMap;

public class UserDashboardFragment extends Fragment {
    TextView wlcmMsg;
    ImageView enterSession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        wlcmMsg = (TextView) view.findViewById(R.id.wlcmMsg);
        enterSession = (ImageView) view.findViewById(R.id.enterUserSession);


//        //Get access to login session
//        SessionManager sessionManager = new SessionManager(getActivity());
//
//        if(sessionManager.checkLogin()) {
//
//            //Get String type data stored in the session
//            HashMap<String, String> stringUserData = sessionManager.getStringDataFromSession();
//
//            String fullName = stringUserData.get(SessionManager.KEY_FULLNAME);
//
//            String name = fullName.substring(0, fullName.indexOf(" "));
//
//            wlcmMsg.setText("Welcome " + name + "!");
//
//        }
//        else {
//            enterSession.setVisibility(View.VISIBLE);
//            enterUserSession();
//        }

        return view;
    }

    public void enterUserSession() {
        enterSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Send_Otp_Page.class));
                getActivity().finish();
            }
        });
    }
}