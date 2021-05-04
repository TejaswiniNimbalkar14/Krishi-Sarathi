package com.tejaswininimbalkar.krishisarathi.Common.OnBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Send_Otp_Page;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityOnBoardingBinding;

/*
 * @author Tejaswini Nimbalkar
 */

public class OnBoarding extends AppCompatActivity {

    ActivityOnBoardingBinding activityOnBoardingBinding;
    SliderAdapter sliderAdapter;
    TextView[] dots;
    Animation animation;
    int curPos;
    //To change the color of dots when page is changed
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            curPos = position;

            //To make 'Get Started' button visible only at last slide
            //and to make next, '>' button invisible at last slide
            if (position == 0) {
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.VISIBLE);
            } else if (position == 2) {
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.INVISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.VISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.bottom_anim);
                activityOnBoardingBinding.getStartedBtn.setAnimation(animation);
                activityOnBoardingBinding.getStartedBtn.setVisibility(View.VISIBLE);
                activityOnBoardingBinding.nextBtn.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        //For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityOnBoardingBinding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(activityOnBoardingBinding.getRoot());

        //Call adapter
        sliderAdapter = new SliderAdapter(this);
        activityOnBoardingBinding.slider.setAdapter(sliderAdapter);

        addDots(0);     //0 as default position
        activityOnBoardingBinding.slider.addOnPageChangeListener(onPageChangeListener); //To change the color of dots
    }

    public void skip(View view) {
        startActivity(new Intent(this, Send_Otp_Page.class));
        finish();   //To finish current activity if user wants to skip
    }

    public void next(View view) {
        activityOnBoardingBinding.slider.setCurrentItem(curPos + 1);
    }

    public void getStarted(View view) {
        startActivity(new Intent(this, Send_Otp_Page.class));
        finish();
    }

    //To add dots at the bottom
    private void addDots(int pos) {
        dots = new TextView[4];

        activityOnBoardingBinding.dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);

            activityOnBoardingBinding.dots.addView(dots[i]);
        }
        //We will get 'pos' from 'onPageChangeListener'
        if (dots.length > 0) {
            dots[pos].setTextColor(getResources().getColor(R.color.purple_500));
        }
    }
}