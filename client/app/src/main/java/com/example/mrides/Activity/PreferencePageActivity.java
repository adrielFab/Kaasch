/*
* Class PreferencePageActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mrides.R;

import java.util.Calendar;

public class PreferencePageActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDatePicker, btnTimePicker;
    private ImageView btnSmokePref, btnMalePref, btnFemalePref;
    private EditText txtDate, txtTime;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private int [] preferenceChoice = {1, 1, 1};

    /**
     * Method that is called to load the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_page);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        btnSmokePref = (ImageView) findViewById(R.id.img_smoke);
        btnMalePref = (ImageView) findViewById(R.id.img_boy);
        btnFemalePref = (ImageView) findViewById(R.id.img_girl);

        btnMalePref.setOnClickListener(this);
        btnSmokePref.setOnClickListener(this);
        btnFemalePref.setOnClickListener(this);


    }

    /**
     * Method moves to the next page (CreateRouteActivity)
     * @param view
     */
    public void goToCreateRoute(View view) {

        Intent intent = new Intent(PreferencePageActivity.this, CreateRouteActivity.class);
        startActivity(intent);
    }

    /**
     * Handles the click events of the preference buttons and the date buttons
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_date:
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.btn_time:
                // Get Current Time
                final Calendar ct = Calendar.getInstance();
                mHour = ct.get(Calendar.HOUR_OF_DAY);
                mMinute = ct.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;

            case R.id.img_smoke:
                changeButtonPreference(btnSmokePref, 0);
                break;

            case R.id.img_boy:
                changeButtonPreference(btnMalePref, 1);
                break;

            case R.id.img_girl:
                changeButtonPreference(btnFemalePref, 2);
                break;

        }

    }

    /**
     * Method that creates an animation for the preference images. If the user
     * wants to add a constraint to an preference, the user will click the image
     * and it will turn red. Reverting it back turns the image to green
     * @param img
     * @param index
     */
    public void changeButtonPreference(ImageView img, int index) {
        if(preferenceChoice[index] == 1) {
            img.setBackgroundColor(Color.RED);
            preferenceChoice[index] = 0;
        } else {
            img.setBackgroundColor(Color.GREEN);
            preferenceChoice[index] = 1;
        }

    }
}
