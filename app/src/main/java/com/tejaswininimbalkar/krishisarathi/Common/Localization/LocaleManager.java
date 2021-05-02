package com.tejaswininimbalkar.krishisarathi.Common.Localization;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/*
 * @author Tejaswini Nimbalkar
 */

public class LocaleManager {
    private Context context;

    public LocaleManager(Context context) {
        this.context = context;
    }

    public void updateResources(String languageCode) {
        Locale locale = new Locale(languageCode);
        locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
