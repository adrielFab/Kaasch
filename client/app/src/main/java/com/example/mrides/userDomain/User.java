/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.userDomain;

import com.google.firebase.auth.FirebaseUser;

public class User {

    private String email;
    private String displayName;
    private String photoUrl;
    private FirebaseUser acct;


    public User(FirebaseUser acct){

        email = acct.getEmail();
        displayName = acct.getDisplayName();
        photoUrl = acct.getPhotoUrl().getPath();
        this.acct = acct;
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

    public FirebaseUser getFirebaseAcct() {
        return acct;
    }
}
