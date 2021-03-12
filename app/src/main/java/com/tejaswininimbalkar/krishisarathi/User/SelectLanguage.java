package com.tejaswininimbalkar.krishisarathi.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tejaswininimbalkar.krishisarathi.HelperClasses.LanguageAdapter;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivitySelectLanguageBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectLanguage extends AppCompatActivity {

    ActivitySelectLanguageBinding activitySelectLanguageBinding;
    List<String> languageList;
    String extra;

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

//        if(getIntent() != null) {
//            extra = getIntent().getStringExtra("changeBtns");
//            if(extra == "Change Buttons") {
//                activitySelectLanguageBinding.languageBackBtn.setVisibility(View.VISIBLE);
//            }
//        }
    }

    public void skipSelectLanguage(View view) {
        startActivity(new Intent(this, UserLogin.class));
        finish();
    }

    public void nextSelectLanguage(View view) {
        startActivity(new Intent(this, UserLogin.class));
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }
}