package com.example.belov.wineapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class OrderItemsActivity extends AppCompatActivity {

    private final static String GET_ITEM = "order";

    private Button placeOrderButton;
    private Button cancelButton;
    private EditText quantity;
    private EditText price;

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

        quantity.setText("1");


        //changes price automatically after user updates quantity
        quantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {


                if(!hasFocus) {

                    String amount = quantity.getText().toString();
                    Double totalDouble = Double.parseDouble(amount) * 4.3;




                    price.setText("$" + totalDouble.toString());
                }

            }
        });



        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
                //create intent and start activity.
                /*
                Intent choice = new Intent(getApplicationContext(), activity.class);

                choice.putExtra(PRODUCT, clickItem.itemName);
                startActivity(choice);
                */

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