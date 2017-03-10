/*
* Class User
*
* 03/04/17
*/
package com.example.mrides.Domain;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import org.parceler.Parcel;

import java.util.ArrayList;

import DirectionModel.Route;

@Parcel //parcel annotation is used to help activities communicate with eachother
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String displayName;
    private GoogleSignInAccount acct;

    private ArrayList<Route> routes = new ArrayList<>();

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addRoute (Route route) {
        this.routes.add(route);
    }


    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

}
