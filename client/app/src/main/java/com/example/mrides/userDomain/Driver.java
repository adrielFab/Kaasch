package com.example.mrides.userDomain;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public class Driver extends User {

    public Driver(FirebaseUser acct, GoogleSignInAccount googleSignInAccount) {
        super(acct, googleSignInAccount);
    }}
