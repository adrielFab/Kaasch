package com.example.mrides;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.PreferencePageActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PreferenceUserInterfaceTest {

    @Rule
    public ActivityTestRule<PreferencePageActivity> mActivityRule = new ActivityTestRule<>(PreferencePageActivity.class);

    @Test
    public void testInputRouteName(){
        // Input route name and click next button directing to following page
        onView(withId(R.id.in_title)).perform(typeText("testing"));
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testEmptyInputRouteName(){
        // Input empty route name and click next button directing to following page
        onView(withId(R.id.in_title)).perform(typeText(""));
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testDriverChoice(){
        // Select driver and click next button directing to following page
        onView(withId(R.id.Driver)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());

    }
    @Test
    public void testPassengerChoice(){
        // Select passenger and click next button directing to following page
        onView(withId(R.id.Passenger)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());

    }
    @Test
    public void testChangeTimeButton(){

        // Clicks change button to output clock
        onView(withId(R.id.btn_time)).perform(click());
    }
    @Test
    public void testChangeDateButton(){

        // Clicks change button to output calendar
        onView(withId(R.id.btn_date)).perform(click());
    }
    @Test
    public void testSmokeMaleFemalePreference(){

        // Clicks next button with preferences smoking/male/female
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testSmokeNoMaleFemalePreference(){

        // Clicks next button with no male as preference
        onView(withId(R.id.img_boy)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void  testSmokeMaleNoFemalePreference(){

        // Clicks next button with no female as preference
        onView(withId(R.id.img_girl)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testNoSmokeMaleFemalePreference(){

        // Clicks next button with no smoking preference
        onView(withId(R.id.img_smoke)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testNoSmokeNoMaleFemalePreference(){

        // Clicks next button with no smoking & no male preferences
        onView(withId(R.id.img_smoke)).perform(click());
        onView(withId(R.id.img_boy)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }
    @Test
    public void testNoSmokeNoMaleNoFemalePreference(){

        // Clicks next button with no smoking & no female preferences
        onView(withId(R.id.img_smoke)).perform(click());
        onView(withId(R.id.img_girl)).perform(click());
        onView(withId(R.id.buttonGoToCreate)).perform(click());
    }

}
