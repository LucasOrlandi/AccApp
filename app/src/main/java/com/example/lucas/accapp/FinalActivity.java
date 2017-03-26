package com.example.lucas.accapp;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.io.Serializable;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        final Activity selectedActivity = (Activity) getIntent().getSerializableExtra("selectedActivity");

        TextView tv_selected_activity_name = (TextView) findViewById(R.id.tv_selected_activity);
        final Chronometer chronometer = (Chronometer) findViewById(R.id.chrono_activity_time);
        Button button_start = (Button) findViewById(R.id.button_start);
        Button button_reset = (Button) findViewById(R.id.button_reset);


        tv_selected_activity_name.setText(selectedActivity.getName());
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
                chronometer.start();
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
            }
        });
    }
}
