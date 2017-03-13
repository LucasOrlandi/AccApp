package com.example.lucas.accapp;

/**
 * Created by lucas on 3/13/17.
 */

public class Activity {

    private int id;
    private String name;
    private float time;

    public Activity(int id, String name, float time) {

        this.id = id;
        this.name = name;
        this.time = time;
    }

    public int getId() {

        return this.id;
    }

    public String getName() {

        return this.name;
    }

    public float getTime() {

        return this.time;
    }
}
