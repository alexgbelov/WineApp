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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parse.ParseHandler;

import java.io.FileInputStream;
import java.text.DecimalFormat;


public class OrderItemsActivity extends AppCompatActivity {

    private final static String GET_ITEM = "order";

    private ImageView itemImageView;
    private Button placeOrderButton;
    private TextView cancelButton;
    private EditText quantity;
    private TextView price;
    private final DecimalFormat formatter = new DecimalFormat("$#.00");
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        // get the toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // setup toolbar
        getSupportActionBar().setTitle("Placing An Order");

        //buttons
        placeOrderButton = (Button) findViewById(R.id.placeOrder);
        cancelButton = (TextView) findViewById(R.id.cancel);

        // image view of item
        itemImageView = (ImageView) findViewById(R.id.mainImg);

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

        //edit text
        quantity = (EditText) findViewById(R.id.quantityInput);
        price = (EditText) findViewById(R.id.priceInput);

        // get item's price from intent
        final Double itemPrice = getIntent().getDoubleExtra("itemPrice", 0);

        // set default values for text areas
        quantity.setText("1");
        price.setText(formatter.format(itemPrice));

        //changes price automatically after user updates quantity
        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    String amount = quantity.getText().toString();
                    Double totalDouble = Integer.parseInt(amount) * itemPrice;

                    price.setText(formatter.format(totalDouble));
                }

            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // place order by saving information to Parse
                String itemId = getIntent().getStringExtra("itemId");
                boolean orderPlaced = ParseHandler.getParseHandler().orderItem("WineBar", itemId, Integer.parseInt(quantity.getText().toString()));

                // inform user of success
                if (orderPlaced)
                    Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Order could not be placed! Database connection failed.", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

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