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
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrides.Invitation.InvitePassengers;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.Passenger;
import com.example.mrides.userDomain.User;
import com.example.mrides.userDomain.UserSerializer;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DirectionModel.Matcher;
import DirectionModel.PopulateMap;
import DirectionModel.Route;
import DirectionModel.RouteDeserializer;

public class CreateRouteActivity extends FragmentActivity implements OnMapReadyCallback,
        ActivityObserver, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    private EditText textViewStartLocation;
    private EditText textViewEndLocation;
    private GoogleMap mGoogleMap;
    private ProgressDialog mProgressDialog;
    private List<Marker> startMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private LocationManager locationManager;
    private RequestHandler requestHandler = new RequestHandler();
    private PopulateMap populateMap = new PopulateMap(this);
    private HashMap<Marker, Passenger> googleMarkerHash = new HashMap<>();
    private Passenger selectedPassenger;
    private Dialog dialog;
    private ArrayList<Passenger> userOnMapCatalog = new ArrayList<>();
    private HashMap<Integer, Marker> matchedMarkers = new HashMap<>();
    private boolean startOrEnd;
    private String start;
    private String destination;
    static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String in_date;
    private String in_time;
    private String in_title;
    private String role;
    private boolean likesSmokes;
    private boolean isLikesBoys;
    private boolean isLikesGirls;
    private Route route;
    private List<Passenger> invitedPassengers = new ArrayList<>();
    /**
     * Method that is called to load the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        in_date = bundle.getString("in_date");
        in_time = bundle.getString("in_time");
        in_title = bundle.getString("in_title");
        likesSmokes =  bundle.getBoolean("likesSomes");
        isLikesBoys =  bundle.getBoolean("likesBoys");
        isLikesGirls = bundle.getBoolean("likesGirls");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        start = "";
        destination = "";

        textViewStartLocation = (EditText) findViewById(R.id.textViewStartLocation);
        textViewEndLocation = (EditText) findViewById(R.id.textViewEndLocation);
        Button buttonSaveChanges = (Button) findViewById(R.id.buttonSaveChanges);
        Button mButtonFindPath = (Button) findViewById(R.id.buttonFindPath);

        textViewStartLocation.setOnClickListener(this);
        textViewEndLocation.setOnClickListener(this);
        buttonSaveChanges.setOnClickListener(this);
        mButtonFindPath.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                createPath();
            }
        });
        textViewEndLocation.setFocusable(false);
        textViewStartLocation.setFocusable(false);

        role = getIntent().getExtras().getString("role");
        Log.i("ROLE", role);
    }

    /**
     * Method that handles user inputs and executes the creation of path after successful evaluation
     * Create path uses the google maps url to get the route by appending the start and end
     * addresses along with the google map api key
     */
    public void createPath() {
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

            Log.e("CreateRouteActivity", e.getMessage());
        }
        startObtainDirection();
        requestHandler.attach(this);
        requestHandler.httpGetStringRequest(url, this);
    }


    /**
     * Method that loads the googleMap
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {

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


        populateMap.requestUsers(this);
    }

    /**
     * This methods adds all the passenger on the google map. Each passenger is a google marker
     * and their position is the start address of the route. When clicking on a passenger,
     * their information(profile) will appear as a dialog
     */
    public void populateGoogleMap() {

        userOnMapCatalog = populateMap.getUsersOnMapCatalog();

        for (Passenger user : userOnMapCatalog) {
            ArrayList<Route> userRoutes = user.getRoutes();
            for (Route route : userRoutes) {
                LatLng location = route.getStartLocation();

                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .title(user.getFirstName() + " " + user.getLastName() + route.getId())
                        .position(location));

                matchedMarkers.put(route.getId(), marker);
                googleMarkerHash.put(marker, user);

            }
        }

        if ("driver".equals(role)) {
            mGoogleMap.setOnMarkerClickListener(this);
        }
    }


    /**
     * Method displays a progress dialog to let the user know that the route is being retrieved
     */
    public void startObtainDirection() {

        if (!requestHandler.isInternetConnected(this)) {
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

            for (Polyline polyline : polylinePaths) {

                polyline.remove();
            }
        }
    }

    /**
     * Method that creates the route from the input start address and the input end address
     * This also has an association with the matcher to invoke a matching algorithm with
     * another route to see if a certain passenger can be picked up.
     *
     */
    public void successObtainDirection() {

        mProgressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        startMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

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

        DateFormat sdf = new SimpleDateFormat("mm-dd-yyyy hh:mm");
        Date date = null;
        String strDate = this.in_date + " " + this.in_time;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            Log.e("CreateRouteActivity", e.getMessage());
        }

        route.setDate(date);

        Matcher matcher = new Matcher();
        matcher.setMatchedMarkers(this.matchedMarkers);
        matcher.setUserMapCatalog(this.userOnMapCatalog);

        matcher.matchRoute(route.getPoints());
        matcher.getMatchedMarkers();
    }

    /**
     * This method receives a response for the creation of the route and sends
     * the request to the handler
     *
     * @param response A string response formatted in a json string returned from the request handler
     */
    @Override
    public void Update(String response) {
        Log.e("CreateRouteActivity: " , response);
        requestHandler.detach(this);
        if(!response.contains("Success")){ //enters statement from google api
            RouteDeserializer deserializer = new RouteDeserializer();
            route = new Route();
            try {
                route = (Route) deserializer.parseJSON(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("CreateRouteActivity", e.getMessage());

            }
            successObtainDirection();
        }
        else if(role.equals("driver") ){ //enters statement from create_route_driver
            InvitePassengers invitePassengers = new InvitePassengers(this, invitedPassengers,this.in_title);
            invitePassengers.invitePassengers();
        }
    }

    /**
     * Method that handles a click event towards a google marker. These google markers correspond only to
     * passengers, and when a passenger is clicked, the user will be able to see the user's profile.
     * In addition, a dialog will appear with the option to invite the passenger.
     * @param marker
     * @return boolean. A predefined boolean that indicates if the button is clickable or not.
     */
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

    /**
     * Method that handles all the click events that are being done in the map. The click events
     * are for entering a start and end location, inviting a passenger, and saving changes
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonInvite:
                addPassengerToInvitationList();
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
                break;
            default:
                break;
        }
    }

    /**
     * Adds a passenger to the invites list to be invited after a route is created
     *
     */
    private void addPassengerToInvitationList(){
        if(!invitedPassengers.contains(selectedPassenger)){
            invitedPassengers.add(selectedPassenger);
            Toast.makeText(CreateRouteActivity.this,
                    getString(R.string.invite_sent), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that saves the changes made by the user for the creation of a route
     * and sends that data to the database
     *
     * The user is then redirected back to the homepage
     */
    public void saveChanges() {
        requestHandler.attach(this);
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("route_name",this.in_title);
        jsonBody.put("start",route.getStartLocationAsSting());
        jsonBody.put("end",route.getEndLocationAsSting());
        jsonBody.put("duration",String.valueOf(route.getDuration().getValue()));
        jsonBody.put("distance",String.valueOf(route.getDistance().getValue()));
        jsonBody.put("time", route.getDate().toString());
        jsonBody.put("smoking", String.valueOf(likesSmokes));
        jsonBody.put("boy", String.valueOf(this.isLikesBoys));
        jsonBody.put("girl",String.valueOf(this.isLikesGirls));
        Map<String, String> passengerInfo;
        passengerInfo = UserSerializer.getParameters(RequestHandler.getUser());
        jsonBody.putAll(passengerInfo);
        System.out.println("createroute: passenger: " + jsonBody.get("smoking"));
        System.out.println("createroute: passenger: " + jsonBody.get("boy"));
        System.out.println("createroute: passenger: " + jsonBody.get("girl"));
        if(role.equals("driver")){
            requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                            "/create_route_driver.php",jsonBody,
                    RequestHandler.URLENCODED ,this);
        }
        else{
            requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                            "/create_route_passenger.php",jsonBody,
                    RequestHandler.URLENCODED ,this);
        }
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
            Log.e("CreateRouteError: ", e.toString());
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e("CreateRouteError: ", e.toString());
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (!startOrEnd) {
                    start = place.getName().toString();
                    textViewStartLocation.setText(start);
                } else {
                    destination = place.getName().toString();
                    textViewEndLocation.setText(destination);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("Check", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
