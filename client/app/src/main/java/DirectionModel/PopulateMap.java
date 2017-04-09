/*
* Class PopulateMap
*
* 03/04/17
*/
package DirectionModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PopulateMap implements ActivityObserver{

    private CreateRouteActivity createRouteActivity;
    private ArrayList<User> usersOnMapCatalog = new ArrayList<>();
    private RequestHandler requestHandler = new RequestHandler();

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
     *  This method requests the users from our server to be added to usersOnMapCatalog
     * @param context the context of the activty making the request
     */
    public void requestUsers(Context context) {
        requestHandler.attach(this);
        Map<String,String> jsonBody = new HashMap<>();
        requestHandler.httpPostStringRequest("http://"+context.getString(R.string.web_server_ip)  +
                        "/populate_maps.php",jsonBody,
                RequestHandler.URLENCODED ,context);
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
            String firstName = jsonObject.getString("first_name");
            String lastName = jsonObject.getString("last_name");
            String email = jsonObject.getString("email");
            String deviceKey = jsonObject.getString("device_key");
            String rating = jsonObject.getString("rating");
            String searchId = jsonObject.getString("search_id");
            int intWantsSmoker = jsonObject.getInt("wantsSmoker");
            int intWantsBoy = jsonObject.getInt("wantsBoy");
            int intWantsGirl = jsonObject.getInt("wantsGirl");
            String startDateTime = jsonObject.getString("start_date_time");
            String routeName = jsonObject.getString("route_name");
            String gender = jsonObject.getString("gender");
            String smoker = jsonObject.getString("smoker");

            boolean wantsSmoker = (intWantsSmoker != 0);
            boolean wantsBoy = (intWantsBoy != 0);
            boolean wantsGirl = (intWantsGirl != 0);
            Preference preference = new Preference(wantsBoy, wantsGirl, wantsSmoker);

            System.out.println("boy "+wantsBoy);
            DateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = sdf.parse(startDateTime);
            } catch (ParseException e) {
                Log.e("PopulateMap ", e.getMessage());
            }


            System.out.println("Date is "+date);
            route.setId(id);
            route.setTitle(routeName);
            route.setPreference(preference);
            route.setDate(date);

            user.setDeviceId(deviceKey);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setSmokes(smoker);
            user.setGender(gender);

            String[] latlongStart =  jsonObject.getString("start_point").split(",");
            double latitudeS = Double.parseDouble(latlongStart[0]);
            double longitudeS = Double.parseDouble(latlongStart[1]);
            LatLng locationS = new LatLng(latitudeS, longitudeS);

            String[] latlongEnd =  jsonObject.getString("end_point").split(",");
            double latitudeE = Double.parseDouble(latlongEnd[0]);
            double longitudeE = Double.parseDouble(latlongEnd[1]);
            LatLng locationE = new LatLng(latitudeE, longitudeE);

            route.setStartLocation(locationS);
            route.setEndLocation(locationE);
            user.addRoute(route);

            usersOnMapCatalog.add(user);
        }

        this.createRouteActivity.populateGoogleMap();
    }

    @Override
    public void Update(String response) {
        System.out.println("RESS" + response);
        requestHandler.detach(this);
        try {
            parseUserandMarker(response);
        } catch (JSONException e) {
            Log.e("PopulateMap", e.getMessage());
        }
    }
}
