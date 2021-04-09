package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tejaswininimbalkar.krishisarathi.Common.IntroPref;
import com.tejaswininimbalkar.krishisarathi.Common.OnBoarding;
import com.tejaswininimbalkar.krishisarathi.HelperClasses.LanguageAdapter;
import com.tejaswininimbalkar.krishisarathi.R;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySelectLanguageBinding;

import java.util.ArrayList;
import java.util.List;

/*
 * @author Tejaswini Nimbalkar
 */

public class SelectLanguage extends AppCompatActivity {

    ActivitySelectLanguageBinding activitySelectLanguageBinding;
    List<String> languageList;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For first time set 'isFirst' to 'true' and 'false' afterwards
        IntroPref pref = new IntroPref(this);
        isFirst = pref.isFirstTimeSelect();

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        //For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ViewBinding
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

        if(isFirst) {
            //If activity is running first time, set variable to 'false'
            pref.setIsFirstTimeSelect(false);
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
        if(isFirst) {
            startActivity(new Intent(this, OnBoarding.class));
        }
        else {
            startActivity(new Intent(this, UserSettings.class));
        }
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}