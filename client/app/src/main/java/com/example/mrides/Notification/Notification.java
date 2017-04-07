package com.example.mrides.Notification;


public class Notification {

    private String driverEmail;
    private String driverReview;
    private String driverProfilePicRul;
    private String firstName;
    private String lastName;

    public Notification(String driverEmail, String driverReview, String driverProfilePicRul,
                        String firstName, String lastName) {
        this.driverEmail = driverEmail;
        this.driverReview = driverReview;
        this.driverProfilePicRul = driverProfilePicRul;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getDriverReview() {
        return driverReview;
    }

    public String getDriverProfilePicRul() {
        return driverProfilePicRul;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
