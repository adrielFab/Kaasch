package com.example.mrides.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.mrides.R;

/*
* DefaultActivity
*
* 1.0.0
*
* March 4, 2017
*
*/

public abstract class DefaultActivity extends AppCompatActivity {

    protected void checkWifi(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.wifi_not_found)
                    .setTitle(R.string.ok);
            AlertDialog dialog = builder.create();
        }
    }

}
