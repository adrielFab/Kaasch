package com.example.mrides.Activity;

import android.os.Bundle;
import android.widget.Toast;

public class CreateRouteDriverActivity extends CreateRouteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(CreateRouteDriverActivity.this, "You are a driver", Toast.LENGTH_SHORT).show();
    }
}
