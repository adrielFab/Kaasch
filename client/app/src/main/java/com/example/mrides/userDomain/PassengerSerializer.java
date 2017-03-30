package com.example.mrides.userDomain;


import java.util.HashMap;
import java.util.Map;

/**
 * This class converts a passenger reference to a Mapping so that the information can be sent
 * to our servers.
 */
public class PassengerSerializer {

    /**
     * This method converts the important passenger information into a mapping.
     *
     * @param user The passenger we want to be sent in a POST request
     * @return Return a map of key value pair of passenger attributes.
     *
     */
    //TODO the paramter needs to be changed to a passenger in the future. For now user works
    public static Map<String, String> getParameters(User user){
        Map<String, String> map = new HashMap<>();
        map.put("passengerEmail", user.getEmail());
        map.put("passengerLastName", user.getLastName());
        map.put("passengerFirstName",user.getFirstName());
        map.put("passengerDisplayName", "Adriel");
        //map.put("passengerDeviceId", user.getDeviceId());
        map.put("passengerDeviceId", "f4TBeBH3M7Q:APA91bHhiq-4egX6AOFbd-3tdL3d2OguCkbzevAAtWzshBTtIdLNzS0pQTOO408H3wx8oN0fZBdLa14jSGWQ4K_TajGeRfqZsd6c-_CrhPOovDqQL3NoeJ113vX2TpqKKLJaNpEZ9Gsi");
       // map.put("passengerProfileUrl", user.getPhotoUrl());
        map.put("passengerProfileUrl", "photoUrl");
        return map;
    }
}
