package com.example.mrides;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mrides.Domain.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignInRequestHandler {

    private boolean validAccout = false; // flag for authentic google account
    private User user;

    public void authenticateGoogleAccount(Context context, String clientId) {
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
                        error.printStackTrace();
                        validAccout = false;
                    }
                });
        // Add the request to the RequestQueue.
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);
    }

    public void logInUser(Context context) {

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, context.getString(R.string.url) +"/testing.php", new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response: " + response);
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
                params.put("email", "harrison@hotmail.com");
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

    public void testServer(Context context){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "http://192.175.117.171:10922/test.json",null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                       // JsonObject obj = new JsonParser().parse(response).getAsJsonObject();
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
                params.put("email", "harrison@hotmail.com");
                return params;
            }
            @Override
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded";
            }

        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsObjRequest);

    }


}
