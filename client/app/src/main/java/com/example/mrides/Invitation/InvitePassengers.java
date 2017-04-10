package com.example.mrides.Invitation;

import android.content.Intent;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Activity.HomePage;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.Passenger;
import com.example.mrides.userDomain.PassengerSerializer;
import com.example.mrides.userDomain.UserSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitePassengers implements ActivityObserver{

    private List<Passenger> invitedUsers;
    private String in_title;
    private CreateRouteActivity createRouteActivity;
    private RequestHandler requestHandler = new RequestHandler();

    public InvitePassengers(CreateRouteActivity createRouteActivity, List<Passenger> invitedUsers, String in_title) {
        this.createRouteActivity =createRouteActivity;
        this.invitedUsers = invitedUsers;
        this.in_title = in_title;
    }

    public void invitePassengers() {
        requestHandler.attach(this);
        //combine map so that it contains driver information and passenger information
        Map<String,String> driverJsonBody = UserSerializer.getParameters(RequestHandler.getUser());
        for(Passenger selected : invitedUsers) {
            Map<String,String> passengerJSonBody = PassengerSerializer.getParameters(selected);
            Map<String,String> jsonBody = new HashMap<>();
            jsonBody.putAll(driverJsonBody);
            jsonBody.putAll(passengerJSonBody);
            jsonBody.put("route_name",in_title);
            requestHandler.httpPostStringRequest("http://"+
                    createRouteActivity.getString(R.string.web_server_ip)  +
                            "/invitePassenger.php",jsonBody,
                    RequestHandler.URLENCODED ,createRouteActivity);
        }
        invitedUsers.clear();
        Intent intent = new Intent(createRouteActivity, HomePage.class);
        createRouteActivity.startActivity(intent);
    }

    @Override
    public void Update(String response) {
        System.out.println("Invite Passenger." + response);
        requestHandler.detach(this);
    }
}
