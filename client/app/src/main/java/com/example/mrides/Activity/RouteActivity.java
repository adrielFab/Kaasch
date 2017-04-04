package com.example.mrides.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.mrides.CustomList;
import com.example.mrides.R;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private String [] names = {"Adriel Fabella", "Ioan Cioca", "Harisson Andriamanantena", "An Ran Chen"};
    private Integer [] imageid = {R.drawable.photo, R.drawable.photo, R.drawable.photo, R.drawable.photo};
    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        button = (Button) findViewById(R.id.ratingButton);
        button.setOnClickListener(this);

        imageView = (ImageView) findViewById(R.id.imageTrash);
        imageView.setOnClickListener(this);

        CustomList customList = new CustomList(this, names, imageid);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.imageTrash:
                promptUserCancellation();
                break;
            default:
                backToHome();
                Toast.makeText(RouteActivity.this, "Ratings have been submitted", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void promptUserCancellation() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.delete_route)
            .setMessage(R.string.prompt_message)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    backToHome();
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

    public void backToHome() {
        Intent intent = new Intent(RouteActivity.this, HomePage.class);
        startActivity(intent);
    }

}
