package com.app.codingtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends FragmentActivity {

    private static final String TAG = "SignupDialogFragment";
    private static final String PREFS_NAME = "UserAutheticationFile";
    private SignupActivity activity;
    private Context context;
    EditText name,password,confirmPassword;
    boolean userNameValid ;
    boolean passwordValid ;
    boolean confirmPasswordValid ;
    SharedPreferences settings;
    private Pattern pattern;
    private Matcher matcher;
    String userPassword;
    String confirm_Password;
    String userName;
    Button signupLoginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        String VALID_PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(VALID_PASSWORD_PATTERN);

        name = (EditText) findViewById(R.id.signup_username);
        password = (EditText) findViewById(R.id.signup_password);
        confirmPassword = (EditText) findViewById(R.id.signup_confirmPassword);
        signupLoginButton = (Button)findViewById(R.id.signup_login_button);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Button createAccount = (Button) findViewById(R.id.btn_signup);


        signupLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
        final SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = name.getText().toString();
                userNameValid = ifUsernameValid(userName);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userPassword = password.getText().toString();
                if(userPassword != null) matcher = pattern.matcher(userPassword);  ///checks if password is valid
                Log.i("PASSWORD", userPassword);
                passwordValid = ifPasswordValid(userPassword);
                Log.i("Password valid", String.valueOf(passwordValid));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirm_Password = confirmPassword.getText().toString();
                confirmPasswordValid = ifConfirmPasswordMatches(confirm_Password, userPassword);
                Log.i("Confirm password", String.valueOf(confirmPasswordValid));

            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
         @Override
             public void onClick(View v) {
             InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
             imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


             /// if all three fields have a valid value, save the details in the shared preference
                 if (userNameValid == true && passwordValid == true && confirmPasswordValid == true) {

                     SharedPreferences.Editor editor = preferences.edit();
                     editor.putString(userName,userPassword);
                     editor.commit();
                     Intent intent = new Intent(getApplication(),HomeActivity.class);   //////starts the home activity on valid credentials
                     startActivity(intent);

                 }
                 else{
                     ifUsernameValid(userName);
                     ifPasswordValid(userPassword);
                     ifConfirmPasswordMatches(confirm_Password,userPassword);
                     Toast.makeText(getApplicationContext(), "Check the errors",Toast.LENGTH_SHORT).show();
                 }
             }

         });
    }

    private boolean ifUsernameValid(String userName){
        if((userName.matches("\\d{10}") || android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches())){
            name.setError(null);
            return true;
        } else if (userName == null) {
            name.setError("Enter a username");
            return false;
        } else if (ifEmailExists(userName) == false) {
            name.setError("Username already exists");
            return false;
        } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(userName).matches()) || !userName.matches("\\d{10}")) {
            name.setError("Username should be email address or phone number");
            return false;
        }
        return true;
    }
    private boolean ifPasswordValid(String userPassword){
        final Drawable icon = getResources().getDrawable(R.drawable.ic_done);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

        if (userPassword == null) {
            password.setError("Password can't be empty");
            return false;
        } else if ((!matcher.matches())) {
            password.setError("Password should have at least 6 characters,1 lowercase, 1 uppercase,1 special character & 1 numeric");
            return false;

        } else {
            password.setError("Valid Password", icon);
            return true;
        }

    }

    private boolean ifConfirmPasswordMatches(String confirm_Password, String userPassword){
        final Drawable icon = getResources().getDrawable(R.drawable.ic_done);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

        if (userPassword !=  null && confirm_Password != null && confirm_Password.equals(userPassword)) {
            Log.i("PASSWORD3", userPassword);
            confirmPassword.setError("Matched", icon);
            return true;
        } else {
            confirmPassword.setError("Incorrect Password");
            return false;
        }
    }

    private boolean ifEmailExists(String email){
        final SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        final Map<String, ?> value = preferences.getAll();

        boolean valid = true;
        for (final Map.Entry<String, ?> entry : value.entrySet())
        {
            System.out.println("KEY SHARED PREF"+ entry.getKey());
            if (email.equals(entry.getKey().toString())) {
                valid = false;
                break;

            }
            else
            {
                continue;
            }
        }
        return valid;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}






