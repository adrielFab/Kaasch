package com.example.mrides;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.ProfileActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ProfileUserInterfaceTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void searchButtons(){

        // Clicks invite button for output
        onView(withId(R.id.buttonInvite)).perform(click());

        // Click cancel button to close dialog
        onView(withId(R.id.buttonCancel)).perform(click());
    }

}
