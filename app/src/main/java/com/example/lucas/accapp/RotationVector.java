package com.example.lucas.accapp;


import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class RotationVector {

    private File file;
    private FileOutputStream fos;
    private PrintStream ps;

    public RotationVector() { }

    public RotationVector(String filename) {

        try {

            file = new File(Environment.getExternalStorageDirectory() + "/Documents/" + filename);
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean exist() {

        return(ps != null);
    }

    public void write(float x, float y, float z, float scalar_component) {

        ps.println(x + " " + y + " " + z + " " + scalar_component);
    }

    public void close() {

        ps.close();
    }
}