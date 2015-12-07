package com.example.belov.wineapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parse.OrderedItem;
import com.example.parse.UserReview;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.util.ArrayList;

public class OrderedItemsAdapter extends ArrayAdapter<OrderedItem> {

    public OrderedItemsAdapter(Context context, ArrayList<OrderedItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final OrderedItem orderedItem = getItem(position);

        if (orderedItem != null) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordered_item, parent, false);
            }

            // get UI elements
            TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
            TextView orderQuantity = (TextView) convertView.findViewById(R.id.order_quantity);
            TextView orderTotal = (TextView) convertView.findViewById(R.id.order_total);
            TextView orderTime = (TextView) convertView.findViewById(R.id.order_placed_at);
            ImageView itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

            // set UI element values
            itemName.setText(orderedItem.getMenuItem().getName());
            orderQuantity.setText("Quantity: " + String.valueOf(orderedItem.getQuantity()));
            orderTotal.setText("Total Price: " + String.valueOf(orderedItem.getTotalPrice()));
            orderTime.setText(orderedItem.getTimeOrdered().toString());
            itemImage.setImageBitmap(orderedItem.getMenuItem().getItemImage());
        }

        return convertView;
    }
}
