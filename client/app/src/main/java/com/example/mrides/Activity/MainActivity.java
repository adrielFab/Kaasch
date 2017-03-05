/*
* Class MainActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.example.mrides.R;
import com.example.mrides.SignInRequestHandler;
import com.example.mrides.controller.RequestHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends DefaultActivity implements
        GoogleApiClient.OnConnectionFailedListener, ActivityObserver{

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private RequestHandler requestHandler = new RequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    // onclick event for sign in button
    public void googleSignIn(View view) {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //result from the sign in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String idToken = acct.getIdToken();
            System.out.println("email: " + acct.getEmail());
            requestHandler.attach(this);
            requestHandler.getStringRequest(getString(R.string.googleVerificationURL)
                    +idToken,this);
            //SignInRequestHandler handler = new SignInRequestHandler();
            //handler.logInUser(this,idToken);
           // Intent intent = new Intent(MainActivity.this, TempMainActivity.class);
            //intent.putExtra("session", Parcels.wrap(user)); //pass data to another activity
            //startActivity(intent);
            /*User user = new User(acct);
            SignInRequestHandler signInRequestHandler = new SignInRequestHandler();
            Intent intent = new Intent(MainActivity.this, TempMainActivity.class);
            intent.putExtra("session", Parcels.wrap(user)); //pass data to another activity
            startActivity(intent);*/
            //signInRequestHandler.logInUser(this,user);
        } else {

            // Signed out, show unauthenticated UI.
            System.out.println("Failed");
        }
    }

    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        //TODO message must show up if gmail user account could not be optained
        //most likely related to no wifi connection
    }


    @Override
    public void responseReceived(String response) {

        requestHandler.detach(this);
        Intent intent = new Intent(MainActivity.this, TempMainActivity.class);
        //intent.putExtra("session", Parcels.wrap(user)); //pass data to another activity
        startActivity(intent);
    }
}
