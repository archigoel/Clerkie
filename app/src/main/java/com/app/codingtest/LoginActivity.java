package com.app.codingtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

public class LoginActivity extends Activity {

    Button loginButton,registerButton;
    EditText loginUsername,loginPassword;
    public SignupActivity signupActivity;
    SharedPreferences sharedPreferences;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("MY PREFS", Context.MODE_PRIVATE);

        if(sharedPreferences.getString("USERNAME",username) != null){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

        loginButton = (Button) findViewById(R.id.login_button);
        loginUsername= (EditText) findViewById(R.id.login_username);
        loginPassword = (EditText) findViewById(R.id.login_password);
        registerButton = (Button) findViewById(R.id.register_button);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                 attemptLogin();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptLogin() {

        // Store values at the time of the login attempt.
        String username = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);


        if (TextUtils.isEmpty(username)) {
            loginUsername.setError(getString(R.string.error_field_required));

        }
        if(TextUtils.isEmpty(password)) {
            loginPassword.setError(getString(R.string.error_field_required));
        }


        SharedPreferences preferences = getSharedPreferences("pref", Context.MODE_PRIVATE);

        Map<String, ?> value = preferences.getAll();
        boolean valid = false;

        for (final java.util.Map.Entry<String, ?> entry : value.entrySet())
        {

            if (username.equals(entry.getKey().toString())) {      // Check for a valid Username
                if(password.equals(entry.getValue().toString())){   // Check for a valid password, if the user entered one.
                    valid = true;

                    SharedPreferences sharedPreferences = getSharedPreferences("MY PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("USERNAME", username);
                    editor.commit();
                    startHomeActivity();
                }
                else{
                    loginPassword.setError("Incorrect Password");
                }
            }
        }

       if(valid == false)
            Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();

    }

        private void startHomeActivity() {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }
