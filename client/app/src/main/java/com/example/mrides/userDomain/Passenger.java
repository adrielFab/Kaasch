package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;


/**
 * This class helps distingues between logged in users, passengers and drivers.
 */
public class Passenger extends User {

    public Passenger() {

    }

    public enum ParameterKeys{

        EMAIL("passengerEmail"),
        LASTNAME("passengerLastName"),
        FIRSTNAME("passengerFirstName"),
        DEVICEID("passengerDeviceId"),
        PROFILEURL("passengerProfileUrl"),
        GENDER("passengerGender"),
        SEARCHID("passengerSearchId"),
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

    private int searchId;

    public Passenger(FirebaseUser acct, GoogleSignInAccount googleSignInAccount) {
        super(acct, googleSignInAccount);
    }

    public void setSearchId(int searchId){
        this.searchId = searchId;
    }

    public int getSearchId(){
        return searchId;
    }

}
