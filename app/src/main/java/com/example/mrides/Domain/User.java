package com.example.mrides.Domain;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.parceler.Parcel;

@Parcel
public class User {

    private String email;
    private String displayName;
    private GoogleSignInAccount acct; //

    public User(){

    }

    public User(GoogleSignInAccount acct){
        email = acct.getEmail();
        displayName = acct.getDisplayName();
    }

    public User(String asdf){
        asdf = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public GoogleSignInAccount getAcct() {
        return acct;
    }

    public void setAcct(GoogleSignInAccount acct) {
        this.acct = acct;
    }
}
