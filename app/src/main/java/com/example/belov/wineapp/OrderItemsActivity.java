package com.example.belov.wineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parse.ParseHandler;

import java.text.DecimalFormat;


public class OrderItemsActivity extends AppCompatActivity {

    private final static String GET_ITEM = "order";

    private Button placeOrderButton;
    private Button cancelButton;
    private EditText quantity;
    private EditText price;
    private final DecimalFormat formatter = new DecimalFormat("$#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        //buttons
        placeOrderButton = (Button) findViewById(R.id.placeOrder);
        cancelButton = (Button) findViewById(R.id.cancel);

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
                    Toast.makeText(getApplicationContext(), "Order Could Not Be Placed!", Toast.LENGTH_SHORT).show();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_items, menu);
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