package com.example.mrides.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class FirstTimeActivity extends AppCompatActivity implements View.OnClickListener, ActivityObserver {

    private RadioGroup genderRadioGroup;
    private RadioGroup smokerRadioGroup;
    private RequestHandler requestHandler = new RequestHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        ImageView car = (ImageView) findViewById(R.id.imageViewCar);
        car.animate().translationXBy(-3000f).setDuration(4000);

        TextView welcomeTitle = (TextView) findViewById(R.id.welcomeTitle);
        TextView welcomeDescription = (TextView) findViewById(R.id.welcomeDescription);

        welcomeTitle.animate().alpha(1f).setDuration(4000);
        welcomeDescription.animate().alpha(1f).setDuration(4000);

        genderRadioGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        smokerRadioGroup = (RadioGroup) findViewById(R.id.smokerRadioGroup);

        Button firstTimeButton = (Button) findViewById(R.id.firsTimeButton);
        firstTimeButton.setOnClickListener(this);

    }

    /**
     * Method that handles the click event
     * The click will store the values set by the user in terms of gender and smoker
     * @param view
     */
    @Override
    public void onClick(View view) {
        int selectedGender = genderRadioGroup.getCheckedRadioButtonId();
        int selectedSmoker = smokerRadioGroup.getCheckedRadioButtonId();

        RadioButton genderRadio = (RadioButton) findViewById(selectedGender);
        RadioButton smokerRadio = (RadioButton) findViewById(selectedSmoker);

        String gender = genderRadio.getText().toString();
        String smoker = smokerRadio.getText().toString();
        String smokerBit = "0";
        if (smoker.equals("Smoker")) {
            smokerBit = "1";
        }
        sendData(smokerBit, gender);
        Intent intent = new Intent(FirstTimeActivity.this, HomePage.class);
        startActivity(intent);



    }

    /**
     * This method sends the data to the server
     */
    public void sendData(String smokerBit, String gender) {

        Map<String,String> jsonBody = new HashMap<>();
        jsonBody.put("email", RequestHandler.getUser().getEmail());
        jsonBody.put("smokes", smokerBit);
        jsonBody.put("gender", gender);
        Log.i("USERR", gender + "badddd" + smokerBit);
        requestHandler.attach(this);
        requestHandler.httpPostStringRequest("http://"+getString(R.string.web_server_ip)  +
                        "/user_characteristics.php",jsonBody,
                RequestHandler.URLENCODED ,this);
    }

    @Override
    public void Update(String response) {
        Log.i("RESPONSE", response);
    }
}
