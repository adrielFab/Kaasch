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
import android.widget.Toast;

import com.example.mrides.R;


public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private Typeface tf1;
    private String [] matchedRoutes = {"Habs game", "Work at Ericsson", "mountain trip"};
    private String [] unmatchedRoutes = {"Party", "Trip to CN", "Engineering workshop", "another one"};

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_home_page);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navvy);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_account:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                }
                return true;
            }
        });


        tf1 = Typeface.createFromAsset(getAssets(), "Ubuntu-L.ttf");

        createMatchedRoutes();
        createUnmatchedRoutes();


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    public void createMatchedRoutes() {
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollMatch);
//
//        for (int i = 0; i < matchedRoutes.length; i++) {
//            Button route = new Button(this);
//            LinearLayout.LayoutParams params = styleButton(route);
//            route.setText(matchedRoutes[i]);
//            linearLayout.addView(route, params);
//
//        }
    }

    public void createUnmatchedRoutes() {
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollUnmatch);
//
//        for (int i = 0; i < unmatchedRoutes.length; i++) {
//            Button route = new Button(this);
//            LinearLayout.LayoutParams params = styleButton(route);
//            route.setText(unmatchedRoutes[i]);
//            linearLayout.addView(route, params);
//
//        }
    }

    public LinearLayout.LayoutParams styleButton(Button button){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);
        button.setLayoutParams(params);
        button.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
        return params;
    }
}
