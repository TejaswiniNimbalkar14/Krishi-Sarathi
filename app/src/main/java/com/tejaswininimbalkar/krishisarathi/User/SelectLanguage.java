package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tejaswininimbalkar.krishisarathi.Common.OnBoarding;
import com.tejaswininimbalkar.krishisarathi.HelperClasses.LanguageAdapter;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySelectLanguageBinding;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Tejaswini Nimbalkar
 */

public class SelectLanguage extends AppCompatActivity {

    ActivitySelectLanguageBinding activitySelectLanguageBinding;
    List<String> languageList;
    String extra;
    SharedPreferences onBoarding;
    boolean isFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySelectLanguageBinding = ActivitySelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(activitySelectLanguageBinding.getRoot());

        activitySelectLanguageBinding.languageRV.setLayoutManager(new LinearLayoutManager(this));
        activitySelectLanguageBinding.languageRV.setHasFixedSize(true);

        languageList = new ArrayList<>();
        languageList.add("English");
        languageList.add("Marathi");
        languageList.add("Hindi");

        //Setting adapter to the recycler view to display languages list
        LanguageAdapter languageAdapter = new LanguageAdapter(languageList);
        activitySelectLanguageBinding.languageRV.setAdapter(languageAdapter);

        onBoarding = getSharedPreferences("onBoarding", MODE_PRIVATE);

        isFirstTime = onBoarding.getBoolean("firstTime", true);

        if(isFirstTime) {
            SharedPreferences.Editor editor = onBoarding.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            activitySelectLanguageBinding.skipLanguageBtn.setVisibility(View.VISIBLE);
        }
        else {
            activitySelectLanguageBinding.skipLanguageBtn.setVisibility(View.INVISIBLE);
            activitySelectLanguageBinding.languageBackBtn.setVisibility(View.VISIBLE);
        }
    }

    public void skipSelectLanguage(View view) {
        startActivity(new Intent(this, OnBoarding.class));
        finish();
    }

    public void nextSelectLanguage(View view) {
        if(isFirstTime) {
            startActivity(new Intent(this, OnBoarding.class));
            finish();
        }
        else {
            startActivity(new Intent(this, UserSettings.class));
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }
}