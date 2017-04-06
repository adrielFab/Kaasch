package com.example.mrides;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.HashMap;

public class CustomList extends ArrayAdapter {

    private String[] names;
    private Integer[] imageid;
    private Activity context;
    private RatingBar ratingBar;
    private HashMap<String, Float> ratingOfUser = new HashMap<>();

    public CustomList(Activity context, String[] names, Integer[] imageid) {
        super(context, R.layout.list_layout, names);
        this.context = context;
        this.names = names;
        this.imageid = imageid;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        ratingBar = (RatingBar) listViewItem.findViewById(R.id.ratingBar);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names[position]);
        image.setImageResource(imageid[position]);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                ratingOfUser.put(names[position], value);
                System.out.println(value + " " + names[position] );
            }
        });

        return listViewItem;
    }

    public HashMap getRatings() {
        return this.ratingOfUser;
    }

}
