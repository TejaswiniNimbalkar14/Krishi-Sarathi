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

import com.google.firebase.auth.FirebaseAuth;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.Databases.SessionManager;
import com.tejaswininimbalkar.krishisarathi.R;

import java.util.HashMap;

public class UserDashboardFragment extends Fragment {

    private TextView wlcmMsg;
    private ImageView enterSession;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_dashboard, container, false);

        wlcmMsg = (TextView) view.findViewById(R.id.wlcmMsg);
        enterSession = (ImageView) view.findViewById(R.id.enterUserSession);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            enterSession.setVisibility(View.VISIBLE);
            enterUserSession();
        } else {
            enterSession.setVisibility(View.GONE);
        }

        return view;
    }

    private void enterUserSession() {
        enterSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Send_Otp_Page.class));
                getActivity().finish();
            }
        });
    }
}