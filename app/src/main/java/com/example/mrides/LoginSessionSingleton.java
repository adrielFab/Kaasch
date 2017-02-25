package com.example.mrides;


import com.example.mrides.Domain.User;


public class LoginSessionSingleton {

    private static LoginSessionSingleton session;
    private static User user;


    private LoginSessionSingleton() {

    }

    public static synchronized LoginSessionSingleton getInstance() {
        if (session == null) {
            session = new LoginSessionSingleton();
        }

        return session;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
