package com.tejaswininimbalkar.krishisarathi.Common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tejaswininimbalkar.krishisarathi.Common.Localization.LocaleManager;
import com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences.IntroPref;

/*
 * @author Tejaswini Nimbalkar
 */

public class AppCompat extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        IntroPref pref = new IntroPref(this);
        super.onCreate(savedInstanceState);

        LocaleManager localeManager = new LocaleManager(this);
        localeManager.updateResources(pref.getLanguageCode());
    }
}