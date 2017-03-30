package com.example.mrides;

import com.example.mrides.userDomain.User;

import org.junit.Test;

import DirectionModel.Matcher;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void match() throws Exception {
        Matcher match = new Matcher();
        assertEquals(match.getUserOnMapCatalog(), match.getUserOnMapCatalog());
    }

    @Test
    public void matchuser() throws Exception {
        User match = new User();
        assertEquals(match.getId(),match.getId());
    }
}