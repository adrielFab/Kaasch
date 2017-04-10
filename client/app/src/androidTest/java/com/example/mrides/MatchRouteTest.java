package com.example.mrides;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.mrides.Activity.MainActivity;
import DirectionModel.Matcher;

import com.example.mrides.userDomain.Passenger;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import DirectionModel.Preference;
import DirectionModel.Route;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;


@RunWith(AndroidJUnit4.class)
public class MatchRouteTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testValidDistance() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng start2 = new LatLng(45.498619,-73.631310);

        LatLng end1 = new LatLng(45.495211,-73.637070);
        LatLng end2 = new LatLng(45.495219,-73.637065);

        assertEquals(matcher.validateDistance(start1, start2),
                matcher.validateDistance(end1, end2));
    }

    @Test
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testInvalidDistance() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng start2 = new LatLng(45.498619,-73.631310);

        LatLng end1 = new LatLng(49.495211,-75.122321);
        LatLng end2 = new LatLng(41.495219,-71.637065);

        assertNotEquals(matcher.validateDistance(start1, start2),
                matcher.validateDistance(end1, end2));
    }

    @Test
    // Test distance method can calculate the distance between two points defined by double
    // variables
    public void testDistance() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        double start1 = 45.498672;
        double start2 = -73.631310;

        double end1 = 45.495211;
        double end2 = -73.637065;

        assertEquals(matcher.distance(start1, start2, end1, end2), 0.3672438898160615);
    }

    @Test
    // Test distance method can calculate the distance between two points defined by double
    // variables
    public void testIntDistance() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        double start1 = 45;
        double start2 = -73;

        double end1 = 45;
        double end2 = -73;

        assertEquals(matcher.distance(start1, start2, end1, end2), 0.0);
    }

    @Test
    // Test distance method can calculate the distance between two points defined by double
    // variables
    public void testDoubleDistance() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        double start1 = 90/2;
        double start2 = -73.000000000000;

        double end1 = 90.05/2;
        double end2 = -73.0000001;
    }

    @Test
    // Test the side effect of matchRoute() method on ArrayList <User> userOnMapCatalog
    public void testMatchRouteSE() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        ArrayList <Passenger> userOnMapCatalog = new ArrayList<>();
        List <LatLng> routeOfUser = new ArrayList <LatLng>();

        Passenger e = new Passenger();
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng end1 = new LatLng(45.495211,-73.637070);

        LatLng start2 = new LatLng(45.498672,-73.631370);
        LatLng end2 = new LatLng(45.495211,-73.637070);

        routeOfUser.add(start2);
        routeOfUser.add(end2);

        userOnMapCatalog.add(e);
        matcher.setUserMapCatalog(userOnMapCatalog);

        int userSize = matcher.getUserMapCatalog().size();
        matcher.matchRoute(routeOfUser);

        assertEquals(matcher.getUserMapCatalog().size(),userSize);
    }

    @Test
    // Test match"able" routes on matchRoute() method on ArrayList <User> userOnMapCatalog
    public void testMatchRoute() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        ArrayList <Passenger> userOnMapCatalog = new ArrayList<>();
        List <LatLng> routeOfUser = new ArrayList <LatLng>();

        Passenger e = new Passenger();
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng end1 = new LatLng(45.495211,-73.637070);

        LatLng start2 = new LatLng(45.498672,-73.631370);
        LatLng end2 = new LatLng(45.495211,-73.637070);

        routeOfUser.add(start2);
        routeOfUser.add(end2);

        userOnMapCatalog.add(e);
        matcher.setUserMapCatalog(userOnMapCatalog);
        assertTrue(matcher.matchRoute(routeOfUser));
    }

    @Test
    public void testMatchMaleGenderPreference() {
        Preference preference = new Preference(true, true, true);
        User user = new User();

        user.setGender("male");
        user.setSmokes("0");
        assertTrue(preference.matchPreferences(user));
    }

    @Test
    public void testMatchFemaleGenderPreference() {
        Preference preference = new Preference(true, true, true);
        User user = new User();

        user.setGender("female");
        user.setSmokes("0");
        assertTrue(preference.matchPreferences(user));
    }

    @Test
    public void testMatchSmokerPreference() {
        Preference preference = new Preference(true, true, true);
        User user = new User();

        user.setGender("female");
        user.setSmokes("1");
        assertTrue(preference.matchPreferences(user));
    }

    @Test
    public void testUnmatchSmokerPreference() {
        Preference preference = new Preference(false, false, true);
        User user = new User();

        user.setGender("female");
        user.setSmokes("1");
        assertFalse(preference.matchPreferences(user));
    }

    @Test
    public void testUnmatchMalePreference() {
        Preference preference = new Preference(true, false, true);
        User user = new User();

        user.setGender("male");
        user.setSmokes("0");
        assertFalse(preference.matchPreferences(user));
    }

    @Test
    public void testUnmatchFemalePreference() {
        Preference preference = new Preference(false, true, true);
        User user = new User();

        user.setGender("female");
        user.setSmokes("0");
        assertFalse(preference.matchPreferences(user));
    }

    @Test
    public void testPartialUnmatchPreference() {
        Preference preference = new Preference(false, true, false);
        User user = new User();

        user.setGender("male");
        user.setSmokes("1");
        assertFalse(preference.matchPreferences(user));
    }

}
