/*
* Class RouteDeserializer
*
* 03/04/17
*/
package DirectionModel;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RouteDeserializer {

    /**
     * This method takes the jsonData provided by the google maps api. Then, it stores all the needed values
     * which characterize a route, according to the Route.java model
     *
     * @param res
     * @return Route
     * @throws JSONException
     */
    public Route parseJSON(String res) throws JSONException {

        Route route = new Route();
        if (res == null)
            return route;

        JSONObject jsonData = new JSONObject(res);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            route = new Route();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.setDistance(new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value")));
            route.setDuration(new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value")));
            route.setEndAddress(jsonLeg.getString("end_address"));
            route.setStartAddress(jsonLeg.getString("start_address"));
            route.setStartLocation(new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng")));
            route.setEndLocation(new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng")));
            route.setPoints(decodePoly(overview_polylineJson.getString("points")));

        }
        return route;
    }

    /**
     * This is a method that takes the polyline provided by google maps api, and returns a list of points
     * which can be interconnected to form the route
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     *
     * @param encoded
     * @return List<LatLng> This returns a list of latitude and longitude coordinates that the new polyline
     * will pass through to form the route
     */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0;
        int lng = 0;

        while (index < len) {

            int b;
            int shift = 0;
            int result = 0;
            do {

                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {

                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
