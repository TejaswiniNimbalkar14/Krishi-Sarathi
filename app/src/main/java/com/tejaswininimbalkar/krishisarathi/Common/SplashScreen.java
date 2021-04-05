package com.tejaswininimbalkar.krishisarathi.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.UserSignIn_page;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.User.SelectLanguage;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySplashScreenBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMER = 2000;

    //viewBinding
    ActivitySplashScreenBinding splashScreenBinding;

    //Animations
    Animation sideAnim, bottomAnim;
    TextView splashScreenAppName;

    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //If the app is compiled for first time, a 'SharedPreferences'
        //called 'onBoardingScreen' will be created
        IntroPref pref = new IntroPref(this);
        //'isFirst' and will set it to 'true'
        isFirst = pref.isFirstTimeLaunch();
        //set a light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        //Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(splashScreenBinding.getRoot());


        //Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        //Set animations on elements
        splashScreenBinding.splashScreenAppName.setAnimation(bottomAnim);

        //Splash screen to next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //And then as it is compiles first time it will create variable with name
                //If it is not first time it will set the value as 'false' of the
                // already created 'firstTime' variable

                if (!isFirst) {
                    //When it is not first time
                    startActivity(new Intent(SplashScreen.this, ContainerActivity.class));
                }
                else {
                    //To allow to edit 'SelectLanguage'
                    pref.setIsFirstTimeLaunch(false);
                    startActivity(new Intent(SplashScreen.this, SelectLanguage.class));
                }
                finish();   //to finish current activity once application is ran
            }
        }, SPLASH_TIMER);

    }
}