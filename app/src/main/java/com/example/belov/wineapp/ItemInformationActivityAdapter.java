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
import com.example.parse.UserReview;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemInformationActivityAdapter extends ArrayAdapter<UserReview>{



    public ItemInformationActivityAdapter(Context context, ArrayList<UserReview> reviews) {
        super(context, 0, reviews);
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final UserReview userReview = getItem(position);

        if (userReview != null) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review_list, parent, false);
            }

            TextView numStars = (TextView) convertView.findViewById(R.id.numStars);
            TextView reviewContent = (TextView) convertView.findViewById(R.id.reviewContent);

            Log.d("DEBUG", "Position of view: " + position);

            // determine if you need to display a decimal value or not
            if (userReview.getNumberOfStars() == Math.round(userReview.getNumberOfStars()))
                numStars.setText(String.valueOf(Math.round(userReview.getNumberOfStars())) + " stars");
            else
                numStars.setText(String.valueOf(userReview.getNumberOfStars()) + " stars");

            reviewContent.setText(userReview.getReview());
        }

        return convertView;
    }
}
