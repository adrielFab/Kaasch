/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.userDomain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import DirectionModel.Route;


public class User {

    public enum ParameterKeys{

        EMAIL("loggedInUserEmail"),
        LASTNAME("loggedInUserLastName"),
        FIRSTNAME("loggedInUserFirstName"),
        DEVICEID("loggedInUserDeviceId"),
        PROFILEURL("loggedInUserProfileUrl");

        private String key;

        private ParameterKeys(String key){
            this.key = key;
        }

        public String toString(){
            return this.key;
        }

    }
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String photoUrl;
    private String deviceId;
    private FirebaseUser acct;
    private ArrayList<Route> routes = new ArrayList<>();

    public User(){

    }

    public User(String firstName, String lastName, String email, String photoUrl, String deviceId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoUrl = photoUrl;
        this.deviceId = deviceId;
    }

    /**
     * This constructor initializes the relevant information of a user.
     * @param acct The firebase account associated to the user. Each account will have a firebase account
     * @param googleSignInAccount The signin method performed by the user. In this case it was done through google
     */
    public User(FirebaseUser acct, GoogleSignInAccount googleSignInAccount){

        email = acct.getEmail();
        this.deviceId = FirebaseInstanceId.getInstance().getToken();
        this.photoUrl = acct.getPhotoUrl().toString();
        firstName = googleSignInAccount.getGivenName();
        lastName = googleSignInAccount.getFamilyName();
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
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