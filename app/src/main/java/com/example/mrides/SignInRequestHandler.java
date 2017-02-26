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

import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;


public class SignInRequestHandler {



    public void logInUser(Context context, final User user) {
        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, context.getString(R.string.url) +"/testing.php", new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
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
                params.put("email", user.getEmail());
                params.put("displayName", user.getDisplayName());
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


}
