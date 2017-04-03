package com.example.lucas.accapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Gyroscope {

    private File file;
    private FileOutputStream fos;
    private PrintStream ps;

    public Gyroscope() { }

    public Gyroscope(String filename) {

        try {

            file = new File(filename);
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean exists() {

        return(ps != null);
    }

    public void write(float x, float y, float z) {

        ps.println(x + " " + y + " " + z);
    }

    public void close() {

        ps.close();
    }
}
