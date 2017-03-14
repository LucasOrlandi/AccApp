package com.example.lucas.accapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTitle(getResources().getText(R.string.full_list_title));

        ArrayList<Activity> items = new ArrayList<Activity>();

        Activity new_item = new Activity(0, "other", 60);
        items.add(new_item);


        ArrayAdapter<Activity> itemsAdapter = new ArrayAdapter<Activity>(this, R.layout.activities_list_item, items);
    }
}
