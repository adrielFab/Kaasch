package Route;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.Passenger;
import com.example.mrides.userDomain.UserSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouteView implements ActivityObserver{

    private Context context;
    private List<Passenger> listOfPassengers = new ArrayList<>();
    private LinearLayout.LayoutParams layout;
    private RequestHandler requestHandler = new RequestHandler();

    public RouteView(LinearLayout.LayoutParams ll, final Context context){
        this.layout = ll;
        this.context = context;
    }

    public void displayButtons(String buttons){
        Map<String, String> jsonbody = new HashMap<>();
        Map<String, String> userInfo = UserSerializer.getParameters(RequestHandler.getUser());
        requestHandler.httpPostStringRequest("http://"+context.getString(R.string.web_server_ip)  +
                        "/getPassengersOnRoute.php",jsonbody ,
                RequestHandler.URLENCODED ,context);
    }

    @Override
    public void Update(String response) {

    }
}
