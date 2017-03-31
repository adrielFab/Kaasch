/*
* Homepage.java
*
* March 13, 2017
*
*/
package com.example.mrides.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;



public class HomePage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,ResultCallback<Status>,
        GoogleApiClient.OnConnectionFailedListener{

    private Typeface tf1;
    private TextView textViewMatch;
    private TextView textViewUnmatch;
    private String [] matchedRoutes = {"Habs game", "Work at Ericsson", "mountain trip"};
    private String [] unmatchedRoutes = {"Party", "Trip to CN", "Engineering workshop", "another one"};

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private GoogleApiClient mGoogleApiClient;

    /**
     * Method that creates the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_home_page);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tf1 = Typeface.createFromAsset(getAssets(), "Ubuntu-L.ttf");
        textViewMatch = (TextView) findViewById(R.id.textViewMatch);
        textViewMatch.setTypeface(tf1);
        textViewUnmatch = (TextView) findViewById(R.id.textViewUnmatch);
        textViewUnmatch.setTypeface(tf1);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , (GoogleApiClient.OnConnectionFailedListener) this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        createMatchedRoutes();
        createUnmatchedRoutes();


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
     * Displays the matched routes of the user
     */
    public void createMatchedRoutes() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollMatch);

        for (int i = 0; i < matchedRoutes.length; i++) {
            Button route = new Button(this);
            LinearLayout.LayoutParams params = styleButton(route);
            route.setText(matchedRoutes[i]);
            linearLayout.addView(route, params);

        }
    }

    /**
     * Displays the unmatched routes of the user
     */
    public void createUnmatchedRoutes() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollUnmatch);

        for (int i = 0; i < unmatchedRoutes.length; i++) {
            Button route = new Button(this);
            LinearLayout.LayoutParams params = styleButton(route);
            route.setText(unmatchedRoutes[i]);
            linearLayout.addView(route, params);

        }
    }

    /**
     * Styles each route to be displayed on the Homepage Activity
     * @param button
     * @return params
     */
    public LinearLayout.LayoutParams styleButton(Button button) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);
        button.setLayoutParams(params);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        return params;
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
            case R.id.nav_account:
                //Go to account
                break;
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
        }
        return true;
    }

    @Override
    public void onResult(@NonNull Status status) {

        Intent intent = new Intent(HomePage.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
