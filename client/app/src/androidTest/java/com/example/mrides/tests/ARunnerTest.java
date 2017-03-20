package com.example.mrides.tests;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;

import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.userDomain.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class ARunnerTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<CreateRouteActivity> mActivityRule = new ActivityTestRule<>(CreateRouteActivity.class);

    @Test
    public void testValidInputs(){

        User user = new User();
        user.setEmail("Hello");
        user.getEmail();
        assertNotNull(user.getEmail());
    }


}
