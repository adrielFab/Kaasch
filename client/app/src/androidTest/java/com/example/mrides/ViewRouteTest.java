//package com.example.mrides;
//
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.example.mrides.Activity.CreateRouteActivity;
//import com.example.mrides.Activity.ProfileActivity;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;
//
//@RunWith(AndroidJUnit4.class)
//public class ViewRouteTest {
//
//    @Rule
//    public ActivityTestRule <CreateRouteActivity> mActivityRule = new ActivityTestRule<>(CreateRouteActivity.class);
//
//    @Test
//    public void viewRoute(){
//        onView(withId(R.id.editTextStart)).perform(typeText("H2S 2K5"));
//        onView(withId(R.id.editTextStart))
//                .check(matches(withText("H2S 2K5")));
//
//        onView(withId(R.id.editTextDestination)).perform(typeText("Concordia Montreal"));
//        onView(withId(R.id.editTextDestination))
//                .check(matches(withText("Concordia Montreal")));
//
//        onView(withId(R.id.buttonFindPath)).perform(click());
//    }
//
//}
