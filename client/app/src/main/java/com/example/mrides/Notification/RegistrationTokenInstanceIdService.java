package com.example.mrides.Notification;

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
}
