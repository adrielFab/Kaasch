package com.example.mrides;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

/**
 * Created by Deez Nuts on 2/21/2017.
 */

public class RequestHandler {

    private String url = "url";

    public void authenticateGoogleAccount(Context context,String clientId){
        // Request a string response from the provided URL.
        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + clientId;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });
        // Add the request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }
}
