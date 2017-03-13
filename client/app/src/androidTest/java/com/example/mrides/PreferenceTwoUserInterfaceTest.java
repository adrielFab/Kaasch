package com.example.mrides;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.PreferencesTwoActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PreferenceTwoUserInterfaceTest {

    @Rule
    public ActivityTestRule<PreferencesTwoActivity> mActivityRule = new ActivityTestRule<>(PreferencesTwoActivity.class);

    @Test
    public void searchUserPreferences(){
        // Verifies that all desired message are displayed
        onView(withText("Select Preferences")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I do not mind pets")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I do not mind smoking")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I do not mind women")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("I do not mind men")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void searchButton(){
        // Verifies if button matches with next and if it is clickable
        onView(withId(R.id.submitBtn)).check(matches(withText("Next")));
        onView(withId(R.id.submitBtn)).perform(click());
    }

}
