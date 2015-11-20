package com.example.belov.wineapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by alexander on 11/17/2015.
 */
public class RegisterActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Intent intent = getIntent();
        EditText username = (EditText) findViewById(R.id.usernameEditText);
        EditText password = (EditText) findViewById(R.id.passwordEditText);
        EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
        EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
        String s = LoginActivity.USERNAME_TAG;

        username.setText(intent.getStringExtra(LoginActivity.USERNAME_TAG));
        password.setText(intent.getStringExtra(LoginActivity.PASSWORD_TAG));

        Button goBack = (Button) findViewById(R.id.goBackButton);
        Button registerButton = (Button) findViewById(R.id.registerButton);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
