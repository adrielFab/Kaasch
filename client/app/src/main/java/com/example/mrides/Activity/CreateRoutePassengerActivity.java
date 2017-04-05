package com.example.mrides.Activity;

import android.os.Bundle;
import android.widget.Toast;

public class CreateRoutePassengerActivity extends CreateRouteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(CreateRoutePassengerActivity.this, "You are a passenger", Toast.LENGTH_SHORT).show();

    }
}
