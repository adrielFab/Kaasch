package com.example.mrides;//package com.example.mrides;

import android.content.Context;
import com.example.mrides.Activity.CreateRouteActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import DirectionModel.Route;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {


    @Test
    public void assertDecodePoly(){

        String start = "Montreal";
        String end = "Ottawa";

        ObtainDirection direction = new ObtainDirection(listener,start,end);

        Method method;
        // Encoded polyline for going from one location to another
        String encoded ="au|tGnsg`MxfIdP";
        // Decoded polyline for the same locations
        String decoded = "[lat/lng: (45.53569,-73.6084), lat/lng: (45.48324,-73.61115)]";
        try {
            method = direction.getClass().getDeclaredMethod("decodePoly", String.class);
            method.setAccessible(true);
            encoded=(String)method.invoke(direction,encoded).toString();
        }catch (Exception e){
            System.out.println("Exception caught: "+e.getMessage());
        }
        assertEquals(encoded, decoded);

    }

    @Test
    public void assertCreateUrl(){

        String start = "Montreal";
        String end = "Ottawa";

        ObtainDirection direction = new ObtainDirection(listener,start,end);

        Method method;
        String url=null;
        String exceptedUrl = "https://maps.googleapis.com/maps/api/directions/json?origin="+start+"&destination="+end;

        try {
            method = direction.getClass().getDeclaredMethod("createUrl");
            method.setAccessible(true);
            url=(String)method.invoke(direction);

            // Getting Google API key
            Field f = direction.getClass().getDeclaredField("GOOGLE_API_KEY");
            f.setAccessible(true);
            String APIkey = (String) f.get(direction);
            // Inserting Google API key into excepted URL
            exceptedUrl+="&key="+APIkey;
        }catch (Exception e){
            System.out.println("Exception caught: "+e.getMessage());
        }

        assertEquals(url,exceptedUrl);

    }


}
