package com.example.mrides.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mrides.R;
import com.example.mrides.controller.RequestHandler;
import com.example.mrides.login.LoginFirstTime;

public class FirstTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup genderRadioGroup;
    private RadioGroup smokerRadioGroup;

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
        if ("Smoker".equals(smoker)) {
            smokerBit = "1";
        }
        sendData(smokerBit, gender);
    }

    /**
     * This method sends the data to the server
     */
    public void sendData(String smokerBit, String gender) {
        RequestHandler.getUser().setSmokes(smokerBit);
        RequestHandler.getUser().setGender(gender);
        Log.i("USER", gender + "badddd" + smokerBit);
        LoginFirstTime firstTimeLggedIn = new LoginFirstTime(this);
        firstTimeLggedIn.registerUser();
    }

}
