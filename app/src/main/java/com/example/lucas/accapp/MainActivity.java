package com.example.lucas.accapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button button_show_all;
    private Button button_show_random;

    private FirebaseDatabase database;

    /*
        * TODO: tentar colocar a variavel database como global entre as activities.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        DataBaseManager db = new DataBaseManager(database);

        /* Gets the Google account of Android device */
        Account[] accounts = AccountManager.get(getApplicationContext()).getAccountsByType("com.google");
        db.addNewUser(Build.SERIAL, Build.MODEL, accounts[0].name);

        button_show_all = (Button) findViewById(R.id.button_show_all);
        button_show_random = (Button) findViewById(R.id.button_show_random);
    }

    public void onResume() {
        super.onResume();

        button_show_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        button_show_random.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListRandomActivity.class);
                startActivity(intent);
            }
        });
    }
}
