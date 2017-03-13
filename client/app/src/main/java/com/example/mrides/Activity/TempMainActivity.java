/*
* Class TempMainActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;
import com.example.mrides.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;

import org.parceler.Parcels;

public class TempMainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private User user;
    private RequestHandler requestHandler = new RequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_main);
        // Request only the user's ID token, which can be used to identify the
        // user securely for the backend. This will contain the user's basic
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , (GoogleApiClient.OnConnectionFailedListener) this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        TextView text = (TextView) findViewById(R.id.email);
        user = requestHandler.getUser();
        System.out.println("Parcing." + user.getFirebaseAcct().getEmail());
        if(user !=null)
            text.setText(user.getEmail());
    }

    public void signOut(View view) {

        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        Intent intent = new Intent(TempMainActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
    }

    public void createRoute(View view){

        Intent intent = new Intent(TempMainActivity.this, CreateRouteActivity.class);
        startActivity(intent);
    }

    public void preferences(View view){

        Intent intent = new Intent(TempMainActivity.this, PreferencePageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
