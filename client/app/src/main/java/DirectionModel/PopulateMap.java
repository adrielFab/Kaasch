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

public class PopulateMap extends AsyncTask<Void, Void, String>{

    private CreateRouteActivity createRouteActivity;
    private ArrayList<User> usersOnMapCatalog = new ArrayList<>();


    public PopulateMap(CreateRouteActivity createRouteActivity){
        this.createRouteActivity = createRouteActivity;
    }

    /**
     * Accessor method for usersOnMapCatalog
     * @return ArrayList<User> This returns a list of all the user to be shown on the map
     */
    public ArrayList<User> getUsersOnMapCatalog() {

        return usersOnMapCatalog;
    }

    /**
     * Mutator method for usersOnMapCatalog
     * @param usersOnMapCatalog
     */
    public void setUsersOnMapCatalog(ArrayList<User> usersOnMapCatalog) {

        this.usersOnMapCatalog = usersOnMapCatalog;
    }

    /**
     * Method that establishes a connection with the web server, sends a GET request
     * and retrieves the corresponding data
     * @param voids
     * @return String This returns the response of the request
     */
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

    /**
     * This method is executed before doInBackground
     */
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    /**
     * This method is executed after doInBackground. If the result is not a successful
     * JSON string, the method will display an error message.
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {

        try{

            parseUserandMarker(result);
        } catch (JSONException e){

            e.printStackTrace();
        }

    }

    /**
     * This method parses the jsonData and stores the information of the users
     * on the map and adds each user to the usersOnMapCatalog
     * @param result
     * @throws JSONException
     */
    private void parseUserandMarker(String result) throws JSONException {

        if (result == null)
            return;

        JSONArray jsonData = new JSONArray(result);

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
            user.addRoute(route);

            usersOnMapCatalog.add(user);
        }

        this.createRouteActivity.populateGoogleMap();
    }
}
