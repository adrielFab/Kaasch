package com.example.mrides;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** got to sign in page */
    public void goToSignIn(View view) {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(intent);
    }








}
