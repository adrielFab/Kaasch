package com.example.mrides;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class SignInTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Mock
    MainActivity mainActivity;
    @Mock
    View view;

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context context = Mockito.mock(Context.class);
      //  when(mainActivity.googleSignIn(new View(appContext))).thenReturn("asdf");
        //assertEquals("com.example.mrides", appContext.getPackageName());
    }


}


