package com.example.mrides;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Activity.MainActivity;
import com.example.mrides.userDomain.Matcher;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import DirectionModel.Route;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
public class MatchRouteLogicTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testValideDistance(){
        Matcher matcher = new Matcher();
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng start2 = new LatLng(45.498619,-73.631310);

        LatLng end1 = new LatLng(45.495211,-73.637070);
        LatLng end2 = new LatLng(45.495219,-73.637065);

        assertEquals(matcher.validateDistance(start1, start2),
                matcher.validateDistance(end1, end2));
    }

    @Test
    // Test the side effect of matchRoute() method on ArrayList <User> userOnMapCatalog
    public void testMatchRoute(){
        Matcher matcher = new Matcher();
        ArrayList <User> userOnMapCatalog = new ArrayList<>();
        List <LatLng> routeOfUser = new ArrayList <LatLng>();

        User e = new User();
        Route route = new Route();
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

    @Test
    // Test the creation of progress bar on CreateRouteActivity
    public void testShouldCreateProgressBar(){
        Context routeActivity = new CreateRouteActivity();
        TestOnCancelListener cancelListener = new TestOnCancelListener();
        ProgressDialog progressDialog = ProgressDialog.show(routeActivity, "Title", "Message", true, true, cancelListener);
        assertThat(progressDialog.getContext(), is(routeActivity));
        progressDialog.cancel();
        assertThat(cancelListener.onCancelDialogInterface, is((DialogInterface) progressDialog));
    }

    private static class TestOnCancelListener implements DialogInterface.OnCancelListener {
        public DialogInterface onCancelDialogInterface;

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            onCancelDialogInterface = dialogInterface;

        }
    }


}
