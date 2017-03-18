package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;


/**
 * This class helps distingues between logged in users, passengers and drivers.
 */
public class Passenger extends User {

    public Passenger(FirebaseUser acct, GoogleSignInAccount googleSignInAccount) {
        super(acct, googleSignInAccount);
    }
}
