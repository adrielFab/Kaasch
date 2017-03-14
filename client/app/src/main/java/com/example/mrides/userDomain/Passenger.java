package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;



public class Passenger extends User {

    public Passenger(String email, String displayName, String deviceId) {
        super(email, displayName, deviceId);
    }

    public Passenger(FirebaseUser acct, GoogleSignInAccount googleSignInAccount) {
        super(acct, googleSignInAccount);
    }
}
