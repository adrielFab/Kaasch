package com.example.mrides.controller;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.R;
import com.example.mrides.RequestQueueSingleton;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import DirectionModel.IPersistanceObject;

public class RequestHandler implements Subject{

    private ArrayList<ActivityObserver> observers = new ArrayList<>();


    public void postStringRequest(String path, IPersistanceObject parcel, Context context){
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, context.getString(R.string.url) +"/testing.php",
                        new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        //System.out.println("Response: " + response);
                        //JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
                        //System.out.println("email" + obj.get("email"));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
            @Override
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded";
            }

        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void attach(ActivityObserver observerToAdd) {
        observers.add(observerToAdd);
    }

    @Override
    public void detach(ActivityObserver observerToRemove) {
        observers.remove(observerToRemove);
    }

    @Override
    public void Notify() {
        for(ActivityObserver e : observers){
            e.responseReceived();
        }
    }
}
