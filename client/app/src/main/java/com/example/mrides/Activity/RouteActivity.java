package com.example.mrides.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mrides.CustomList;
import com.example.mrides.R;

public class RouteActivity extends AppCompatActivity {

    ListView listView;
    String [] names = {"Adriel Fabella", "Ioan Cioca", "Harisson Andriamanantena", "An Ran Chen"};
    String [] desc = {"The best", "The worst", "Ehh", "Shwing"};
    Integer [] imageid = {R.drawable.photo, R.drawable.photo, R.drawable.photo, R.drawable.photo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        CustomList customList = new CustomList(this, names, desc, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);

    }
}
