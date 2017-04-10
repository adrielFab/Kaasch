package com.example.mrides.Invitation;

import com.example.mrides.userDomain.Passenger;
import com.example.mrides.userDomain.User;

public class Invitation {

    private String driverRouteName;
    private User driver;
    private Passenger passenger;

    public Invitation(String driverRouteName, User driver, Passenger passenger) {
        this.driverRouteName = driverRouteName;
        this.driver = driver;
        this.passenger = passenger;
    }

}
