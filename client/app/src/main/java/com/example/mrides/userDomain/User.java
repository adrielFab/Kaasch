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
        PROFILEURL("loggedInUserProfileUrl"),
        GENDER("loggedInUserGender"),
        SMOKES("loggedInUserSmokes");

        private String key;

        private ParameterKeys(String key){
            this.key = key;
        }

        @Override
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
    private ArrayList<Route> routes = new ArrayList<>();
    private String smokes;
    private String gender;
    private int wantsBoy;
    private int wantsGirl;
    private int wantsSmoker;
    private String rating;

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

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String url){
        this.photoUrl = url;
    }

    public String getGender() {
        return gender;
    }

    public boolean isMale(){
        return "male".equalsIgnoreCase(gender);
    }

    public boolean isFemale(){
        return "female".equalsIgnoreCase(gender);
    }

    public boolean isSmoker(){
        return "1".equals(smokes);
    }

    public String getSmokes() {
        return smokes;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSmokes(String smokes) {
        this.smokes = smokes;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getWantsBoy() {
        return wantsBoy;
    }

    public void setWantsBoy(int wantsBoy) {
        this.wantsBoy = wantsBoy;
    }

    public int getWantsGirl() {
        return wantsGirl;
    }

    public void setWantsGirl(int wantsGirl) {
        this.wantsGirl = wantsGirl;
    }

    public int getWantsSmoker() {
        return wantsSmoker;
    }

    public void setWantsSmoker(int wantsSmoker) {
        this.wantsSmoker = wantsSmoker;
    }
}