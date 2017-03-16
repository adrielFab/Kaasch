package com.example.mrides;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import com.example.mrides.Activity.CreateRouteActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class CreateRouteUserInterfaceTest {

    @Rule
    public ActivityTestRule<CreateRouteActivity> mActivityRule = new ActivityTestRule<>(CreateRouteActivity.class);

    @Test
    public void searchStart(){
        // Verifies if enter start address is displayed
        onView(withHint("Enter start address")).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void searchDestination(){
        // Verifies if enter destination address is displayed
        onView(withHint("Enter destination address")).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void searchButtonFindPath(){
        // Verifies if button is available and can be clicked
        onView(withId(R.id.buttonFindPath)).perform(click());
        // Verifies find path text on button
        onView(withText("Find path")).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void searchDistance(){
        // Verifies if distance is displayed
        onView(withId(R.id.textDistance)).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void searchDuration(){
        // Verifies if duration is displayed
        onView(withId(R.id.textDuration)).check(ViewAssertions.matches(isDisplayed()));
    }


}
