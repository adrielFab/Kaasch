package com.example.mrides.Activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.mrides.R;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private Typeface tf1;
    private String [] matchedRoutes = {"Habs game", "Work at Ericsson", "mountain trip"};
    private String [] unmatchedRoutes = {"Party", "Trip to CN", "Engineering workshop", "another one"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        tf1 = Typeface.createFromAsset(getAssets(), "Ubuntu-L.ttf");

        createMatchedRoutes();
        createUnmatchedRoutes();
    }

    @Override
    public void onClick(View view) {

    }

    public void createMatchedRoutes() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollMatch);

        for (int i = 0; i < matchedRoutes.length; i++) {
            Button route = new Button(this);
            LinearLayout.LayoutParams params = styleButton(route);
            route.setText(matchedRoutes[i]);
            linearLayout.addView(route, params);

        }
    }

    public void createUnmatchedRoutes() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layoutScrollUnmatch);

        for (int i = 0; i < unmatchedRoutes.length; i++) {
            Button route = new Button(this);
            LinearLayout.LayoutParams params = styleButton(route);
            route.setText(unmatchedRoutes[i]);
            linearLayout.addView(route, params);

        }
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
