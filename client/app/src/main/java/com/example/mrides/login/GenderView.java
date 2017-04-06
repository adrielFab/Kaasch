package com.example.mrides.login;


import android.app.ProgressDialog;
import android.content.Intent;

import com.example.mrides.Activity.ActivityObserver;
import com.example.mrides.Activity.HomePage;
import com.example.mrides.Activity.MainActivity;
import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.UserSerializer;

public class GenderView implements ActivityObserver{

    private MainActivity mainActivity;
    private RequestHandler requestHandler = new RequestHandler();
    @Override
    public void Update(String response) {
        if(response.equals("True")){
            Intent intent = new Intent(mainActivity, HomePage.class);
            mainActivity.startActivity(intent);
        }
        else{
            Intent intent = new Intent(mainActivity, HomePage.class);
            mainActivity.startActivity(intent);
        }
    }

    public void checkFirstTimeLogin(MainActivity mainActivity, ProgressDialog mProgressDialog) {
        mProgressDialog.dismiss();
        requestHandler.attach(this);
        requestHandler.httpPostStringRequest("http://" + mainActivity.getString(R.string.web_server_ip) +
                        "/is_first_time.php", UserSerializer.getParameters(RequestHandler.getUser()),
                RequestHandler.URLENCODED, mainActivity);

    }
}
