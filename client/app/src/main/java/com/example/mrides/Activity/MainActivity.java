/*
* Class MainActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.userDomain.User;
import com.example.mrides.userDomain.UserSerializer;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,ActivityObserver, FirebaseAuth.AuthStateListener,
        OnCompleteListener<AuthResult>{

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final int RC_SIGN_IN = 9001;
    private RequestHandler requestHandler = new RequestHandler();
    private User user;
    private GoogleSignInAccount googleuser;
    private ProgressDialog mProgressDialog;


    /**
     * When activity is created the APIs are requested through GoogleApiClient
     *
     * @param savedInstanceState The bundle is required for all activities to pass to the
     *                           parent class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        System.out.println("Refreshed token: " + FirebaseInstanceId.getInstance().getToken());
    }

    /**
     * When the Activity is initialized this activity is added as a listener to
     * the FirebaseAuthentication
     */
    @Override
    public void onStart() {

        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    /**
     * Remove this activity as a listener to the firebase authentication.
     */
    @Override
    public void onStop() {

        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    /**
     * When the user clicks the Get started button, the user is then brought to the home page
     *
     * @param view
     */
    public void googleSignIn(View view) {

        if (requestHandler.isInternetConnected(this)){
            pleaseWait(this);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }
    }

    /**
     * Result from the sign in. See google documentation
     * https://developers.google.com/identity/sign-in/android/
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
            firebaseAuthWithGoogle(acct);
        } else {

            System.out.println("Failed");
        }
    }

    /**
     *  When the user selects the back button from the Sign in page, the user will be brought
     *  back to the Android home page. This prevents the user from accesing the application without
     *  logging in.
     */
    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        googleuser = acct;
        AuthCredential credential = GoogleAuthProvider.getCredential(googleuser.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,this);
    }


    /**
     * Response returned by the requestHandler
     *
     * @param response A string response formatted in a json string returned from the request handler
     */
    @Override
    public void Update(String response) {

        requestHandler.detach(this);
        System.out.println("Response: "+ response);
        if(response.contains("User")) {
            requestHandler.attach(this);
            requestHandler.httpPostStringRequest("http://" + getString(R.string.web_server_ip) +
                            "/updateDeviceId.php", UserSerializer.getParameters(user),
                    RequestHandler.URLENCODED, this);
        }
    }

    /**
     * Method called when the application can't connect to Google sign in API
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This method handles loging in and logging out the user.
     * Here is where the user credentials are recieved
     * @param firebaseAuth
     */
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseuser = firebaseAuth.getCurrentUser();

        if (firebaseuser != null && googleuser!= null) {
            // User is signed in
            user = new User(firebaseuser,googleuser);
            requestHandler.setUser(user);
            requestHandler.attach(this);
            requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)+"/register_user.php",
                    UserSerializer.getParameters(user), RequestHandler.URLENCODED,
                    this);
            System.out.println("onAuthStateChanged:signed_in:" + firebaseuser.getUid());
            System.out.println("onAuthStateChanged:email:" + firebaseuser.getEmail());
            System.out.println("onAuthStateChanged:profil" + firebaseuser.getPhotoUrl());
        } else {
            System.out.println("onAuthStateChanged:signed_out");
        }
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        System.out.println("signInWithCredential:onComplete:" + task.isSuccessful());
        mProgressDialog.dismiss();
        Intent intent = new Intent(MainActivity.this, HomePage.class);
        startActivity(intent);

        if (!task.isSuccessful()) {

            System.out.println( task.getException());
            Toast.makeText(MainActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void pleaseWait(Context context){
        mProgressDialog = ProgressDialog.show(context, "Please wait.",
                "Processing Data", true);
    }

}
