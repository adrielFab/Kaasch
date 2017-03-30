package com.example.mrides;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.mrides.Activity.CreateRouteActivity;
import com.example.mrides.userDomain.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import DirectionModel.Distance;
import DirectionModel.Duration;
import DirectionModel.Matcher;
import DirectionModel.Route;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class RouteCreationTestSuite {

    @Test
    public void testRouteCreation(){
        Route route = new Route();
        Distance distance = new Distance("km", 2);
        Duration duration = new Duration("hour",1);

        route.setDistance(distance);
        route.setDuration(duration);

        route.setStartAddress("H2S 3K5");
        route.setEndAddress("H2K 1Y1");
        route.setId(1);

        assertEquals(route.getDistance(),distance);
        assertEquals(route.getDuration(),duration);
        assertEquals(route.getId(),1);
        assertEquals(route.getStartAddress(),"H2S 3K5");
        assertEquals(route.getEndAddress(),"H2K 1Y1");
    }

}
