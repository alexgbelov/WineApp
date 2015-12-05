package com.example.belov.wineapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parse.MenuItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ViewMenuActivityAdapter extends ArrayAdapter<MenuItem> {

    // set up decimal formatter
    private final DecimalFormat formatter = new DecimalFormat("$#.00");

    public ViewMenuActivityAdapter(Context context, ArrayList<MenuItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final MenuItem itemInfo = getItem(position);

        if (itemInfo != null) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_menu_list, parent, false);
            }

            TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            ImageView itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

            Button orderButton = (Button) convertView.findViewById(R.id.orderButton);

            itemName.setText(itemInfo.getName());
            itemPrice.setText(formatter.format(itemInfo.getPrice()));
            //itemImage.setImageBitmap(itemInfo.itemPicture);

            orderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent order = new Intent(getContext(), OrderItemsActivity.class);
                    order.putExtra("itemId", itemInfo.getItemId());
                    order.putExtra("itemPrice", itemInfo.getPrice());

                    getContext().startActivity(order);

                }
            });
        }

        return convertView;
    }
}
