package com.example.mrides.Notification;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;
import com.example.mrides.userDomain.UserSerializer;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * When the application is initialized, this class
 * generates the device's registration token.
 * On startup a registration token is generated. This token
 * is used to identify the client using Firebase Cloud Messaging
 * Services.
 * https://firebase.google.com/docs/cloud-messaging/android/first-message
 */

public class RegistrationTokenInstanceIdService extends FirebaseInstanceIdService implements ActivityObserver{

    private RequestHandler handler = new RequestHandler();

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Refreshed token changed: " + refreshedToken);
        User user = RequestHandler.getUser();
        if(RequestHandler.getUser()!=null) {
            RequestHandler.getUser().setDeviceId(refreshedToken);
            handler.httpPostStringRequest("http://" + getString(R.string.web_server_ip) +
                            "/updateDeviceId.php", UserSerializer.getParameters(user),
                    "application/x-www-form-urlencoded; charset=UTF-8", this);
        }
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }

    @Override
    public void Update(String response) {

    }
}
