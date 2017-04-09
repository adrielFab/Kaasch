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
     * @param passenger The passenger we want to be sent in a POST request
     * @return Return a map of key value pair of passenger attributes.
     *
     */
    //TODO the paramter needs to be changed to a passenger in the future. For now user works
    public static Map<String, String> getParameters(Passenger passenger) {
        Map<String, String> map = new HashMap<>();
        map.put(Passenger.ParameterKeys.EMAIL.toString(), "deezgloriousnutsgg@gmail.com");
        map.put(Passenger.ParameterKeys.LASTNAME.toString(), passenger.getLastName());
        map.put(Passenger.ParameterKeys.FIRSTNAME.toString(),passenger.getFirstName());
        map.put(Passenger.ParameterKeys.DEVICEID.toString(), "criMxY4hoy8:APA91bEt9_PXI4ymqYdZ_fIqydDfKKuNyMZ6xRBAeyH-_SRrO_B4KP582_c5Vaggp-d7Ph68kJug_2ojHO5D_hsbyKV7h3iAkKhj-feMw7QnD6AHIN3BkM2jBAiuRg5VI6fvE-sNvWl9");
        map.put(Passenger.ParameterKeys.PROFILEURL.toString(), "photoUrl");
        map.put(Passenger.ParameterKeys.SEARCHID.toString(), String.valueOf(passenger.getSearchId()));
        if(passenger.getGender()!=null) { //when the user logs in for the first time the gender is not set
            map.put(Passenger.ParameterKeys.GENDER.toString(), passenger.getGender());
            map.put(Passenger.ParameterKeys.SMOKES.toString(), passenger.getSmokes());
        }
        return map;
    }
}
