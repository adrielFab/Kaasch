package DirectionModel;

import com.example.mrides.userDomain.Passenger;
import android.util.Log;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Matcher {

    private List <Passenger> userOnMapCatalog = new ArrayList<>();
    private HashMap<Integer, Marker> matchedMarkers = new HashMap<>();
    private Route route = new Route();

    public Matcher(Route route) {
        this.route = route;
    }

    public void setMatchedMarkers(HashMap<Integer, Marker> matchedMarkers) {
        this.matchedMarkers = matchedMarkers;
    }

    public HashMap<Integer, Marker> getMatchedMarkers() {
        return this.matchedMarkers;
    }

    public void setUserMapCatalog(List <Passenger> userOnMapCatalog) {
        this.userOnMapCatalog = userOnMapCatalog;
    }

    public List <Passenger> getUserMapCatalog() {
        return this.userOnMapCatalog;
    }

    /** calculates the distance between two locations in MILES */
    public double distance(double lat1, double lng1, double lat2, double lng2) {
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
            return (distance( passengerLocation.latitude, passengerLocation.longitude,
                    userLocation.latitude, userLocation.longitude) <= 0.1);
    }

    /**
     * Matches route of driver and passengers
     * @param routeOfUser
     */
    public void matchRoute(List<LatLng> routeOfUser) {

        for (User user : userOnMapCatalog) {
            ArrayList<Route> passengerRoutes = user.getRoutes();

            for (Route route : passengerRoutes) {

                // Match current user's preference to other users'information
                if(!matchPreferences(this.route, user)){
                    continue;
                }

                LatLng pickUp = route.getStartLocation();
                LatLng drop = route.getEndLocation();
                int passengerRouteId = route.getId();
                Date dateOfPassenger = route.getDate(); //from the passenger
                Date dateOfUser = this.route.getDate(); //from the user
                int dateMatched = 1;

                Calendar cal = Calendar.getInstance();
                cal.setTime(dateOfUser);
                cal.add(Calendar.HOUR, +1);
                Date datePlusAnHour = cal.getTime();

                cal.setTime(dateOfUser);
                cal.add(Calendar.HOUR, -1);
                Date dateMinusAnHour = cal.getTime();

                if (dateOfPassenger.after(dateMinusAnHour) && dateOfPassenger.before(datePlusAnHour)) {
                    dateMatched = 0;
                }

                boolean pickUpBool = false;
                boolean goToEnd = false;
                int i = 0;
                // the value of pickUpBool and goToEnd are modified in matchDistance method
                matchDistance(passengerRouteId, dateMatched, routeOfUser, pickUp, drop);
            }
        }
    }

     /**
     * Match routes on valid distance and user preferences
     */
    private void matchDistance(int passengerRouteId, int dateMatched, List<LatLng> routeOfUser,
                               LatLng pickUp, LatLng drop) {
        int i = 0;
        boolean pickUpBool = false;
        boolean goToEnd = false;

        while (i < routeOfUser.size() && !pickUpBool) {
            LatLng pointInPoly = routeOfUser.get(i);
            // match the pickup points
            if (this.validateDistance(pickUp, pointInPoly) && !goToEnd && dateMatched==0) {
                goToEnd = true;
            } else {
                Marker marker = matchedMarkers.get(passengerRouteId);
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            }
            // match the dropout points
            if (this.validateDistance(drop, pointInPoly) && goToEnd) {
                for (Map.Entry<Integer, Marker> entry : matchedMarkers.entrySet()) {
                    int key = entry.getKey();
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

    private boolean matchPreferences(Route route, User user) {
        return route.getPreference().matchPreferences(user);
    }

}
