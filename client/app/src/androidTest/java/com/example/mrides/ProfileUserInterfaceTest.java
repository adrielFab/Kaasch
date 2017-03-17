package com.example.mrides;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.mrides.Activity.ProfileActivity;

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
public class ProfileUserInterfaceTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void searchTextDisplay(){

        // Verifies if all the texts are displayed
        onView(withId(R.id.textViewFirstName)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.textViewEmail)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.textViewRating)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.textViewRatingValue)).check(ViewAssertions.matches(isDisplayed()));

    }

    @Test
    public void searchButtons(){

        // Verifies if invite is displayed and button is clickable
        onView(withText("Invite")).check(matches(isDisplayed()));
        onView(withId(R.id.buttonInvite)).perform(click());

        // Verifies if cancel is displayed and button is clickable
        onView(withText("Cancel")).check(matches(isDisplayed()));
        onView(withId(R.id.buttonCancel)).perform(click());
    }

}
