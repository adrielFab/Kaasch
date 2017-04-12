package com.example.mrides;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomList extends ArrayAdapter {

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> photoURL = new ArrayList<>();
    private ArrayList<String> emails = new ArrayList<>();
    private Activity context;
    private HashMap<String, String> ratingOfUser = new HashMap<>();

    public CustomList(Activity context, ArrayList<String> names, ArrayList<String> photoURL, ArrayList<String> emails) {
        super(context, R.layout.list_layout, names);
        this.context = context;
        this.names = names;
        this.photoURL = photoURL;
        this.emails = emails;
    }

    /**
     * This will create the list item for each user in the list of users on that route
     * What will be displayed in this view is the name of the user, their profile picture,
     * and a rating bar where the user will be able to rate the user
     * @param position The index of that passenger in the list
     * @param convertView A View which will convert the data into a view
     * @param parent Calls the view group, also known as the list
     * @return view. The view corresponds to the list_layout of a passenger
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        RatingBar ratingBar = (RatingBar) listViewItem.findViewById(R.id.ratingBar);
        ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names.get(position));
        ImageConverter imageConverter = new ImageConverter(imageView);
        imageConverter.execute(photoURL.get(position));

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float value, boolean b) {
                String valueString = Float.toString(value);
                ratingOfUser.put(emails.get(position), valueString);
            }
        });

        return listViewItem;
    }

    /**
     * Returns the hashMapge ratingsOfUser which contains the ratings that the user
     * has assigned to the other users shown
     * @return
     */
    public HashMap getRatings() {
        return this.ratingOfUser;
    }

}
