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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.RequestQueueSingleton;
import com.example.mrides.userDomain.User;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DirectionModel.IPersistanceObject;

public class RequestHandler implements Subject{

    private ArrayList<ActivityObserver> observers = new ArrayList<>();
    private static User user;

    public void setUser(User user){

        this.user = user;
    }

    public static User getUser(){

        return user;
    }

    public void httpPostStringRequest(String url, final Map<String,String> parameters, Context context){

        if(!isInternetConnected(context)){
            return;
        }
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, url,
                        new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                
                return parameters;
            }
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded";
            }

        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

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

                        error.printStackTrace();
                    }
                });
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

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

    @Override
    public void attach(ActivityObserver observerToAdd) {

        if(observers.contains(observerToAdd)){
            return;
        }
        observers.add(observerToAdd);
    }

    @Override
    public void detach(ActivityObserver observerToRemove) {
        observers.remove(observerToRemove);
    }

    @Override
    public void Notify(String response) {

        for(ActivityObserver e : observers){

            e.responseReceived(response);
        }
    }
}
