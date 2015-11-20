package com.example.belov.wineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parse.ParseHandler;

public class LoginActivity extends AppCompatActivity {

    private String mUserName;
    private String mPassword;

    final static String USERNAME_TAG = "USERNAME";
    final static String PASSWORD_TAG = "PASSWORD";

    private ParseHandler mParseHander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final Button registerButton = (Button) findViewById(R.id.registerButton);

        mParseHander = new ParseHandler(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = usernameEditText.getText().toString();
                mPassword = passwordEditText.getText().toString();
                if (mUserName.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_LONG).show();
                else if (mPassword.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter a password", Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent.putExtra(USERNAME_TAG, mUserName);
                    intent.putExtra(PASSWORD_TAG, mPassword);
                    startActivity(intent);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUserName = usernameEditText.getText().toString();
                mPassword = passwordEditText.getText().toString();
                if (mUserName.equals(""))
                    Toast.makeText(getApplicationContext(), getString(R.string.enterUsername), Toast.LENGTH_LONG).show();
                else if (mPassword.equals(""))
                    Toast.makeText(getApplicationContext(), getString(R.string.enterPassword), Toast.LENGTH_LONG).show();
                else if (mParseHander.loginUser(mUserName, mPassword)) {
                    //user information is correct
                    //go to menu activity
                } else {
                    //user information is not correct
                    Toast.makeText(getApplicationContext(), getString(R.string.failedLogin), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
