package com.example.lucas.accapp;


import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;

public class StorageManager {

    private StorageReference storage;

    public StorageManager(StorageReference reference) {

        storage = reference;
    }

    public void saveFile(String path, File file) {

        storage.child("sensors").child(path).child(Uri.fromFile(file).getLastPathSegment()).putFile(Uri.fromFile(file));
    }
}
