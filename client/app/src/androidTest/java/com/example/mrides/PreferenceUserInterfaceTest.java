package com.example.mrides;



import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.PreferencePageActivity;

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
    public ActivityTestRule<PreferencePageActivity> mActivityRule = new ActivityTestRule<>(PreferencePageActivity.class);

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
    public void testNextButton(){

        // Clicks next button to redirect to following page
        onView(withId(R.id.buttonGoToCreate)).perform(click());

    }

}
