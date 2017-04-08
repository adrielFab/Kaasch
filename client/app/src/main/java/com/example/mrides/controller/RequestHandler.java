/*
* Class RequestHandler
*
* 03/04/17
*/
package com.example.mrides.controller;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.RequestQueueSingleton;
import com.example.mrides.userDomain.User;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is designed to handle requests on behalf of activites and other services.
 * This class uses volley to perform GET and POST requests.
 * This class is the subject and the Observers are the Activities. When a response is recieved the
 * request handler will notifiy the activites with the response.
 */

public class RequestHandler implements Subject{

    private ArrayList<ActivityObserver> observers = new ArrayList<>();
    private static User user;
    public static final String URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";

    public void setUser(User user){

        this.user = user;
    }

    public static User getUser(){

        return user;
    }

    /**
     * Method used to initiate a post request. A StringRequest is used to initiate a post request.
     * A StringRequest is used by volley to initiate HTTP requests, however the response will be returned
     * as a string. The string will have to be converted
     * @param url The url to connect to
     * @param parameters The body of the post request. Key value paires are part of the Map
     * @param contentType The content type of the request. Ex: application/json, application/x-www-urlencoded
     * @param context The context of the activity initiating the request
     */
    public void httpPostStringRequest(String url, final Map<String,String> parameters,
                                      final String contentType, Context context){

        if(!isInternetConnected(context)){
            return;
        }
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Notify(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("RequestHandler", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return parameters;
            }
            @Override
            public String getBodyContentType() {
                return contentType;
            }

        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * The GET request method responsible for initiating get requests on behalf of the activites.
     * @param url Url to connect to
     * @param context The context initiating the get request
     */
    public void httpGetStringRequest(String url, Context context){

        if(!isInternetConnected(context)){
            return;
        }
        StringRequest stringRequest = new StringRequest
                (url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Notify(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("RequestHandler", error.getMessage());
                    }
                });
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    /**
     * This method checks to see if the internet is connected. If the internet is not connected then
     * a message is shown that the internet is not connected.
     * @param context
     * @return
     */
    public boolean isInternetConnected(Context context){

        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        System.out.println("check");
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.wifi_not_found)
                    .setTitle(R.string.ok);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return isConnected;
    }


    /**
     * @see com.example.mrides.controller.RequestHandler#attach(ActivityObserver)
     *
     */
    @Override
    public void attach(ActivityObserver observerToAdd) {

        if(observers.contains(observerToAdd)){
            return;
        }
        observers.add(observerToAdd);
    }

    /**
     * @see com.example.mrides.controller.RequestHandler#detach(ActivityObserver)
     */
    @Override
    public void detach(ActivityObserver observerToRemove) {
        observers.remove(observerToRemove);
    }

    /**
     * @see com.example.mrides.controller.RequestHandler#Notify(String)
     */
    @Override
    public void Notify(String response) {
        for(ActivityObserver e : observers){

            e.Update(response);
        }
    }
}
