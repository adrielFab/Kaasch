package com.example.mrides;

import android.support.test.rule.ActivityTestRule;
import com.example.mrides.Activity.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import java.util.Date;
import DirectionModel.Preference;
import DirectionModel.Route;
import static junit.framework.Assert.assertNotNull;

public class RouteTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    // Test the initiation of a route with empty date object
    // Expected output: route.getDate() returns current date
    public void testDateRouteCreation() {
        Route route = new Route();
        Date date = new Date();

        route.setDate(date);
        assertNotNull(route.getDate());
    }

    @Test
    // Test the initiation of a route with given preference
    // Expected output: preference not null
    public void testPreferenceRouteCreation() {
        Route route = new Route();
        Preference preference = new Preference(true, true, true);

        route.setPreference(preference);
        assertNotNull(route.getPreference());
    }

    @Test
    // Test the initiation of a route with given title
    // Expected output: title not null
    public void testTitleRouteCreation() {
        Route route = new Route();
        String title = "Route to university";

        route.setTitle(title);
        assertNotNull(route.getTitle());
    }
}
