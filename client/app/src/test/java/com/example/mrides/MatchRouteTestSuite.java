package com.example.mrides;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.Activity.MainActivity;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import DirectionModel.Matcher;
import DirectionModel.Route;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class MatchRouteTestSuite {

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
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testInvalideDistance(){
        Matcher matcher = new Matcher();
        LatLng start1 = new LatLng(45.498672,-73.631370);
        LatLng start2 = new LatLng(45.498619,-73.631310);

        LatLng end1 = new LatLng(45.495211,-73.1);
        LatLng end2 = new LatLng(45.99,-73.637065);

        assertNotEquals(matcher.validateDistance(start1, start2),
                matcher.validateDistance(end1, end2));
    }

    @Test
    // Test validateDistance method can match two coordinates within a reasonable distance
    public void testInvalideDistanceInput(){
        Matcher matcher = new Matcher();
        LatLng start1 = new LatLng(45498672,-73631370);
        LatLng start2 = new LatLng(45498619,-73631310);

        LatLng end1 = new LatLng(45.485211,-73.1);
        LatLng end2 = new LatLng(45.495219,-73.637065);

        assertNotEquals(matcher.validateDistance(start1, start2),
                matcher.validateDistance(end1, end2));
        assertNotEquals(matcher.validateDistance(start1, start1),
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
        matcher.setUserOnMapCatalog(userOnMapCatalog);

        int userSize = matcher.getUserOnMapCatalog().size();
        matcher.matchRoute(routeOfUser);

        assertEquals(matcher.getUserOnMapCatalog().size(),userSize);
        assertEquals(route.getStartLocation(),start1);
        assertEquals(route.getEndLocation(),end1);
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
