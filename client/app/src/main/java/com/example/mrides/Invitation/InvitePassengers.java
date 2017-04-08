package com.example.mrides.Invitation;

import android.content.Intent;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Activity.HomePage;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.PassengerSerializer;
import com.example.mrides.userDomain.User;
import com.example.mrides.userDomain.UserSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvitePassengers implements ActivityObserver{

    private List<User> invitedUsers;
    private CreateRouteActivity createRouteActivity;
    private RequestHandler requestHandler = new RequestHandler();

    public InvitePassengers(CreateRouteActivity createRouteActivity, List<User> invitedUsers) {
        this.createRouteActivity =createRouteActivity;
        this.invitedUsers = invitedUsers;
    }

    public void invitePassengers() {
        requestHandler.attach(createRouteActivity);
        //combine map so that it contains driver information and passenger information
        Map<String,String> driverJsonBody = UserSerializer.getParameters(RequestHandler.getUser());
        for(User selected : invitedUsers) {
            Map<String,String> passengerJSonBody = PassengerSerializer.getParameters(selected);
            Map<String,String> jsonBody = new HashMap<>();
            jsonBody.putAll(driverJsonBody);
            jsonBody.putAll(passengerJSonBody);
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
        requestHandler.detach(this);
    }
}
