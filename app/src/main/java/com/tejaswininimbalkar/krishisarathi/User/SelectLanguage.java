package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.tejaswininimbalkar.krishisarathi.Common.IntroPref;
import com.tejaswininimbalkar.krishisarathi.Common.Localization.LanguageDTO;
import com.tejaswininimbalkar.krishisarathi.Common.Localization.LanguageList;
import com.tejaswininimbalkar.krishisarathi.Common.Localization.LocaleManager;
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
    private LanguageAdapter languageAdapter;
    private int selectedLanguagePosition = -1;
    private LocaleManager localeManager;
    boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For first time set 'isFirst' to 'true' and 'false' afterwards
        IntroPref pref = new IntroPref(this);

        //set a no night mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);

        //For fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ViewBinding
        activitySelectLanguageBinding = ActivitySelectLanguageBinding.inflate(getLayoutInflater());
        setContentView(activitySelectLanguageBinding.getRoot());

        isFirst = pref.isFirstTimeSelect();

        if(isFirst) {
            //If activity is running first time, set variable to 'false'
            pref.setIsFirstTimeSelect(false);
            activitySelectLanguageBinding.skipLanguageBtn.setVisibility(View.VISIBLE);
        }
        else {
            activitySelectLanguageBinding.skipLanguageBtn.setVisibility(View.GONE);
            activitySelectLanguageBinding.languageBackBtn.setVisibility(View.VISIBLE);
        }

        List<LanguageDTO> languageList = LanguageList.getLanguageList();
        localeManager = new LocaleManager(this);

        onNextClick(languageList);
        configureAdapter(languageList);
    }

    public void skipSelectLanguage(View view) {
        startActivity(new Intent(this, OnBoarding.class));
        finish();
    }

    private void onNextClick(List<LanguageDTO> languageList) {
        activitySelectLanguageBinding.nextSelectLanguageBtn.setOnClickListener(view -> {
            if(selectedLanguagePosition >= 0 && activitySelectLanguageBinding.nextSelectLanguageBtn.isEnabled()) {
                String code = languageList.get(selectedLanguagePosition).getLanguageCode();
                localeManager.updateResources(code);
                recreate();
                if(isFirst) {
                    startActivity(new Intent(this, OnBoarding.class));
                }
                else {
                    startActivity(new Intent(this, UserSettings.class));
                }
                finish();
            } else {
                Toast.makeText(this, R.string.select_to_proceed, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configureAdapter(List<LanguageDTO> languageList) {
        activitySelectLanguageBinding.languageRV.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        languageAdapter = new LanguageAdapter(((position, language) -> {
            selectedLanguagePosition = position;
            activitySelectLanguageBinding.nextSelectLanguageBtn.setEnabled(true);
            languageAdapter.notifyDataSetChanged();
        }),selectedLanguagePosition, languageList);
        activitySelectLanguageBinding.languageRV.setAdapter(languageAdapter);
    }

//    public void nextSelectLanguage(View view) {
//        if(isFirst) {
//            startActivity(new Intent(this, OnBoarding.class));
//        }
//        else {
//            startActivity(new Intent(this, UserSettings.class));
//        }
//        finish();
//    }

    public void goBack(View view) {
        onBackPressed();
    }
}