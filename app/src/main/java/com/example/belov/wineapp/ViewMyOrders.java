package com.example.belov.wineapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ListView;

import com.example.parse.MenuItem;
import com.example.parse.OrderedItem;
import com.example.parse.ParseHandler;

import java.util.ArrayList;

public class ViewMyOrders extends AppCompatActivity {

    private ArrayList<OrderedItem> currentOrders, pastOrders;
    private OrderedItemsAdapter currentOrdersAdapter, pastOrdersAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        // get the toolbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // setup toolbar
        getSupportActionBar().setTitle("My Ordered Items");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get all ordered items
        constructOrdersLists();

        // initialize adapters
        currentOrdersAdapter = new OrderedItemsAdapter(this, currentOrders);
        pastOrdersAdapter = new OrderedItemsAdapter(this, pastOrders);

        // set list view adapters
        ListView currentOrdersView = (ListView) findViewById(R.id.current_order_list_view);
        ListView pastOrdersView = (ListView) findViewById(R.id.past_order_list_view);
        currentOrdersView.setAdapter(currentOrdersAdapter);
        pastOrdersView.setAdapter(pastOrdersAdapter);
    }

    /**
     * Helper method to get all current orders and past orders.
     */
    private void constructOrdersLists() {
        currentOrders = new ArrayList<OrderedItem>();
        pastOrders = new ArrayList<OrderedItem>();

        for (OrderedItem item : ParseHandler.getParseHandler().getOrderedItems()) {
            if (!item.getIsFulfilled())
                currentOrders.add(item);
            else
                pastOrders.add(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
