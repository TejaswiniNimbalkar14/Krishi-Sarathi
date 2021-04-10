package com.tejaswininimbalkar.krishisarathi.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_EMAIL_ID = "emailID";
    public static final String KEY_PHONE_NO = "phoneNo";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EQUI_OWNER = "equiOwner";
    //private because user should not have access of it
    private static final String IS_LOGIN = "IsLoggedIn";
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    //Which activity is calling this class
    Context context;

    public SessionManager(Context _context) {
        context = _context;

        //This will check whether "userLoginSession" is created, if not it will create one
        userSession = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);

        editor = userSession.edit();
    }

    //To create a login session of user
    public void createLoginSession(String fullName, String emailId, String phoneNo, String gender, String password, Boolean equiOwner) {

        //When user logs in, this will be set to true
        editor.putBoolean(IS_LOGIN, true);

        //put all data of user in variables
        editor.putString(KEY_FULLNAME, fullName);
        editor.putString(KEY_EMAIL_ID, emailId);
        editor.putString(KEY_PHONE_NO, phoneNo);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_GENDER, gender);
        editor.putBoolean(KEY_EQUI_OWNER, equiOwner);

        editor.commit();

    }

    //To get the String type data from session
    public HashMap<String, String> getStringDataFromSession() {
        HashMap<String, String> stringUserData = new HashMap<String, String>();

        //get data from session and store in "stringUserData"
        stringUserData.put(KEY_FULLNAME, userSession.getString(KEY_FULLNAME, null));
        stringUserData.put(KEY_EMAIL_ID, userSession.getString(KEY_EMAIL_ID, null));
        stringUserData.put(KEY_PHONE_NO, userSession.getString(KEY_PHONE_NO, null));
        stringUserData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        stringUserData.put(KEY_GENDER, userSession.getString(KEY_GENDER, null));

        return stringUserData;
    }

    //To get Boolean type data from session
    public HashMap<String, Boolean> getBooleanDataFromSession() {
        HashMap<String, Boolean>  booleanUserData = new HashMap<String, Boolean>();

        booleanUserData.put(KEY_EQUI_OWNER, userSession.getBoolean(KEY_EQUI_OWNER, false));

        return booleanUserData;
    }

    //Check whether user login
    public boolean checkLogin() {
        //Following 'true' does not set "IS_LOGIN" to true,
        //the following statement will just return true if user is logged in
        //else false
        if (userSession.getBoolean(IS_LOGIN, true)) {
            return true;
        } else {
            return false;
        }
    }

    //When user logs out from session
    public void logoutUserFromSession() {
        editor.clear();
        editor.commit();
    }

}
