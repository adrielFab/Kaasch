package com.example.mrides.userDomain;



import java.util.HashMap;
import java.util.Map;



public class UserSerializer {

    public static Map<String, String> getParameters(User user){
        Map<String, String> map = new HashMap<>();
        map.put(User.ParameterKeys.EMAIL.toString(), user.getEmail());
        map.put(User.ParameterKeys.LASTNAME.toString(), user.getLastName());
        map.put(User.ParameterKeys.FIRSTNAME.toString(), user.getFirstName());
        map.put(User.ParameterKeys.DEVICEID.toString(), user.getDeviceId());
        map.put(User.ParameterKeys.PROFILEURL.toString(), user.getPhotoUrl());
        return map;
    }
}
