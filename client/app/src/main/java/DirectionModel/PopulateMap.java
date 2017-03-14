/*
* Class PopulateMap
*
* 03/04/17
*/
package DirectionModel;

import android.os.AsyncTask;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.userDomain.User;
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

            parseUserandMarker(result);
        } catch (JSONException e){

            e.printStackTrace();
        }

    }

    private void parseUserandMarker(String result) throws JSONException {

        if (result == null)
            return;

        HashMap<User, LatLng> hashUsers = new HashMap<>();
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
