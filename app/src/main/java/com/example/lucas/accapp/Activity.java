package com.example.lucas.accapp;

import java.io.Serializable;

public class Activity implements Serializable{

    private int id;
    private String name;
    private long time;

    public Activity() { }

    public Activity(int id, String name, long time) {

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

    public void setTime(long time) {

        this.time = time;
    }

    public long getTime() {

        return this.time;
    }
}
