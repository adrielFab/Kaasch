package DirectionModel;

import android.util.Log;

import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import DirectionModel.Route;

public class Matcher {

    private Route route = new Route();
    private ArrayList <User> userOnMapCatalog = new ArrayList<>();
    private HashMap<Integer, Marker> matchedMarkers = new HashMap<>();

    public Matcher(Route route) {
        this.route = route;
    }

    public void setMatchedMarkers(HashMap<Integer, Marker> matchedMarkers) {
        this.matchedMarkers = matchedMarkers;
    }

    public HashMap<Integer, Marker> getMatchedMarkers() {
        return this.matchedMarkers;
    }

    public void setUserMapCatalog(ArrayList <User> userOnMapCatalog) {
        this.userOnMapCatalog = userOnMapCatalog;
    }

    public ArrayList <User> getUserMapCatalog() { 
        return this.userOnMapCatalog;
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
        return (earthRadius * c); // output distance, in MILES
    }

    /**
     * This method validates the distance between two points, point of a polyline and start and end address
     * of the passenger. This algorithm indicates that if the distance between two points
     * satisfy the condition, then this passenger point is part of the polyline
     * @param passengerLocation
     * @param userLocation
     * @return boolean
     */
    public boolean validateDistance(LatLng passengerLocation, LatLng userLocation) { 
        if (distance( passengerLocation.latitude, passengerLocation.longitude,
                userLocation.latitude, userLocation.longitude) <= 0.1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Matches route of driver and passengers
     * @param routeOfUser
     */
    public void matchRoute(List<LatLng> routeOfUser) {

        for (User user : userOnMapCatalog) {
            ArrayList<Route> passengerRoutes = user.getRoutes();

            for (Route route : passengerRoutes) {
                LatLng pickUp = route.getStartLocation();
                LatLng drop = route.getEndLocation();
                int passengerRouteId = route.getId();
                Date date = route.getDate();
                int dateMatched = 0;
//                int dateMatched = date.compareTo(this.route.getDate());
                boolean pickUpBool = false;
                boolean goToEnd = false;
                int i = 0;

                // the value of pickUpBool and goToEnd are modified in matchDistance method
                matchDistance(i, passengerRouteId, dateMatched, routeOfUser,  pickUpBool, goToEnd, pickUp, drop);
            }
        }
    }

     /**
     * Match routes on valid distance and user preferences
     */
    private void matchDistance(int i, int passengerRouteId, int dateMatched, List<LatLng> routeOfUser,
                               boolean pickUpBool, boolean goToEnd, LatLng pickUp, LatLng drop) {
        while (i < routeOfUser.size() && !pickUpBool) {
            LatLng pointInPoly = routeOfUser.get(i);
            if (this.validateDistance(pickUp, pointInPoly) && !goToEnd && dateMatched==0) {
                goToEnd = true;
                i++;
            }

            if (this.validateDistance(drop, pointInPoly) && goToEnd) {
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
