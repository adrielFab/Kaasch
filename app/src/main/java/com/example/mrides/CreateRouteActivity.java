package com.example.mrides;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private Button btnFindPath;
    private EditText etStart;
    private EditText etDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etStart = (EditText) findViewById(R.id.etStart);
        etDestination = (EditText) findViewById(R.id.etDestination);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
