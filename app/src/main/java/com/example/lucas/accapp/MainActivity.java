package com.example.lucas.accapp;

import android.*;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button button_show_all;
    private Button button_show_random;

    private  boolean allowed;

    private DataBaseManager db;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        db = new DataBaseManager(database);

        allowed = false;

        button_show_all = (Button) findViewById(R.id.button_show_all);
        button_show_random = (Button) findViewById(R.id.button_show_random);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, 0);

            } else {

                allowed = true;
            }

        } else {

            allowed = true;
        }
    }

    public void onResume() {
        super.onResume();

        if(allowed) {

            /* Gets the Google account of Android device */
            Account[] accounts = AccountManager.get(getApplicationContext()).getAccountsByType("com.google");
            db.addNewUser(Build.SERIAL, Build.MODEL, accounts[0].name);
        }

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

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch(requestCode) {

            case 0: {

                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    allowed = true;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);

        return(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.settings):
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);

                return(true);

            default:
                return(super.onOptionsItemSelected(item));
        }
    }
}
