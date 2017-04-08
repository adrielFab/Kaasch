//package com.example.mrides;
//
//
//import android.support.test.rule.ActivityTestRule;
//import android.support.test.runner.AndroidJUnit4;
//
//import com.example.mrides.Activity.TempMainActivity;
//import com.example.mrides.userDomain.User;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.lang.reflect.Field;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//
//@RunWith(AndroidJUnit4.class)
//public class SignInTest {
//
//
//    @Rule
//    public ActivityTestRule<TempMainActivity> mActivityRule = new ActivityTestRule<>(
//            TempMainActivity.class);
//
//    private User user;
//
//    @Test
//    public void testEmailContext() {
//        try {
//            user = new User("h@hotmail","displayName");
//            Field f = mActivityRule.getActivity().getClass().getDeclaredField("user"); //NoSuchFieldException
//            f.setAccessible(true);
//            f.set(mActivityRule.getActivity(), user);
//
//        } catch (IllegalAccessException e) {
//              Log.e("SignInTest", e.getMessage());

//        }
//        catch(NoSuchFieldException e){
//              Log.e("SignInTest", e.getMessage());
//        }
//        onView(withId(R.id.email)).perform(click())
//                .check(matches(isDisplayed()));
//
//
//    }
//
//
//}
//
//
