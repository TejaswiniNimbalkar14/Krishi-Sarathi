package com.tejaswininimbalkar.krishisarathi.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.User.ContainerActivity;
import com.tejaswininimbalkar.krishisarathi.User.SelectLanguage;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMER = 5000;

    //viewBinding
    ActivitySplashScreenBinding splashScreenBinding;

    //Animations
    Animation sideAnim, bottomAnim;

    SharedPreferences selectLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                //If the app is compiled for first time, a 'SharedPreferences'
                //called 'onBoardingScreen' will be created
                selectLanguage = getSharedPreferences("selectLanguage", MODE_PRIVATE);
                //And then as it is compiles first time it will create variable with name
                //'firstTime' and will set it to 'true'
                boolean isFirstTime = selectLanguage.getBoolean("firstTime" ,true);
                //If it is not first time it will set the value as 'false' of the
                // already created 'firstTime' variable

                if (isFirstTime) {

                    //To allow to edit 'onBoardingScreen'
                    SharedPreferences.Editor editor = selectLanguage.edit();
                    editor.putBoolean("firstTime", false);  //since it will not be first time
                    editor.commit();

                    startActivity(new Intent(SplashScreen.this, SelectLanguage.class));
                    finish();   //to finish current activity once application is ran
                }
                else {
                    //When it is not first time
                    startActivity(new Intent(SplashScreen.this, ContainerActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIMER);

    }
}