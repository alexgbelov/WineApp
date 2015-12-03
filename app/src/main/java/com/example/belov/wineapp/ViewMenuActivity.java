package com.example.belov.wineapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;

public class ViewMenuActivity extends AppCompatActivity {

    private ArrayList<Item> itemArrayList = new ArrayList<Item>();
    private ViewMenuActivityAdapter mAdapter;

    private static final String PRODUCT = "product";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);

        Item temp = new Item();
        Item temp2 = new Item();

        temp.itemName = "beer";
        temp.itemPrice = "$4.00";
        temp2.itemName = "wine";
        temp2.itemPrice = "$7.00";

        itemArrayList.add(temp);
        itemArrayList.add(temp2);

        //initialize adapter
        mAdapter = new ViewMenuActivityAdapter(itemArrayList);

        //get listview
        ListView listMenu = (ListView)findViewById(R.id.listView);

        //add listview to adapter
        listMenu.setAdapter(mAdapter);

        //start async task
        new Stuff();

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Item clickItem = mAdapter.getItem(position);

                Toast.makeText(getBaseContext(), clickItem.itemName + " " + clickItem.itemPrice, Toast.LENGTH_LONG).show();

                //create intent and start activity.
                /*
                Intent choice = new Intent(getApplicationContext(), activity.class);

                choice.putExtra(PRODUCT, clickItem.itemName);
                startActivity(choice);
                */
            }
        });
    }


    public class Stuff  {


        Stuff(){
            new HttpGetTask().execute();

        }

        private class HttpGetTask extends AsyncTask<Void, Void, Item> {


            @Override
            protected Item doInBackground(Void... params) {

                Item x = new Item();
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
            protected void onPostExecute(Item x) {

                //set text and image fields
                mAdapter.itemArrayList.add(x);
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
