package com.tejaswininimbalkar.krishisarathi.Common.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

//Jayesh Pravin Borase

public class IntroPref {

    private static final String PREF_NAME = "xyz";
    private static final String IS_FIRST_TIME_LAUNCH = "firstTimeLaunch";
    private static final String IS_FIRST_TIME_SELECT = "firstTimeSelect";
    private static final String IS_FIRST_TIME_SEND_OTP = "firstTimeSendOtp";
    private static final String IS_FIRST_TIME_ADD_EQUI = "firstTimeAddEqui";
    private static final String IS_CANCEL = "isCancel";
    private static final String IS_OWNER = "isOwner";
    private static final String LANGUAGE_CODE = "languageCode";
    private String LOCATION = "Add Location";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public IntroPref(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setIsFirstTimeLaunch(boolean firstTimeLaunch) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, firstTimeLaunch);
        editor.commit();
    }

    public void setIsFirstTimeSelect(boolean firstTimeSelect) {
        editor.putBoolean(IS_FIRST_TIME_SELECT, firstTimeSelect);
        editor.commit();
    }

    public void setIsFirstTimeSendOtp(boolean firstTimeSendOtp) {
        editor.putBoolean(IS_FIRST_TIME_SEND_OTP, firstTimeSendOtp);
        editor.commit();
    }

    public void setIsFirstTimeAddEqui(boolean firstTimeAddEqui) {
        editor.putBoolean(IS_FIRST_TIME_ADD_EQUI, firstTimeAddEqui);
        editor.commit();
    }

    public void setIsCancel(boolean cancel) {
        editor.putBoolean(IS_CANCEL, cancel);
        editor.commit();
    }

    public void setIsOwner(boolean isOwner) {
        editor.putBoolean(IS_OWNER, isOwner);
        editor.commit();
    }

    public String getLocation() {
        return preferences.getString(LOCATION, "Add Location");
    }

    public String getLanguageCode() {
        return preferences.getString(LANGUAGE_CODE, "en");
    }

    public void setLanguageCode(String languageCode) {
        editor.putString(LANGUAGE_CODE, languageCode);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public boolean isFirstTimeSelect() {
        return preferences.getBoolean(IS_FIRST_TIME_SELECT, true);
    }

    public boolean isFirstTimeAddEqui() {
        return preferences.getBoolean(IS_FIRST_TIME_ADD_EQUI, true);
    }

    public boolean isFirstTimeSendOtp() {
        return preferences.getBoolean(IS_FIRST_TIME_SEND_OTP, true);
    }

    public boolean isOwner() {
        return preferences.getBoolean(IS_OWNER, false);
    }

    public void setLocation(String location) {
        editor.putString(LOCATION, "Add Location");
        editor.commit();
    }
}
