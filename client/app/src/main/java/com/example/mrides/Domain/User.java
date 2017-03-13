/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.Domain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

import org.parceler.Parcel;

public class User {

    private String email;
    private String displayName;
    private String photoUrl;
    private FirebaseUser acct;


    public User(FirebaseUser acct){

        email = acct.getEmail();
        displayName = acct.getDisplayName();
        photoUrl = acct.getPhotoUrl().getPath();
    }

    public String getEmail() {

        return email;
    }

    public String getDisplayName() {

        return displayName;
    }

    public String getPhotoUrl(){

        return photoUrl;
    }

    public void setAcct(FirebaseUser acct) {

       this.acct = acct;
    }
}
