package com.example.mrides.userDomain;



import java.util.HashMap;
import java.util.Map;



public class UserSerializer {

    public static Map<String, String> getParameters(User user){
        Map<String, String> map = new HashMap<>();
        map.put("loggedInUserEmail", user.getEmail());
        map.put("loggedInUserLastName", user.getLastName());
        map.put("loggedInUserFirstName", user.getFirstName());
        map.put("loggedInUserDisplayName", user.getDisplayName());
        map.put("loggedInUserDeviceId", user.getDeviceId());
        map.put("loggedInUserProfileUrl", user.getPhotoUrl());
        return map;
    }
}
