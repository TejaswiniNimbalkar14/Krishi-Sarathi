package com.tejaswininimbalkar.krishisarathi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.Successful_create;
import com.tejaswininimbalkar.krishisarathi.Common.LoginSignup.User_Forgot_Page;
import com.tejaswininimbalkar.krishisarathi.User.UserSettings;
import com.tejaswininimbalkar.krishisarathi.databinding.ActivityResetPassUsingCurrentPassBinding;


/*
 * @author Tejaswini Nimbalkar
 */

public class ResetPassUsingCurrentPass extends AppCompatActivity {

    ActivityResetPassUsingCurrentPassBinding resetPassUsingCurrentPassBinding;
    String currentPass, newPass, conPass;
    String email = "";
    String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resetPassUsingCurrentPassBinding = ActivityResetPassUsingCurrentPassBinding.inflate(getLayoutInflater());
        setContentView(resetPassUsingCurrentPassBinding.getRoot());

        currentPass = resetPassUsingCurrentPassBinding.resetPassCurrent.getEditText().getText().toString();
        newPass = resetPassUsingCurrentPassBinding.resetPassNew.getEditText().getText().toString();
        conPass = resetPassUsingCurrentPassBinding.resetPassConfirm.getEditText().getText().toString();

        //SessionManager sessionManager = new SessionManager(this);
        //HashMap<String, String> stringUserData = sessionManager.getStringDataFromSession();
        //email = stringUserData.get(sessionManager.KEY_EMAIL_ID);
        email = email.substring(0, email.indexOf("@"));
        //pass = stringUserData.get(SessionManager.KEY_PASSWORD);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, UserSettings.class));
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
    }

    public void forgotCurrentPass(View view) {
        startActivity(new Intent(this, User_Forgot_Page.class));
        finish();
    }

    public void resetPassUsingCurrentPass(View view) {
        if (!validateCurPass() || !validateNewPass() || !validateConPass()) {
            return;
        }
        updatePassword();
    }

    private void updatePassword() {
        try {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
            reference.child(email).child("password")
                    .setValue(resetPassUsingCurrentPassBinding.resetPassNew.getEditText().getText().toString());
            Intent intent = new Intent(getApplicationContext(), Successful_create.class);
            intent.putExtra("textUpdate", "Password\nChanged");
            intent.putExtra("massage", "Your password has been changed");
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateCurPass() {
        if (currentPass.isEmpty()) {
            resetPassUsingCurrentPassBinding.resetPassNew.setError("Field can not be empty");
            return false;
        } else if (!currentPass.equals(pass)) {
            resetPassUsingCurrentPassBinding.resetPassNew.setError("Current password doesn't match!");
            return false;
        } else {
            resetPassUsingCurrentPassBinding.resetPassNew.setError(null);
            resetPassUsingCurrentPassBinding.resetPassNew.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateNewPass() {
        String checkPassword = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (newPass.isEmpty()) {
            resetPassUsingCurrentPassBinding.resetPassNew.setError("Field can not be empty");
            return false;
        } else if (!newPass.matches(checkPassword)) {
            resetPassUsingCurrentPassBinding.resetPassNew.setError("Password should contain 6 characters!");
            return false;
        } else {
            resetPassUsingCurrentPassBinding.resetPassNew.setError(null);
            resetPassUsingCurrentPassBinding.resetPassNew.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateConPass() {
        if (!newPass.equals(conPass)) {
            resetPassUsingCurrentPassBinding.resetPassConfirm.setError("Can't match password");
            return false;
        } else {
            resetPassUsingCurrentPassBinding.resetPassConfirm.setError(null);
            resetPassUsingCurrentPassBinding.resetPassConfirm.setErrorEnabled(false);
            return true;
        }
    }
}