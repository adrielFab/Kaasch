package com.example.mrides;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DirectionModel.ObtainDirection;
import DirectionModel.ObtainDirectionListener;
import DirectionModel.PopulateMap;
import DirectionModel.Route;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback, ObtainDirectionListener {

    private Button mButtonFindPath;
    private EditText mEditTextStart;
    private EditText mEditTextDestination;
    private GoogleMap mGoogleMap;
    private ProgressDialog mProgressDialog;
    private List<Marker> startMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //requestLocationUpdates demands an explicit permission check
                if(ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mButtonFindPath = (Button) findViewById(R.id.buttonFindPath);
        mEditTextStart = (EditText) findViewById(R.id.editTextStart);
        mEditTextDestination = (EditText) findViewById(R.id.editTextDestination);

        mButtonFindPath.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createPath();
            }
        });


    }

    public void createPath(){
        String start = mEditTextStart.getText().toString();
        String destination = mEditTextDestination.getText().toString();
        if(start.isEmpty()){
            Toast.makeText(this, "Please enter a starting address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter the destination", Toast.LENGTH_SHORT).show();
            return;
        }

        try{
            new ObtainDirection(this, start, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if(Build.VERSION.SDK_INT < 23){

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else{

            if(ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                LatLng myLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mGoogleMap.clear();

                mGoogleMap.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
            }
        }

        PopulateMap populateMap = new PopulateMap(this);
        populateMap.execute();
    }

    public void populateGoogleMap(HashMap<String, LatLng> hashMap){
        HashMap<String, LatLng> hashUsers = hashMap;
        String result = "";

        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.passenger_icon);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        for(String key: hashUsers.keySet()){
            LatLng location = hashUsers.get(key);
            mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .title(key)
                    .position(location));
        }
    }


    @Override
    public void startObtainDirection() {
        mProgressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction...", true);

        if (startMarkers != null) {
            for (Marker marker : startMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void successObtainDirection(List<Route> routes) {
        mProgressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        startMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.getStartLocation(), 16));
            ((TextView) findViewById(R.id.textDuration)).setText(route.getDuration().getText());
            ((TextView) findViewById(R.id.textDistance)).setText(route.getDistance().getText());

            startMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .title(route.getStartAddress())
                    .position(route.getStartLocation())));

            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                    .title(route.getEndAddress())
                    .position(route.getEndLocation())));

            PolylineOptions polylineOptions = new PolylineOptions(). //Route
                    geodesic(true).
                    color(Color.CYAN).
                    width(10);

            for (int i = 0; i < route.getPoints().size(); i++)
                polylineOptions.add(route.getPoints().get(i));

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }
}