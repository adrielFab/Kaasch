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
    public static Map<String, String> getParameters(User user) {
        Map<String, String> map = new HashMap<>();
        map.put("passengerEmail", "adriel.fab@gmail.com");
        map.put("passengerLastName", user.getLastName());
        map.put("passengerFirstName",user.getFirstName());
        map.put("passengerDeviceId", "dZqG2lST6uo:APA91bF1f5ZkYW5xTNCawaEzXbgEeWe6eB74F6rFyJGRsWfTTxJX6C2Nxv54b1eS5QK6suhwYxX8GkL-W9C3z7HlbYZYZSFqtm8NMsygvDVsYaosUx2BBoU5ZB2pU9UV3aji_qej-my5");
        map.put("passengerProfileUrl", "photoUrl");
        return map;
    }
}
