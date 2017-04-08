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
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.CustomList;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener, ActivityObserver {

    private String route;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> photoURL = new ArrayList<>();
    private CustomList customList;
    private JSONArray ratingsJSON = new JSONArray();
    private String distance;
    private String duration;
    private final double GAS_PRICE = 1.20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        route = getIntent().getExtras().getString("nameOfRoute");

        Button button = (Button) findViewById(R.id.ratingButton);
        button.setOnClickListener(this);

        ImageView imageView = (ImageView) findViewById(R.id.imageTrash);
        imageView.setOnClickListener(this);
        TextView title = (TextView) findViewById(R.id.routeTitle);
        title.setText(route);

        retrievePassengers();

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
//                submitRating();
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
//    public void submitRating() {
//
//        HashMap ratingsOfUser = customList.getRatings();
//
//        if ( ratingsOfUser.size() == names.length) {
//            jsonConversion(ratingsOfUser);
//            deleteRoute();
//            Intent intent = new Intent(RouteActivity.this, HomePage.class);
//            startActivity(intent);
//            Toast.makeText(RouteActivity.this, "Ratings have been submitted" + route, Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(RouteActivity.this, "PLEASE RATE ALL USERS", Toast.LENGTH_SHORT).show();
//        }
//
//    }

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

    /**
     * Method that sends the email and the route name to the web server
     * to retrieve all the other members that are part of the route
     */
    public void retrievePassengers() {
        RequestHandler requestHandler =  new RequestHandler();
        requestHandler.attach(this);

        Map<String,String> jsonBody = new HashMap<>();
        jsonBody.put("loggedInUserEmail", RequestHandler.getUser().getEmail());
        jsonBody.put("routeName", route);
        jsonBody.put(User.ParameterKeys.EMAIL.toString(), RequestHandler.getUser().getEmail());
        requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                        "/getPassengersOnRoute.php",jsonBody,
                RequestHandler.URLENCODED ,this);
    }

    /**
     * The response received from the web server is a list of passengers and the metrics
     *
     * The format of the JSON response is that the LAST jsonObject contains the metrics
     * This implies that the first JSONObjects in the array are passengers, and the last
     * one which is (jsonArray.length - 1) will always be the metrics Object
     *
     * This format explains why the loop does not go through the last entry, and why
     * the last entry retrieves different keys from the jsonArray
     * @param response A string response formatted in a json string returned from the request handler
     */
    @Override
    public void Update(String response) {
        if (response == null) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length()-1; i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String firstName = jsonObject.getString("first_name");
                String lastName = jsonObject.getString("last_name");
                String name = firstName + " " + lastName;
                String photoUrl = jsonObject.getString("profile_picture");

                names.add(name);
                photoURL.add(photoUrl);
            }

            JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.length()-1);
            duration = jsonObject.getString("duration");
            distance = jsonObject.getString("distance");
            Log.i("VALUEE", duration.getClass().getName() + " and " + distance);

        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }

        populateTheList();
        displayMetrics();
    }

    /**
     * Method that displays the metrics of the given route
     * The metrics that are being displayed are duration (in minutes), distance (in km), and
     * price (in canadian dollars)
     *
     * The price is calculated by the distance multiplied by the gas price
     */
    public void displayMetrics() {
        TextView textViewDuration = (TextView) findViewById(R.id.textViewDurationValue);
        TextView textViewDistance = (TextView) findViewById(R.id.textViewDistanceValue);
        TextView textViewPrice = (TextView) findViewById(R.id.textViewPriceValue);

        textViewDistance.setText(distance);
        textViewDuration.setText(duration);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        double distanceValue = Double.parseDouble(distance);
        double doublePrice = distanceValue*GAS_PRICE;
        double roundOff = (double) Math.round(doublePrice * 100) / 100;
        String totalPrice = Double.toString(roundOff);
        textViewPrice.setText(totalPrice);
    }

    /**
     * This method populates the list view by displaying the people who are a part of that route
     * What will be shown for each user is their name and their profile picture, with the option
     * to rate the user
     */
    public void populateTheList() {
        customList = new CustomList(this, names, photoURL);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);
    }
}
