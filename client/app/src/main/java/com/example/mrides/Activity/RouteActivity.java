package com.example.mrides.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.mrides.CustomList;
import com.example.mrides.R;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private String [] names = {"Adriel Fabella", "Ioan Cioca", "Harisson Andriamanantena", "An Ran Chen"};
    private Integer [] imageid = {R.drawable.photo, R.drawable.photo, R.drawable.photo, R.drawable.photo};
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        button = (Button) findViewById(R.id.ratingButton);
        button.setOnClickListener(this);
        CustomList customList = new CustomList(this, names, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(RouteActivity.this, HomePage.class);
        startActivity(intent);
        Toast.makeText(RouteActivity.this, "Ratings have been submitted", Toast.LENGTH_SHORT).show();
    }
}
