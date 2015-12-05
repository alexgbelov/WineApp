package com.example.belov.wineapp;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parse.ParseHandler;

public class AddNewReview extends AppCompatActivity{

    private Toolbar toolbar;

    Float starCount;
    ParseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_review);

        // get the toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // setup toolbar
        getSupportActionBar().setTitle("Adding New Review");

        Button submitButton = (Button) findViewById(R.id.submitButton);
        handler = ParseHandler.getParseHandler();
        starCount = 0F;
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((RatingBar) findViewById(R.id.ratingBar)).setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        starCount = rating;
                    }
                });

                String ratingText = ((EditText) findViewById(R.id.review)).getText().toString();


                handler.uploadUserReview(getIntent().getStringExtra("itemId"), ratingText, Math.round(starCount) );

                Toast toast = Toast.makeText(getApplicationContext(), "Review Submitted", Toast.LENGTH_SHORT);
                toast.show();
                finish();

            }
        });
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemInformationActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                ParseHandler.getParseHandler().logoutUser();
                Intent backToLogin = new Intent(this, LoginActivity.class);
                startActivity(backToLogin);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
