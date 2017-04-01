package com.example.lucas.accapp;

import com.google.firebase.database.FirebaseDatabase;


public class DataBaseManager {

    private FirebaseDatabase database;

    public DataBaseManager(FirebaseDatabase db) {

        database = db;
    }

    public void addNewUser(String userID, String deviceModel, String email) {

        User user = new User(deviceModel, email);
        database.getReference().child("users").child(userID).setValue(user);
    }
}
