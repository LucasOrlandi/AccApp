package com.example.lucas.accapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListRandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_random);

        setTitle(getResources().getText(R.string.random_list_title));
    }
}
