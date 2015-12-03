package com.example.belov.wineapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewMenuActivityAdapter extends BaseAdapter {

    public ArrayList<Item> itemArrayList;

    ViewMenuActivityAdapter(ArrayList<Item> input){

        this.itemArrayList = input;
    }

    @Override
    public int getCount() {

        return itemArrayList.size();
    }

    @Override
    public Item getItem(int num) {

        return itemArrayList.get(num);
    }

    @Override
    public long getItemId(int num) {

        return num;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context mContext = parent.getContext();

        if(convertView==null){

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_menu_list, parent,false);
        }

        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
        ImageView itemImage = (ImageView) convertView.findViewById(R.id.itemImage);

        Button orderButton = (Button) convertView.findViewById(R.id.orderButton);


        Item itemInfo = itemArrayList.get(position);

        itemName.setText(itemInfo.itemName);
        itemPrice.setText(itemInfo.itemPrice);
        //itemImage.setImageBitmap(itemInfo.itemPicture);

        orderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent order = new Intent(mContext, OrderItemsActivity.class);
                mContext.startActivity(order);

            }
        });

        return convertView;
    }
}
