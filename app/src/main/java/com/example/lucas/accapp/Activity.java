package com.example.lucas.accapp;

public class Activity {

    private int id;
    private String name;
    private float time;

    public Activity() { }

    public Activity(int id, String name, float time) {

        this.id = id;
        this.name = name;
        this.time = time;  // Seconds
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {

        return this.id;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }

    public void setTime(float time) {

        this.time = time;
    }

    public float getTime() {

        return this.time;
    }
}
