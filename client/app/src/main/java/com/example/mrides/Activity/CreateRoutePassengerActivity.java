package com.example.mrides.Activity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mrides.R;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import DirectionModel.Route;

public class CreateRoutePassengerActivity extends CreateRouteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(CreateRoutePassengerActivity.this, "You are a passenger", Toast.LENGTH_SHORT).show();

    }
}
