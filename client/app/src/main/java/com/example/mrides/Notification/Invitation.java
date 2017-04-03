package com.example.mrides.Notification;


public class Invitation {

    private String driverEmail;
    private String driverReview;
    private String driverProfilePicRul;
    private String driverDisplayName;

    public Invitation(String driverEmail, String driverReview, String driverProfilePicRul,
                      String driverDisplayName) {
        this.driverEmail = driverEmail;
        this.driverReview = driverReview;
        this.driverProfilePicRul = driverProfilePicRul;
        this.driverDisplayName = driverDisplayName;
    }
}
