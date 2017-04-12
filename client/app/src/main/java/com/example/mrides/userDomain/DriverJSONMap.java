package com.example.mrides.userDomain;


import java.util.HashMap;
import java.util.Map;

public class DriverJSONMap {

    public enum ParameterKeys{

        EMAIL("driverEmail"),
        LASTNAME("driverLastName"),
        FIRSTNAME("driverFirstName"),
        DEVICEID("driverDeviceId"),
        PROFILEURL("driverProfileUrl"),
        GENDER("driverGender"),
        SMOKES("driverSmokes");

        private String key;

        private ParameterKeys(String key){
            this.key = key;
        }

        @Override
        public String toString(){
            return this.key;
        }
    }

    public static Map<String, String> getParameters(User driver) {
        Map<String, String> map = new HashMap<>();
        map.put(ParameterKeys.EMAIL.toString(), driver.getEmail());
        map.put(ParameterKeys.LASTNAME.toString(), driver.getLastName());
        map.put(ParameterKeys.FIRSTNAME.toString(), driver.getFirstName());
        map.put(ParameterKeys.DEVICEID.toString(), driver.getDeviceId());
        map.put(ParameterKeys.PROFILEURL.toString(), driver.getPhotoUrl());
        if(driver.getGender()!=null) { //when the user logs in for the first time the gender is not set
            map.put(User.ParameterKeys.GENDER.toString(), driver.getGender());
            map.put(User.ParameterKeys.SMOKES.toString(), driver.getSmokes());
        }
        return map;
    }
}
