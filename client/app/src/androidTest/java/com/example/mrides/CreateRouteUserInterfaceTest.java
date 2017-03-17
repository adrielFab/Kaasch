package com.example.mrides;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import com.example.mrides.Activity.CreateRouteActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class CreateRouteUserInterfaceTest {

    @Rule
    public ActivityTestRule<CreateRouteActivity> mActivityRule = new ActivityTestRule<>(CreateRouteActivity.class);

    @Test
    public void testValidInputs(){

        onView(withId(R.id.editTextStart)).perform(typeText("H3S 1V2"));
        onView(withId(R.id.editTextDestination)).perform(typeText("Bell Centre"));
        onView(withId(R.id.buttonFindPath)).perform(click());
        Espresso.closeSoftKeyboard();
    }
    @Test
    public void testStartInputInvalid(){

        onView(withId(R.id.editTextStart)).perform(typeText(""));
        onView(withId(R.id.editTextDestination)).perform(typeText("Bell Centre"));
        onView(withId(R.id.buttonFindPath)).perform(click());
    }
    @Test
    public void testDestinationInputInvalid(){

        onView(withId(R.id.editTextStart)).perform(typeText("H3S 1V2"));
        onView(withId(R.id.editTextDestination)).perform(typeText(""));
        onView(withId(R.id.buttonFindPath)).perform(click());
    }
    @Test
    public void testInvalidInputs(){

        onView(withId(R.id.editTextStart)).perform(typeText(""));
        onView(withId(R.id.editTextDestination)).perform(typeText(""));
        onView(withId(R.id.buttonFindPath)).perform(click());
    }

}
