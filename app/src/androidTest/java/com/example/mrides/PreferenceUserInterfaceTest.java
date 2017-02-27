package com.example.mrides;



import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PreferenceUserInterfaceTest {

    @Rule
    public ActivityTestRule<preferencePage> mActivityRule = new ActivityTestRule<>(preferencePage.class);

    @Test
    public void searchRouteSpecifications(){
        // Verifies if route name is displayed
        onView(withText("Route Name :")).check(matches(isDisplayed()));
        // Verifies if departure date is displayed
        onView(withText("Departure Date:")).check(matches(isDisplayed()));
        // Verifies if arrival time is displayed
        onView(withText("Arrival Time:")).check(matches(isDisplayed()));
    }
    @Test
    public void searchDateTime(){
        // Verifies if the time and date are displayed
        onView(withId(R.id.in_date)).check(matches(isDisplayed()));
        onView(withId(R.id.in_time)).check(matches(isDisplayed()));
    }

    @Test
    public void searchDateButton(){
        // Verifies if button's name is change for departure date and if it is clickable
        onView(withId(R.id.btn_date)).check(matches(withText("Change")));
        onView(withId(R.id.btn_date)).perform(click());
    }
    @Test
    public void searchTimeButton(){
        // Verifies if button's name is change for arrival date and if it is clickable
        onView(withId(R.id.btn_time)).check(matches(withText("Change")));
        onView(withId(R.id.btn_time)).perform(click());
    }
    @Test
    public void checkUser(){
        // Verifies if the button matches with driver then checks if it clickable
        // and if it stays on driver's button choice
        onView(withId(R.id.Driver)).check(matches(withText("Driver")));
        onView(withId(R.id.Driver)).perform(click());
        onView(withId(R.id.Driver)).check(matches(isChecked()));
        // Verifies if the button matches with passenger then checks if it clickable
        // and if it stays on passenger's button choice
        onView(withId(R.id.Passenger)).check(matches(withText("Passenger")));
        onView(withId(R.id.Passenger)).perform(click());
        onView(withId(R.id.Passenger)).check(matches(isChecked()));
    }

}
