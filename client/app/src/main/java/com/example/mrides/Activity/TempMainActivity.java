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
    private RequestHandler requestHandler = new RequestHandler();
    private User user = requestHandler.getUser();

    /**
     * When activity is created the APIs are requested through GoogleApiClient
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .build();
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

    /**
     * Handles signing out the user and removing the FirebaseAuthentication credentials from
     * the current device.
     *http://stackoverflow.com/questions/38707133/google-firebase-sign-out-and-forget-user-in-android-app
     *
     * @param view
     */
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

    /**
     * Brings the user to the create route page.
     *
     * @param view
     */
    public void createRoute(View view){

        Intent intent = new Intent(TempMainActivity.this, CreateRouteActivity.class);
        startActivity(intent);
    }

    /**
     * Brings the user to the preference page.
     *
     * @param view
     */
    public void preferences(View view){

        Intent intent = new Intent(TempMainActivity.this, PreferencePageActivity.class);
        startActivity(intent);
    }

    /**
     * Method called when the application can't connect to Google sign in API
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
