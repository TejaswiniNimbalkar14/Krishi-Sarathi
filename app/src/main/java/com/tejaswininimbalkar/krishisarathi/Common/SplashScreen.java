package com.tejaswininimbalkar.krishisarathi.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatDelegate;

import com.tejaswininimbalkar.krishisarathi.Common.Localization.SelectLanguage;
import com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences.IntroPref;
import com.tejaswininimbalkar.krishisarathi.Owner.Dashbord.OwnerContainer;
import com.tejaswininimbalkar.krishisarathi.Owner.OwnerLoginActivity;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySplashScreenBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class SplashScreen extends AppCompat {

    private static final int SPLASH_TIMER = 2000;

    //viewBinding
    ActivitySplashScreenBinding splashScreenBinding;

    //Animations
    Animation bottomAnim;

    boolean isFirst, isOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Calling 'IntroPref' SharedPreferences
        IntroPref pref = new IntroPref(this);
        //The variable 'isFirst' will be set to 'true' for first time and 'false' afterwards
        isFirst = pref.isFirstTimeLaunch();
        isOwner = pref.isOwner();

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        //For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //View Binding
        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());


        //Animations
        //sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Set animations on elements
        splashScreenBinding.splashScreenAppName.setAnimation(bottomAnim);

        //Splash screen to next screen automatically after few seconds(described)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFirst) {
                    if (isOwner) {
                        startActivity(new Intent(SplashScreen.this, OwnerContainer.class));
                    } else {
                        startActivity(new Intent(SplashScreen.this, ContainerActivity.class));
                    }
                } else {
                    //When it is first time, set the variable 'isFirst' to 'false'
                    pref.setIsFirstTimeLaunch(false);
                    startActivity(new Intent(SplashScreen.this, SelectLanguage.class));
                }
                finish();   //to finish current activity once application is ran
            }
        }, SPLASH_TIMER);

    }
}