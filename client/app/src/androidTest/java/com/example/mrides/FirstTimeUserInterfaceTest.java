package com.example.mrides;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.FirstTimeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class FirstTimeUserInterfaceTest {

    @Rule
    public ActivityTestRule<FirstTimeActivity> mActivityRule = new ActivityTestRule<>(FirstTimeActivity.class);

    @Test
    public void testFemaleSmokerPreferences(){

        // Selects Female and Smoker then clicks I am ready button to redirect to next page
        onView(withId(R.id.radioWoman)).perform(click());
        onView(withId(R.id.Driver)).perform(click());
        onView(withId(R.id.firsTimeButton)).perform(click());
    }
    @Test
    public void testFemaleNonSmokerPreferences(){

        // Selects Female and Non Smoker then clicks I am ready button to redirect to next page
        onView(withId(R.id.radioWoman)).perform(click());
        onView(withId(R.id.Passenger)).perform(click());
        onView(withId(R.id.firsTimeButton)).perform(click());
    }
    @Test
    public void testMaleSmokerPreferences(){

        // Selects Female and Non Smoker then clicks I am ready button to redirect to next page
        onView(withId(R.id.radioMan)).perform(click());
        onView(withId(R.id.Driver)).perform(click());
        onView(withId(R.id.firsTimeButton)).perform(click());
    }
    @Test
    public void testMaleNonSmokerPreferences(){

        // Selects Female and Non Smoker then clicks I am ready button to redirect to next page
        onView(withId(R.id.radioWoman)).perform(click());
        onView(withId(R.id.Passenger)).perform(click());
        onView(withId(R.id.firsTimeButton)).perform(click());
    }
}
