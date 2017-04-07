package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;


/**
 * This class helps distingues between logged in users, passengers and drivers.
 */
public class Passenger extends User {

    public enum ParameterKeys{

        EMAIL("passengerEmail"),
        LASTNAME("passengerLastName"),
        FIRSTNAME("passengerFirstName"),
        DEVICEID("passengerDeviceId"),
        PROFILEURL("passengerProfileUrl"),
        GENDER("passengerGender"),
        SMOKES("passengerSmokes");

        private String key;

        private ParameterKeys(String key){
            this.key = key;
        }

        @Override
        public String toString(){
            return this.key;
        }
    }

    public Passenger(FirebaseUser acct, GoogleSignInAccount googleSignInAccount) {
        super(acct, googleSignInAccount);
    }
}
