package com.example.mrides;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mrides.Domain.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInRequestHandler {

    private boolean validAccout = false; // flag for authentic google account
    private User user;

    public boolean authenticateGoogleAccount(Context context,String clientId){
        // Request a string response from the provided URL.
        String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + clientId;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        validAccout = true;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        validAccout = false;
                    }
                });
        // Add the request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
        return validAccout;
    }

    public void logInUser(Context context){

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, context.getString(R.string.url)+"/login.php", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                        /*Gson gson = new Gson();
                        try {
                            user = gson.fromJson(response.getJSONObject("user").toString(), User.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }



}
