package DirectionModel;

import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Matcher {

    private ArrayList <User> userOnMapCatalog = new ArrayList<>();
    private HashMap<Integer, Marker> matchedMarkers = new HashMap<>();
    private int passengerRouteId;

    public void putMatchedMarkers (Route route, Marker marker){
        this.matchedMarkers.put(route.getId(), marker);
    }

    public void setPassengerRouteId(int passengerRouteId){
        this.passengerRouteId = passengerRouteId;
    }

    public void setUserOnMapCatalog (ArrayList <User> userOnMapCatalog){
        this.userOnMapCatalog = userOnMapCatalog;
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

    public boolean validateDistance(LatLng passengerLocation, LatLng userLocation) {
        if (distance( passengerLocation.latitude, passengerLocation.longitude,
                userLocation.latitude, userLocation.longitude) <= 0.1) {
            return true;
        }
        else {
            return false;
        }
    }

    public ArrayList <User> getUserOnMapCatalog (){
        return this.userOnMapCatalog;
    }

    public void matchRoute(List<LatLng> routeOfUser) {

        for (User user : userOnMapCatalog) {
            ArrayList<Route> passengerRoutes = user.getRoutes();

            for (Route route : passengerRoutes) {
                LatLng pickUp = route.getStartLocation();
                LatLng drop = route.getEndLocation();
                int passengerRouteId = route.getId();
                boolean pickUpBool = false;
                boolean goToEnd = false;
                int i = 0;

                while (i < routeOfUser.size() && !pickUpBool) {
                    LatLng pointInPoly = routeOfUser.get(i);
                    if (validateDistance(pickUp, pointInPoly) && !goToEnd) {
                        goToEnd = true;
                        i++;
                    }

                    if (validateDistance(drop, pointInPoly) && goToEnd) {
                        for ( int key : matchedMarkers.keySet()) {
                            if(key == passengerRouteId) {
                                Marker marker = matchedMarkers.get(key);
                                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                                pickUpBool = true;
                                break;
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }
}
