package com.example.mrides.Notification;

import com.example.mrides.controller.RequestHandler;
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

public class RegistrationTokenInstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("Refreshed token: " + refreshedToken);
        RequestHandler.getUser().setDeviceId(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }

}
