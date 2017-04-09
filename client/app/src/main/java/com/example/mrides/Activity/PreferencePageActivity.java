/*
* Class PreferencePageActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mrides.R;

import java.util.Calendar;

public class PreferencePageActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView btnSmokePref;
    private ImageView btnMalePref;
    private ImageView btnFemalePref;
    private EditText txtDate;
    private EditText txtTime;
    private RadioGroup radioTypeGroup;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
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

        Button btnDatePicker=(Button)findViewById(R.id.btn_date);
        Button btnTimePicker=(Button)findViewById(R.id.btn_time);

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

        setDateTime();
    }

    /**
     * Method moves to the next page (CreateRouteActivity)
     * @param view
     */
    public void goToCreateRoute(View view) {
        int selectedId = radioTypeGroup.getCheckedRadioButtonId();
        RadioButton radioTypeButton = (RadioButton) findViewById(selectedId);
        String choice = radioTypeButton.getText().toString();
        Intent intent = new Intent(PreferencePageActivity.this, CreateRouteActivity.class);
        TextView tvDate = (TextView) findViewById(R.id.in_date);
        TextView tvTime = (TextView) findViewById(R.id.in_time);
        TextView tvTitle = (TextView) findViewById(R.id.in_title);
        String inDate = tvDate.getText().toString();
        String inTime = tvTime.getText().toString();
        String title = tvTitle.getText().toString();

        if(title.isEmpty()) {
            Toast.makeText(PreferencePageActivity.this, R.string.input_route_name, Toast.LENGTH_SHORT).show();
        }
        else {
            //Create the bundle
            Bundle bundle = new Bundle();
            bundle.putString("in_title", title);
            bundle.putString("in_date", inDate);
            bundle.putString("in_time", inTime);
            bundle.putBoolean("likesSomes", isPreferenceChoiceSelected[0]);
            bundle.putBoolean("likesBoys", isPreferenceChoiceSelected[1]);
            bundle.putBoolean("likesGirls", isPreferenceChoiceSelected[2]);
            //Add the bundle to the intent
            intent.putExtras(bundle);
            if ("Driver".equals(choice)) {
                intent.putExtra("role", "driver");
                startActivity(intent);
            } else if ("Passenger".equals(choice)) {
                intent.putExtra("role", "passenger");
                startActivity(intent);
            }
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

                                txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year%100);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;

            case R.id.btn_time:
                // Get Current Time
                final Calendar ct = Calendar.getInstance();
                mHour = ct.get(Calendar.HOUR_OF_DAY);
                int mMinute = ct.get(Calendar.MINUTE);
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
        if(isPreferenceChoiceSelected[index]) {
            img.setImageResource(img1);
            isPreferenceChoiceSelected[index] = false;
        } else {
            img.setImageResource(img2);
            isPreferenceChoiceSelected[index] = true;
        }
    }

    /**
     *  If a gender prefence is being set to off while the other is already set to off, it will be set to on
     * @param img
     * @param selectedPreferenceIndex
     * @param img1
     * @param img2
     */
    private void toggleBoyGirl(ImageView img, int selectedPreferenceIndex, int img1, int img2) {
        if(selectedPreferenceIndex==1 && !isPreferenceChoiceSelected[2]) {
            //reject men accept females
            changeButtonPreference(btnMalePref, 1, R.drawable.men_not_accepted, R.drawable.men_accepted);
            changeButtonPreference(btnFemalePref, 2, R.drawable.women_not_accepted, R.drawable.women_accepted);
        }
        else if(selectedPreferenceIndex==2 && !isPreferenceChoiceSelected[1]) {
            //reject females accept males
            changeButtonPreference(btnMalePref, 1, R.drawable.men_not_accepted, R.drawable.men_accepted);
            changeButtonPreference(btnFemalePref, 2, R.drawable.women_not_accepted, R.drawable.women_accepted);
        }
        else {
            changeButtonPreference(img, selectedPreferenceIndex, img1, img2);
        }
    }

    /**
     * Sets the default date and time to current date and current time + 10mins
     */
    private void setDateTime() {
        //set current date
        Calendar c = Calendar.getInstance();
         mYear = c.get(Calendar.YEAR) %100;
         mMonth = c.get(Calendar.MONTH);
         mDay = c.get(Calendar.DAY_OF_MONTH);
        txtDate.setText(mDay + "-" + mMonth + "-" + mYear);
        //set current time + 10mins
         mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMin = c.get(Calendar.MINUTE) + 10;
        txtTime.setText(mHour + ":" + mMin);
    }
}
