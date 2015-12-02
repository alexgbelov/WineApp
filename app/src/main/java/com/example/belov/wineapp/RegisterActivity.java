package com.example.belov.wineapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parse.ParseHandler;

/**
 * Created by alexander on 11/17/2015.
 */
public class RegisterActivity extends Activity {

    private ParseHandler mParseHandler;
    private String mUserName, mPassword, mFirstName, mLastName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        mParseHandler = new ParseHandler(this);
        Intent intent = getIntent();
        final EditText username = (EditText) findViewById(R.id.usernameEditText);
        final EditText password = (EditText) findViewById(R.id.passwordEditText);
        final EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
        final EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
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
                mUserName = username.getText().toString();
                mPassword = password.getText().toString();
                mFirstName = firstName.getText().toString();
                mLastName = lastName.getText().toString();
                if (mParseHandler.registerUser(mFirstName, mLastName, mUserName, mPassword)) {
                    //go to menu
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.failedRegister), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
