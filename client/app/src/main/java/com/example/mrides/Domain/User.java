/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.Domain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.parceler.Parcel;

@Parcel //parcel annotation is used to help activities communicate with eachother
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String displayName;
    private GoogleSignInAccount acct; //

    public User(){ //empty constructor needed for Parcel

    }

    public User(String email, String displayName){

        this.email = email;
        this.displayName = displayName;
    }


    public User(GoogleSignInAccount acct){

        email = acct.getEmail();
        displayName = acct.getDisplayName();
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
