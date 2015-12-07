package com.example.belov.wineapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.parse.ParseHandler;
import com.example.parse.UserReview;

import java.io.FileInputStream;
import java.util.ArrayList;

public class ItemInformationActivity extends AppCompatActivity {
    ParseHandler handler;
    ArrayList reviews;
    private ArrayList<UserReview> reviewArrayList;
    private ItemInformationActivityAdapter mAdapter;
    private Toolbar toolbar;
    private ImageView itemImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_information);

        // get the toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // setup toolbar
        getSupportActionBar().setTitle("Item Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // grab user reviews
        reviewArrayList = ParseHandler.getParseHandler().getUserReviews(getIntent().getStringExtra("itemId"));

        //initialize adapter
        mAdapter = new ItemInformationActivityAdapter(this, reviewArrayList);

        //get listview
        ListView listMenu = (ListView)findViewById(R.id.listView);

        //add listview to adapter
        listMenu.setAdapter(mAdapter);
        // image view of item
        itemImageView = (ImageView) findViewById(R.id.itemImage);
        // get image and set it on the image view
        String imageFileName = getIntent().getStringExtra("imageFileName");
        if (imageFileName != null) {
            try {

                FileInputStream fileInputStream = this.openFileInput(imageFileName);
                itemImageView.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d("DEBUG", "Image file name is null!");
        }

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
                intent.putExtra("itemDescription", getIntent().getStringExtra("itemDescription"));
                intent.putExtra("itemName", getIntent().getStringExtra("itemName"));
                intent.putExtra("itemPrice", getIntent().getDoubleExtra("itemPrice", 0));
                intent.putExtra("imageFileName", getIntent().getStringExtra("imageFileName"));
                startActivity(intent);
                finish();
            }
        });

        handler = ParseHandler.getParseHandler();
        reviews = handler.getUserReviews(getIntent().getStringExtra("itemId"));

        Button orderButton = (Button) findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), OrderItemsActivity.class);
                intent.putExtra("itemId", getIntent().getStringExtra("itemId"));
                intent.putExtra("itemPrice", getIntent().getDoubleExtra("itemPrice", 0));
                intent.putExtra("imageFileName", getIntent().getStringExtra("imageFileName"));
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_view_orders:
                Intent viewOrders = new Intent(this, ViewMyOrders.class);
                viewOrders.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(viewOrders);
                return true;
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
