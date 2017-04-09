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
        map.put(Passenger.ParameterKeys.EMAIL.toString(), passenger.getEmail());
        map.put(Passenger.ParameterKeys.LASTNAME.toString(), passenger.getLastName());
        map.put(Passenger.ParameterKeys.FIRSTNAME.toString(),passenger.getFirstName());
        map.put(Passenger.ParameterKeys.DEVICEID.toString(), passenger.getDeviceId());
        map.put(Passenger.ParameterKeys.PROFILEURL.toString(), passenger.getPhotoUrl());
        map.put(Passenger.ParameterKeys.SEARCHID.toString(), String.valueOf(passenger.getSearchId()));
        if(passenger.getGender()!=null) { //when the user logs in for the first time the gender is not set
            map.put(Passenger.ParameterKeys.GENDER.toString(), passenger.getGender());
            map.put(Passenger.ParameterKeys.SMOKES.toString(), passenger.getSmokes());
        }
        return map;
    }
}
