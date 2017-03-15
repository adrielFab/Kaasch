package com.example.mrides.userDomain;


import java.util.HashMap;
import java.util.Map;

public class PassengerSerializer {

    public static Map<String, String> getParameters(User user){
        Map<String, String> map = new HashMap<>();
        map.put("passengerEmail", user.getEmail());
        map.put("passengerLastName", user.getLastName());
        map.put("passengerFirstName",user.getFirstName());
        map.put("passengerDisplayName", user.getDisplayName());
        map.put("passengerDeviceId", user.getDeviceId());
        map.put("passengerProfileUrl", user.getPhotoUrl());
        return map;
    }
}
