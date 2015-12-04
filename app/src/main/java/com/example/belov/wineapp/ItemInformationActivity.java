package com.example.belov.wineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.parse.MenuItem;
import com.example.parse.ParseHandler;

import java.util.ArrayList;

public class ItemInformationActivity extends AppCompatActivity {
    ParseHandler handler;
    ArrayList reviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        TextView name =  (TextView) findViewById(R.id.name);
        String itemName = getIntent().getStringExtra("itemName");
        name.setText(itemName);
        TextView description = (TextView) findViewById(R.id.description);
        String itemDescription = getIntent().getStringExtra("itemDescription");
        description.setText(itemDescription);

        Button addReviewButton = (Button) findViewById(R.id.addReviewButton);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), AddNewReview.class);
                intent.putExtra("itemId", getIntent().getStringExtra("itemId"));
                startActivity(intent);
            }
        });

        handler = ParseHandler.getParseHandler();
        reviews = handler.getItemReviews(getIntent().getStringExtra("itemId"));

        Button orderButton = (Button) findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), OrderItemsActivity.class);
                intent.putExtra("itemId", getIntent().getStringExtra("itemId"));
                intent.putExtra("itemPrice", getIntent().getDoubleExtra("itemPrice", 0));
                startActivity(intent);
            }
        });

    }


}
