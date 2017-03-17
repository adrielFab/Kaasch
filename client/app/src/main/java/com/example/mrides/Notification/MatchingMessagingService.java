package com.example.mrides.Notification;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.InboxActivity;
import com.example.mrides.Activity.MainActivity;
import com.example.mrides.R;
import com.example.mrides.controller.Subject;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MatchingMessagingService extends FirebaseMessagingService implements Subject{

    private ArrayList<ActivityObserver> observers = new ArrayList<>();
    /**
     * The message received from Firebase Cloud Messaging. Allows for users to send
     * notifications to each other devices.
     *
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        System.out.println("From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(500);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Intent intent = new Intent(this, InboxActivity.class);
        intent.putExtra("NOTIFICATION",remoteMessage);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle("FCM NOTIFICATION");
        notificationBuilder.setContentText(remoteMessage.getNotification().getBody());
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.icon);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }



    @Override
    public void attach(ActivityObserver observerToAdd) {

        if(observers.contains(observerToAdd)){
            return;
        }
        observers.add(observerToAdd);
    }

    @Override
    public void detach(ActivityObserver observerToRemove) {
        observers.remove(observerToRemove);
    }

    @Override
    public void Notify(String resonse) {

    }

    @Override
    public void Notify(Map<String, String> response) {

        for(ActivityObserver e : observers){

            e.responseReceived(response);
        }
    }
}
