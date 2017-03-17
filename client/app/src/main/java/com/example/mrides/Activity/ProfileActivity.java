package com.example.mrides.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;

import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements ActivityObserver{

    private RequestHandler requestHandler = new RequestHandler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void sendInvite(View view){
        requestHandler.attach(this);
    }

    @Override
    public void responseReceived(String response) {
        requestHandler.detach(this);
        //requestHandler.httpPostStringRequest(getString(R.string.web_server_ip),);
    }

    @Override
    public void responseReceived(Map<String, String> response) {

    }
}
