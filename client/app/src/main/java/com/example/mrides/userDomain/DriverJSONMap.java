package com.example.mrides.userDomain;


import java.util.HashMap;
import java.util.Map;

public class DriverJSONMap {

    public static Map<String, String> getParameters(User driver) {
        Map<String, String> map = new HashMap<>();
        map.put("driverEmail", driver.getEmail());
        map.put("driverLastName", driver.getLastName());
        map.put("driverFirstName", driver.getFirstName());
        map.put("driverDeviceId", driver.getDeviceId());
        map.put("driverProfileUrl", driver.getPhotoUrl());
        return map;
    }
}
