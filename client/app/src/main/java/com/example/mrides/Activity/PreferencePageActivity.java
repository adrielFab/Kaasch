/*
* Class PreferencePageActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.mrides.R;

import java.util.Calendar;

public class PreferencePageActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDatePicker;
    private Button btnTimePicker;
    private ImageView btnSmokePref;
    private ImageView btnMalePref;
    private ImageView btnFemalePref;
    private EditText txtDate;
    private EditText txtTime;
    private RadioGroup radioTypeGroup;
    private RadioButton radioTypeButton;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private boolean [] isPreferenceChoiceSelected = {true, true, true};

    /**
     * Method that is called to load the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference_page);

        radioTypeGroup = (RadioGroup) findViewById(R.id.radioGroupType);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);

        txtDate = (EditText) findViewById(R.id.in_date);
        txtTime = (EditText) findViewById(R.id.in_time);

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
        int selectedId = radioTypeGroup.getCheckedRadioButtonId();
        radioTypeButton = (RadioButton) findViewById(selectedId);

        String choice = radioTypeButton.getText().toString();

        if(choice.equals("Driver")) {
            Intent intent = new Intent(PreferencePageActivity.this, CreateRouteDriverActivity.class);
            TextView tv_in_date = (TextView) findViewById(R.id.in_date);
            TextView tv_in_time = (TextView) findViewById(R.id.in_time);
            TextView tv_title = (TextView) findViewById(R.id.in_title);

            String in_date = tv_in_date.getText().toString();
            String in_time = tv_in_time.getText().toString();
            String title = tv_title.getText().toString();
            //Create the bundle
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("in_date", in_date);
            bundle.putString("in_time", in_time);
            //Add the bundle to the intent
            intent.putExtras(bundle);

            startActivity(intent);
        } else if (choice.equals("Passenger")) {
            Intent intent = new Intent(PreferencePageActivity.this, CreateRoutePassengerActivity.class);
            startActivity(intent);
        }
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
                changeButtonPreference(btnSmokePref, 0, R.drawable.smoking_not_accepted, R.drawable.smoking_accepted);
                break;

            case R.id.img_boy:
                toggleBoyGirl(btnMalePref, 1, R.drawable.men_not_accepted, R.drawable.men_accepted);
                break;

            case R.id.img_girl:
                toggleBoyGirl(btnFemalePref, 2, R.drawable.women_not_accepted, R.drawable.women_accepted);
                break;
            default:
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
    private void changeButtonPreference(ImageView img, int index, int img1, int img2) {
        if(isPreferenceChoiceSelected[index] == true) {
            img.setImageResource(img1);
            isPreferenceChoiceSelected[index] = false;
        } else {
            img.setImageResource(img2);
            isPreferenceChoiceSelected[index] = true;
        }
    }

    private void toggleBoyGirl(ImageView img, int selectedPreferenceIndex, int img1, int img2){
        if(selectedPreferenceIndex==1 && isPreferenceChoiceSelected[2]==false){
            //reject men accept females
            changeButtonPreference(btnMalePref, 1, R.drawable.men_not_accepted, R.drawable.men_accepted);
            changeButtonPreference(btnFemalePref, 2, R.drawable.women_not_accepted, R.drawable.women_accepted);
        }
        else if(selectedPreferenceIndex==2 && isPreferenceChoiceSelected[1]==false){
            //reject females accept males
            changeButtonPreference(btnMalePref, 1, R.drawable.men_not_accepted, R.drawable.men_accepted);
            changeButtonPreference(btnFemalePref, 2, R.drawable.women_not_accepted, R.drawable.women_accepted);
        }
        else{
            changeButtonPreference(img, selectedPreferenceIndex, img1, img2);
        }
    }
}
