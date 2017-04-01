package com.example.lucas.accapp;

/**
 * Created by lucas on 3/31/17.
 */

public class User {

    private String deviceModel;
    private String email;

    public User() {
        // Default constructor required for calls to DataBaseReference.setValue(Object obj
    }

    public User(String model, String emailAddress) {

        deviceModel = model;
        email = emailAddress;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getEmail() {
        return email;
    }
}
