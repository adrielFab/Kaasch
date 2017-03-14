/*
* Class SingInRequestHandler
*
* 03/04/17
*/
package com.example.mrides;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class SignInRequestHandler {


    public void logInUser(Context context, final String clientId) {

        StringRequest stringRequest = new StringRequest
                (Request.Method.POST, context.getString(R.string.googleVerificationURL) +clientId,
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
                //params.put("email", user.getEmail());
                //params.put("displayName", user.getDisplayName());
                return params;
            }
            @Override
            public String getBodyContentType() {

                return "application/x-www-form-urlencoded";
            }

        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
