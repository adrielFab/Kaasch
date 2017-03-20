package com.example.mrides;//package com.example.mrides;

import android.content.Context;
import com.example.mrides.Activity.CreateRouteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import DirectionModel.Route;
import DirectionModel.RouteDeserializer;

import static android.R.attr.direction;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RouteDeserializerTestSuite {


    @Test
    public void testDecodePoly(){

        String start = "Montreal";
        String end = "Ottawa";

        RouteDeserializer deserializer = new RouteDeserializer();
        // Encoded polyline for going from one location to another
        String encoded ="au|tGnsg`MxfIdP";
        // Decoded polyline for the same locations
        String decoded = "[lat/lng: (45.53569,-73.6084), lat/lng: (45.48324,-73.61115)]";

        encoded = deserializer.decodePoly(encoded).toString();
        assertEquals(encoded, decoded);
    }

    public boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

}
