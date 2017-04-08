/*
* Homepage.java
*
* March 13, 2017
*
*/
package com.example.mrides.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.ImageConverter;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DirectionModel.Route;



public class HomePage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,ResultCallback<Status>,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ActivityObserver {

    private ActionBarDrawerToggle toggle;
    private GoogleApiClient mGoogleApiClient;
    private ArrayList<Route> routeList = new ArrayList<>();
    private HashMap<String, Button> hashRouteButton = new HashMap<>();

    /**
     * Method that creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_home_page);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        String firstName = RequestHandler.getUser().getFirstName();
        String lastName = RequestHandler.getUser().getLastName();
        String username = firstName + " " + lastName;
        String email = RequestHandler.getUser().getEmail();
        String photoUrl = RequestHandler.getUser().getPhotoUrl();
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView = (ImageView)headerView.findViewById(R.id.profile_image);
        TextView textViewNameNav = (TextView)headerView.findViewById(R.id.username);
        TextView textViewEmailNav = (TextView)headerView.findViewById(R.id.email);

        ImageConverter imageConverter = new ImageConverter(imageView);
        imageConverter.execute(photoUrl);

        textViewEmailNav.setText(email);
        textViewNameNav.setText(username);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "Ubuntu-L.ttf");
        TextView textViewMatch = (TextView) findViewById(R.id.textViewMatch);
        textViewMatch.setTypeface(tf1);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , (GoogleApiClient.OnConnectionFailedListener) this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        retrieveRoutes();
    }

    /**
     * Method moves to the first preference page (PreferencePageActivity)
     * @param view
     */
    public void goToPreferencePage(View view) {
        Intent intent = new Intent(HomePage.this, PreferencePageActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the navigation drawer to view the navigation list
     * @param item
     * @return boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Displays the active routes of the user
     */
    public void createRoutes() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScroll);
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setMargins(0,15,0,15);

        if (!routeList.isEmpty()) {
            for (int i = 0; i < routeList.size(); i++) {
                boolean type = false;
                String status =routeList.get(i).getRouteStatus();
                Button button = createRouteButton(routeList.get(i).getTitle(), type, status);
                hashRouteButton.put(routeList.get(i).getTitle(), button);
                button.setOnClickListener(this);
                linearLayout.addView(button, ll);
            }
        } 

    }

    /**
     * Creates and returns a button displaying route name, type and status
     * @param name
     * @param isDriver
     * @param status
     * @return Button
     */
    public Button createRouteButton(String name, Boolean isDriver, String status) {
        Button button = new Button(this);
        button.setBackground(getRouteDrawable(status));
        button.setText(name);
        button.setTextSize(15);
        button = setImage(button, isDriver);
        button.setTypeface(null, Typeface.BOLD);
        return button;
    }

    /**
     * Resizes given drawable
     * @param image
     * @return Drawable
     */
    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 210, 130, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    /**
     * Sets appropriate image on the left side of the button
     * @param button
     * @param isDriver
     * @return Button
     */
    private Button setImage(Button button, boolean isDriver) {
        if(isDriver) {
            Drawable driverImg = getResources().getDrawable(R.drawable.wheel);
            driverImg = resize(driverImg);
            button.setCompoundDrawablesWithIntrinsicBounds(driverImg, null, null, null);
        }
        else {
            Drawable passengerImg = getResources().getDrawable(R.drawable.seat);
            passengerImg = resize(passengerImg);
            button.setCompoundDrawablesWithIntrinsicBounds(passengerImg, null, null, null);
        }
        return button;
    }

    /**
     * Returns appropriate drawable for the route
     * @param status
     * @return drawable
     */
    private Drawable getRouteDrawable(String status) {
        Drawable drawable;
        switch(status){
            case "CREATED":
                drawable = getResources().getDrawable(R.drawable.unmatched_route_button);
                break;
            case "MATCHED":
                drawable = getResources().getDrawable(R.drawable.matched_route_button);
                break;
            case "PENDING":
                drawable = getResources().getDrawable(R.drawable.pending_route_button);
                break;
            default:
                drawable = getResources().getDrawable(R.drawable.unmatched_route_button);
                break;
        }
        return drawable;
    }

    /**
     * Method with a switch case that performs the corresponding intent depending on the
     * item selected from the nav
     * @param item
     * @return boolean
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_inbox:
                Intent intentInbox = new Intent(getApplicationContext(), InboxActivity.class);
                startActivity(intentInbox);
                break;
            case R.id.nav_settings:
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.nav_logout:
                Toast.makeText(this, R.string.loggedOut , Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(this);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     *
     * @param status
     */
    @Override
    public void onResult(@NonNull Status status) {
        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This onClick handles all the clicks incoming from the route buttons
     * @param view
     */
    @Override
    public void onClick(View view) {

        for (HashMap.Entry<String, Button> entry : hashRouteButton.entrySet()) {
            if (entry.getValue() == view) {
                Intent intent = new Intent(HomePage.this, RouteActivity.class);
                intent.putExtra("nameOfRoute", entry.getKey());
                startActivity(intent);
            }
        }

    }

    /**
     * Method that retrieves all the routes of the user
     */
    public void retrieveRoutes() {
        RequestHandler requestHandler =  new RequestHandler();
        requestHandler.attach(this);

        Map<String,String> jsonBody = new HashMap<>();
        jsonBody.put(User.ParameterKeys.EMAIL.toString(), RequestHandler.getUser().getEmail());
        requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                        "/retreive_routes.php",jsonBody,
                RequestHandler.URLENCODED ,this);
    }

    @Override
    public void Update(String response) {

        Log.i("BOMB", response);

        if (response == null) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                Route route = new Route();

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String userType = jsonObject.getString("user_type");
                String title = jsonObject.getString("route_name");
                String routeStatus = jsonObject.getString("route_status");

                route.setUserType(userType);
                route.setTitle(title);
                route.setRouteStatus(routeStatus);

                routeList.add(route);

            }
        } catch (JSONException e) {
            Log.e("Error: ", e.toString());
        }

        createRoutes();
    }
}