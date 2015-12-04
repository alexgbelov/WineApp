package com.example.belov.wineapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.example.parse.MenuItem;
import com.example.parse.ParseHandler;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewMenuActivity extends AppCompatActivity {

    private ArrayList<MenuItem> itemArrayList;
    private ViewMenuActivityAdapter mAdapter;

    private static final String PRODUCT = "product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        // grab menu items
        itemArrayList = ParseHandler.getParseHandler().getMenuItems("WineBar");

        //initialize adapter
        mAdapter = new ViewMenuActivityAdapter(this, itemArrayList);

        //get listview
        ListView listMenu = (ListView)findViewById(R.id.listView);

        //add listview to adapter
        listMenu.setAdapter(mAdapter);

        //start async task
        //new Stuff();

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                MenuItem clickedItem = mAdapter.getItem(position);

                // create intent with info needed for item information
                Intent goToInfo = new Intent(ViewMenuActivity.this, ItemInformationActivity.class);
                goToInfo.putExtra("itemId", clickedItem.getItemId());
                goToInfo.putExtra("itemName", clickedItem.getName());
                goToInfo.putExtra("itemPrice", clickedItem.getPrice());
                goToInfo.putExtra("itemDescription", clickedItem.getDescription());

                startActivity(goToInfo);
            }
        });
    }


    public class Stuff  {


        Stuff(){
            new HttpGetTask().execute();

        }

        private class HttpGetTask extends AsyncTask<Void, Void, MenuItem> {


            @Override
            protected MenuItem doInBackground(Void... params) {

                MenuItem x = new MenuItem(null, null, null, null, null);
                return x;
                /*
                try {

                    Item x = new Item();
                    return x;

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

                */


            }

            @Override
            protected void onPostExecute(MenuItem x) {
                super.onPostExecute(x);
                //set text and image fields
                mAdapter.add(x);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_menu, menu);
        return true;
    }
/*
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
    }*/
}
