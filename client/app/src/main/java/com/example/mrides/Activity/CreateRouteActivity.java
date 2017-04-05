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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.userDomain.Driver;
import com.example.mrides.userDomain.DriverJSONMap;
import DirectionModel.Matcher;
import com.example.mrides.userDomain.PassengerSerializer;
import com.example.mrides.userDomain.User;
import com.example.mrides.userDomain.UserSerializer;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DirectionModel.PopulateMap;
import DirectionModel.Route;
import DirectionModel.RouteDeserializer;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityObserver, GoogleMap.OnMarkerClickListener, View.OnClickListener{

    private TextView textViewStartLocation;
    private TextView textViewEndLocation;
    private Button mButtonFindPath;
    private Button buttonSaveChanges;
    private GoogleMap mGoogleMap;
    private ProgressDialog mProgressDialog;
    private List<Marker> startMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RequestHandler requestHandler = new RequestHandler();
    private PopulateMap populateMap = new PopulateMap(this);
    private HashMap <Marker, User> googleMarkerHash = new HashMap<>();
    private User selectedPassenger;
    private User loggedInUser = RequestHandler.getUser();
    private Dialog dialog;
    private ArrayList <User> userOnMapCatalog = new ArrayList<>();
    private HashMap <Integer, Marker> matchedMarkers = new HashMap<>();
    private boolean startOrEnd;
    private String start;
    private String destination;
    final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;


//    /**
//     * Method that requests the user to capture their current location
//     * @param requestCode
//     * @param permissions
//     * @param grantResults
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == 1) {
//
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                //requestLocationUpdates demands an explicit permission check
//                if(ContextCompat.checkSelfPermission(this,  Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
//                }
//            }
//        }
//    }

    /**
     * Method that is called to load the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        start = "";
        destination = "";

        textViewStartLocation = (TextView) findViewById(R.id.textViewStartLocation);
        textViewEndLocation = (TextView) findViewById(R.id.textViewEndLocation);
        buttonSaveChanges = (Button) findViewById(R.id.buttonSaveChanges);
        mButtonFindPath = (Button) findViewById(R.id.buttonFindPath);

        textViewStartLocation.setOnClickListener(this);
        textViewEndLocation.setOnClickListener(this);
        buttonSaveChanges.setOnClickListener(this);
        mButtonFindPath.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                createPath();
            }
        });

    }

    /**
     * Method that handles user inputs and executes the creation of path after successful evaluation
     */
    public void createPath(){
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
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        startObtainDirection();
        requestHandler.attach(this);
        requestHandler.httpGetStringRequest(url, this);

    }


    /**
     * Method that loads the googleMap
     * @param googleMap
     */
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

                LatLng myLocation;

                if(lastKnownLocation != null) {
                    myLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                } else {
                    myLocation = new LatLng(45.4958567,-73.5743482);
                }

                mGoogleMap.clear();

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(myLocation)
                        .title("My Location"));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
            }
        }

        populateMap.requestUsers(this);
    }

    /**
     * This methods adds all the passenger on the google map. Each passenger is a google marker
     * and their position is the start address of the route. When clicking on a passenger,
     * their information(profile) will appear as a dialog
     */
    public void populateGoogleMap() {

         userOnMapCatalog = populateMap.getUsersOnMapCatalog();

        for(User user : userOnMapCatalog) {
            ArrayList<Route> userRoutes = user.getRoutes();
            for(Route route : userRoutes) {
                LatLng location = route.getStartLocation();

                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(user.getFirstName() + " " + user.getLastName() + route.getId())
                        .position(location));

                matchedMarkers.put(route.getId(), marker);
                googleMarkerHash.put(marker, user);

            }
        }
        mGoogleMap.setOnMarkerClickListener(this);
    }


    /**
     * This method displays a progress dialog to let the user know that the route is being retrieved
     */
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

    /**
     * This methods creates the route from the input start address and the input end address
     * @param routes
     */
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
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
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

            Matcher matcher = new Matcher ();
            matcher.setMatchedMarkers(this.matchedMarkers);
            matcher.setUserMapCatalog(this.userOnMapCatalog);

            matcher.matchRoute(route.getPoints());
        }
    }

    /**
     * This method receives a response for the creation of the route and sends
     * the request to the handler
     * @param response A string response formatted in a json string returned from the request handler
     */
    @Override
    public void Update(String response) {
        System.out.println(response);
        requestHandler.detach(this);
        RouteDeserializer deserializer = new RouteDeserializer();
        ArrayList<Route> route = new ArrayList<>();
        try {

            route = (ArrayList<Route>) deserializer.parseJSON(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        successObtainDirection(route);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        selectedPassenger = googleMarkerHash.get(marker);

        if (selectedPassenger != null) {
            dialog = new Dialog(CreateRouteActivity.this);
            dialog.setTitle(marker.getTitle());
            dialog.setContentView(R.layout.userprofile_dialog_layout);
            dialog.show();

            TextView textViewFullName = (TextView) dialog.findViewById(R.id.textViewFirstName);
            textViewFullName.setText(marker.getTitle());

            TextView textViewEmail = (TextView) dialog.findViewById(R.id.textViewEmail);
            textViewEmail.setText(selectedPassenger.getEmail());

            ImageView imageViewProfile = (ImageView) dialog.findViewById(R.id.imageViewProfile);
            imageViewProfile.setImageResource(R.drawable.sample_profile_image);

            Button buttonInvite = (Button) dialog.findViewById(R.id.buttonInvite);

            buttonInvite.setOnClickListener(this);

            Button buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
            buttonCancel.setOnClickListener(this);

        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonInvite:
                invitePassenger();
                break;
            case R.id.buttonCancel:
                dialog.cancel();
                break;
            case R.id.buttonSaveChanges:
                saveChanges();
                break;
            case R.id.textViewStartLocation:
                startOrEnd = false;
                showSearchLocationDialog();
                break;
            case R.id.textViewEndLocation:
                startOrEnd = true;
                showSearchLocationDialog();
            default:
                break;
        }
    }

    private void invitePassenger(){

        requestHandler.attach(this);
        //combine map so that it contains driver information and passenger information
        Map<String,String> driverJsonBody = UserSerializer.getParameters(loggedInUser);
        Map<String,String> passengerJSonBody = PassengerSerializer.getParameters(selectedPassenger);
        Map<String,String> jsonBody = new HashMap<>();
        jsonBody.putAll(driverJsonBody);
        jsonBody.putAll(passengerJSonBody);
        requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                        "/invitePassenger.php",jsonBody,
                RequestHandler.URLENCODED ,this);
        Toast.makeText(CreateRouteActivity.this, getString(R.string.invie_sent), Toast.LENGTH_SHORT).show();
    }

    /**
     * Brings user back to homepage
     */
    public void saveChanges() {
        Intent intent = new Intent(CreateRouteActivity.this, HomePage.class);
        startActivity(intent);
    }

    public PopulateMap getPopulateMap() {
        return populateMap;
    }

    public void setPopulateMap(PopulateMap populateMap) {
        this.populateMap = populateMap;
    }

    public GoogleMap getGoogleMap() {
        return mGoogleMap;
    }

    public void setGoogleMap(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    /**
     * Method that shows the search bar where the user will enter the location
     */
    public void showSearchLocationDialog() {
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry("CA")
                    .build();
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter)
                            .build(CreateRouteActivity.this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                String apple = "";
                if (!startOrEnd) {
                    apple = "start";
                    start = place.getName().toString();
                    textViewStartLocation.setText(start);
                } else {
                    apple = "end";
                    destination = place.getName().toString();
                    textViewEndLocation.setText(destination);
                }
                Toast.makeText(CreateRouteActivity.this, apple + place.getName().toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("Check", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
