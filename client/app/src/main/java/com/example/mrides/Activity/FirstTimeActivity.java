package com.example.mrides.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mrides.R;

public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        ImageView car = (ImageView) findViewById(R.id.imageViewCar);
        car.animate().translationXBy(-3000f).setDuration(4000);
    }
}
