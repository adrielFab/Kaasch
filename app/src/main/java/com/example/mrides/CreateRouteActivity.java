package com.example.mrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateRouteActivity extends AppCompatActivity {

    private Button btnFindPath;
    private EditText etStart;
    private EditText etDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        etStart = (EditText) findViewById(R.id.etStart);
        etDestination = (EditText) findViewById(R.id.etDestination);

        
    }
}
