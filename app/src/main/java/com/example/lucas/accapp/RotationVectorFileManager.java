package com.example.lucas.accapp;


import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class RotationVectorFileManager {

    private File file;
    private FileOutputStream fos;
    private PrintStream ps;

    public RotationVectorFileManager() { }

    public RotationVectorFileManager(Context context, String filename) {

        try {

            file = new File(context.getFilesDir(), filename);
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {

        return(file);
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

    public void delete() {

        file.delete();
    }
}
