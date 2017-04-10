package com.example.mrides.login;


import android.app.ProgressDialog;
import android.content.Intent;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.FirstTimeActivity;
import com.example.mrides.Activity.HomePage;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.UserSerializer;

public class LoginFirstTime implements ActivityObserver{

    private FirstTimeActivity activity;
    private RequestHandler requestHandler = new RequestHandler();
    private ProgressDialog mProgressDialog;


    public LoginFirstTime(FirstTimeActivity activity){
        this.activity = activity;
    }

    /**
     * Method that directs the user to their HomePage after registration
     * of the new user
     * @param response A string response formated in a json string returned from the request handler
     */
    @Override
    public void Update(String response) {
        requestHandler.detach(this);
        mProgressDialog.dismiss();
        Intent intent = new Intent(activity, HomePage.class);
        activity.startActivity(intent);
    }

    /**
     * Method that sends the gender and isSmoker data of the new logged in user
     * back to the database
     */
    public void registerUser() {
        mProgressDialog = ProgressDialog.show(activity, "Please wait.",
                "Processing Data", true);
        requestHandler.attach(this);
        requestHandler.httpPostStringRequest("http://" + activity.getString(R.string.web_server_ip) +
                        "/register_user.php", UserSerializer.getParameters(RequestHandler.getUser()),
                RequestHandler.URLENCODED, activity);

    }
}
