/*
* Class CreateRouteActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.Domain.User;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
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

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import DirectionModel.PopulateMap;
import DirectionModel.Route;
import DirectionModel.RouteDeserializer;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback, ActivityObserver {

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
    private RequestHandler requestHandler = new RequestHandler();
    private PopulateMap populateMap = new PopulateMap(this);


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
        if(start.isEmpty()) {

            Toast.makeText(this, "Please enter a starting address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {

            Toast.makeText(this, "Please enter the destination", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = "";
        try {

            String urlOrigin = URLEncoder.encode(start, "utf-8");
            String urlDestination = URLEncoder.encode(destination, "utf-8");
            url = getString(R.string.direction_url_api) + "origin=" + urlOrigin + "&destination=" +
                    urlDestination + "&key=" + getString(R.string.google_maps_api_key);
        }
        catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        startObtainDirection();
        requestHandler.attach(this);
        requestHandler.httpGetStringRequest(url,this);
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

        if(Build.VERSION.SDK_INT < 23) {

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

        populateMap.execute();
    }

    public void populateGoogleMap() {

        ArrayList <User> userOnMapCatalog = populateMap.getUsersOnMapCatalog();
        final HashMap <Marker, User> googleMarkerHash = new HashMap<>();

        /* Creating a custom icon (passenger) */
        int height = 100;
        int width = 100;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.passenger_icon);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        for(User user : userOnMapCatalog) {
            ArrayList<Route> userRoutes = user.getRoutes();
            for(Route route : userRoutes) {
                LatLng location = route.getStartLocation();

                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                        .title(user.getFirstName() + " " + user.getLastName())
                        .position(location));

                googleMarkerHash.put(marker, user);
            }
        }

        //This dialog can use a design pattern!
        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
               User user = googleMarkerHash.get(marker);

                final Dialog dialog = new Dialog(CreateRouteActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.userprofile_dialog_layout);
                dialog.show();

                TextView textViewFullName = (TextView) dialog.findViewById(R.id.textViewFirstName);
                textViewFullName.setText(marker.getTitle());

                TextView textViewEmail = (TextView) dialog.findViewById(R.id.textViewEmail);
                textViewEmail.setText(user.getEmail());

                ImageView imageViewProfile = (ImageView) dialog.findViewById(R.id.imageViewProfile);
                imageViewProfile.setImageResource(R.drawable.sample_profile_image);

                Button buttonInvite = (Button) dialog.findViewById(R.id.buttonInvite);
                buttonInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CreateRouteActivity.this, "Invite Sent", Toast.LENGTH_SHORT).show();
                    }
                });

                Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                return false;
            }
        });
    }



    public void startObtainDirection() {

        if(!requestHandler.isInternetConnected(this)){
            return;
        }
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

            for (int i = 0; i < route.getPoints().size(); i++){

                System.out.println(route.getPoints().get(i));
                polylineOptions.add(route.getPoints().get(i));
            }


            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void responseReceived(String response) {

        requestHandler.detach(this);
        RouteDeserializer deserializer = new RouteDeserializer();
        ArrayList<Route> route = new ArrayList<>();
        try {

            route = (ArrayList<Route>) deserializer.parseJSON(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        List <LatLng> currentUser = route.get(0).getPoints();
        List <LatLng> currentUser = new ArrayList<LatLng>();;

        // H2S 2K5 to McDonald
        currentUser.add(new LatLng(45.53598, -73.60092));
        currentUser.add(new LatLng(45.5353,-73.59939));
        currentUser.add(new LatLng(45.535,-73.59966));
        currentUser.add(new LatLng(45.5347,-73.59994));
        currentUser.add(new LatLng(45.53612,-73.60307));
        currentUser.add(new LatLng(45.53619,-73.60324));

        matchRoute(route, currentUser);
        successObtainDirection(route);
    }

    public void matchRoute(ArrayList<Route> route, List <LatLng> currentUser){

        // For each route
        for (int i = 0; i < route.size(); i++){
            // If not current user...

            for(int j = 0; j < route.get(i).getPoints().size(); j++){

                System.out.println("Checking new route with data "+route.get(i).getPoints());
                for (int k = 0; k < currentUser.size(); k++){

                    // If the distance between two coordinates is less or equal to 20 meters,
                    // they are considered to be on the same path.
                    if(distance(route.get(i).getRoutePointsLatitudeAt(j),
                            route.get(i).getRoutePointsLongtitudeAt(j),
                            currentUser.get(j).latitude,
                            currentUser.get(j).longitude)
                            <=0.0124274){

                        System.out.println("Distance if statement passed: checkEqual now");
                        checkEqual(currentUser, j, route.get(i).getPoints(), j);
                        // if the distance is more than 20 meters, check next point.

                    }

                }

            }

        }
    }

    private void checkEqual (List <LatLng> currentUser, int position1, List <LatLng> route, int position2){

        int maxLength;
        String result=null;
        if (currentUser.size()>route.size()){
            maxLength = currentUser.size();
        } else{
            maxLength = route.size();
        }

        for(int i = 0; position2+i < maxLength && position1+i < maxLength; i++){
            System.out.println("Comparing the following data:" + " Latitude 2: " +
                    route.get(position2+i).latitude + " Longitude 2: " +
                    route.get(position2+i).longitude +" Latitude 1: " +
                    currentUser.get(position1+i).latitude + " Longitude 1: " +
                    currentUser.get(position1+i).longitude);

            if(distance(route.get(position2+i).latitude,
                    route.get(position2+i).longitude,
                    currentUser.get(position1+i).latitude,
                    currentUser.get(position1+i).longitude)
                    >0.0124274){
                result = "false";
                break;
            }
            result = "true";
        }
    }

    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }

}
