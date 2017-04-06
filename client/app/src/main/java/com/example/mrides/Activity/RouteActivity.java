package com.example.mrides.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mrides.CustomList;
import com.example.mrides.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;

    //Get all the information of the route
    private String [] names = {"Adriel Fabella", "Ioan Cioca", "Harisson Andriamanantena", "An Ran Chen"};
    private Integer [] imageid = {R.drawable.photo, R.drawable.photo, R.drawable.photo, R.drawable.photo};
    private Button button;
    private ImageView imageView;
    private CustomList customList;
    private JSONArray ratingsJSON = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        button = (Button) findViewById(R.id.ratingButton);
        button.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageTrash);
        imageView.setOnClickListener(this);

        customList = new CustomList(this, names, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);

    }

    /**
     * This onClick method handles all the clicks from :
     * - trash icon
     * - submit rating button
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.imageTrash:
                promptUserCancellation();
                break;
            default:
                submitRating();
                break;
        }

    }

    /**
     * Method that prompts the user if the user is certain to delete the route
     */
    public void promptUserCancellation() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_route)
            .setMessage(R.string.prompt_message)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deleteRoute();
                    Toast.makeText(RouteActivity.this, R.string.delete_confirm, Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    /**
     * Submits the rating that the user assigned to each member in the route
     * If the user did not provide a rating, the application will prompt the user
     * to assign a rating to all members before submission
     */
    public void submitRating() {

        HashMap ratingsOfUser = customList.getRatings();

        if ( ratingsOfUser.size() == names.length) {
            jsonConversion(ratingsOfUser);
            Intent intent = new Intent(RouteActivity.this, HomePage.class);
            startActivity(intent);
            Toast.makeText(RouteActivity.this, "Ratings have been submitted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(RouteActivity.this, "PLEASE RATE ALL USERS", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Method that allows the user to delete the current route
     */
    public void deleteRoute() {
        Intent intent = new Intent(RouteActivity.this, HomePage.class);
        startActivity(intent);
    }

    /**
     * Method that converts the data to JSON format which is readable
     * by the web server
     */
    public void jsonConversion(HashMap<String, Float> hashMap) {

        for (Map.Entry<String, Float> entry : hashMap.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
                ratingsJSON.put(jsonObject);
            } catch (JSONException e) {
                Log.e("RouteActivity error: ", e.toString());
            }
        }
    }

}
