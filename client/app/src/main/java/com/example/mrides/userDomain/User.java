/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.parceler.Parcel;

import java.util.ArrayList;

import DirectionModel.Route;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String displayName;
    private String photoUrl;
    private String deviceId;
    private FirebaseUser acct;
    private ArrayList<Route> routes = new ArrayList<>();

    public User(){

    }

    public User(String email, String displayName, String deviceId){

        this.email = email;
        this.displayName = displayName;
        this.deviceId = deviceId;
    }


    public User(FirebaseUser acct, GoogleSignInAccount googleSignInAccount){

        email = acct.getEmail();
        displayName = acct.getDisplayName();
        this.deviceId = FirebaseInstanceId.getInstance().getToken();
        this.photoUrl = acct.getPhotoUrl().getPath();
        firstName = googleSignInAccount.getGivenName();
        lastName = googleSignInAccount.getFamilyName();
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

    public void setAcct(FirebaseUser acct) {

        this.acct = acct;
    }

    public String getLastName() {

        return lastName;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    public int getId() {

        return id;
    }

    public String getFirstName(){

        return firstName;
    }

    public void setId(int id) {

        this.id = id;
    }

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public void addRoute (Route route) {

        this.routes.add(route);
    }

    public String getDeviceId(){

        return deviceId;
    }

    public ArrayList<Route> getRoutes() {

        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {

        this.routes = routes;
    }

    public String getPhotoUrl() {

        return photoUrl;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setPhotoUrl(String photoUrl) {

        this.photoUrl = photoUrl;
    }
}