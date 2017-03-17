/*
* Class PopulateMap
*
* 03/04/17
*/
package DirectionModel;

import android.os.AsyncTask;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Domain.User;
import com.google.android.gms.maps.model.LatLng;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PopulateMap extends AsyncTask<Void, Void, String>{

    private CreateRouteActivity createRouteActivity;
    private ArrayList<User> usersOnMapCatalog = new ArrayList<>();


    public PopulateMap(CreateRouteActivity createRouteActivity){
        this.createRouteActivity = createRouteActivity;
    }

    public ArrayList<User> getUsersOnMapCatalog() {
        return usersOnMapCatalog;
    }

    public void setUsersOnMapCatalog(ArrayList<User> usersOnMapCatalog) {
        this.usersOnMapCatalog = usersOnMapCatalog;
    }

    @Override
    protected String doInBackground(Void... voids) {

        String retrieve_url = "http://successdrivingschool.ca/test2_android.php";

        try {

            URL url = new URL(retrieve_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            InputStream inputStream =  httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null){

                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {

        try{


            System.out.println("Result is null");
            parseUserandMarker(result);
        } catch (JSONException e){

            e.printStackTrace();
        }

    }

    private void parseUserandMarker(String result) throws JSONException {

        if (result == null){
            return;
        }

        HashMap<User, LatLng> hashUsers = new HashMap<>();
        JSONArray jsonData = new JSONArray(result);


        List<Route> routes = new ArrayList<Route>();

        JSONObject jsonDataRoute = new JSONObject(result);
        JSONArray jsonRoutes = jsonDataRoute.getJSONArray("routes");

        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Route route = new Route();

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

            System.out.println("Points are: "+decodePoly(overview_polylineJson.getString("points")));
            routes.add(route);
        }


        for(int i = 0; i < jsonData.length(); i ++){

            User user = new User();
            Route route = new Route();

            JSONObject jsonObject = (JSONObject) jsonData.get(i);
            int id = jsonObject.getInt("id");
            String firstName = jsonObject.getString("firstName");
            String lastName = jsonObject.getString("lastName");
            String email = jsonObject.getString("email");

            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);

            String[] latlong =  jsonObject.getString("start").split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng location = new LatLng(latitude, longitude);

            route.setStartLocation(location);
            JSONObject overview_polylineJson = jsonObject.getJSONObject("overview_polyline");

            route.setPoints(decodePoly(overview_polylineJson.getString("points")));
            user.addRoute(route);

            usersOnMapCatalog.add(user);
        }

        this.createRouteActivity.populateGoogleMap();
    }

    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {

            int b, shift = 0, result = 0;
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
