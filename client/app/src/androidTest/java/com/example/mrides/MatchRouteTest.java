package com.example.mrides;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.mrides.Activity.MainActivity;
import DirectionModel.Matcher;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import DirectionModel.Route;
import static junit.framework.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class MatchRouteTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testValideDistance() {
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
    // Test the side effect of matchRoute() method on ArrayList <User> userOnMapCatalog
    public void testMatchRoute() {
        Route route = new Route();
        Matcher matcher = new Matcher(route);
        ArrayList <User> userOnMapCatalog = new ArrayList<>();
        List <LatLng> routeOfUser = new ArrayList <LatLng>();

        User e = new User();
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng end1 = new LatLng(45.495211,-73.637070);

        LatLng start2 = new LatLng(45.498672,-73.631370);
        LatLng end2 = new LatLng(45.495211,-73.637070);

        routeOfUser.add(start2);
        routeOfUser.add(end2);

        route.setStartLocation(start1);
        route.setEndLocation(end1);
        userOnMapCatalog.add(e);
        matcher.setUserMapCatalog(userOnMapCatalog);

        int userSize = matcher.getUserMapCatalog().size();
        matcher.matchRoute(routeOfUser);

        assertEquals(matcher.getUserMapCatalog().size(),userSize);
    }




}
