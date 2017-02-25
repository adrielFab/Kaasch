package com.example.mrides;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mrides.Domain.User;


public class LoginSessionSingleton {

    private static LoginSessionSingleton session;
    private User user;


    private LoginSessionSingleton(User logedInUser) {
        user = logedInUser;
        session = getInstance();
    }

    public static synchronized LoginSessionSingleton getInstance(User user) {
        if (session == null) {
            session = new LoginSessionSingleton(user);
        }

        return session;
    }

    public RequestQueue getUser() {
        return mRequestQueue;
    }
}
