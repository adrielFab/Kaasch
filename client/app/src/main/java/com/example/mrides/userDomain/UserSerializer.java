package com.example.mrides.userDomain;


import java.util.HashMap;
import java.util.Map;



public class UserSerializer {

    public static Map<String, String> getParameters(User user){
        Map<String, String> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("lastName", user.getLastName());
        map.put("displayName", user.getDisplayName());
        map.put("deviceId", user.getDeviceId());

        return map;
    }
}
