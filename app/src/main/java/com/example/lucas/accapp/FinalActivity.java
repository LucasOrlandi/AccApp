package com.example.lucas.accapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class FinalActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager accelerometerManager;
    private Sensor accelerometer;

    private Button button_start;
    private Button button_reset;

    private File file;
    private FileWriter writer;

    private TextView x_acc;  // Displays the X value of the accelerometer
    private TextView y_acc;  // Displays the Y value of the accelerometer
    private TextView z_acc;  // Displays the Z value of the accelerometer

    private Chronometer chronometer;
    private Activity selectedActivity;  // Activity selected by the user
    private CountDownTimer countDownTimer;  // Makes a timer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        /*
            * Makes the accelerometer sensor ready
            *
            * Use TYPE_ACCELEROMETER to the acceleration force, including the force of gravity;
            * Use TYPE_LINEAR_ACCELERATION to the acceleration force, excluding the force of gravity.
        */
        accelerometerManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = accelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /*
            * Makes the views ready to be used
        */
        TextView tv_selected_activity_name = (TextView) findViewById(R.id.tv_selected_activity);
        button_start = (Button) findViewById(R.id.button_start);
        button_reset = (Button) findViewById(R.id.button_reset);

        chronometer = (Chronometer) findViewById(R.id.chrono_activity_time);
        x_acc = (TextView) findViewById(R.id.tv_acc_x);
        y_acc = (TextView) findViewById(R.id.tv_acc_y);
        z_acc = (TextView) findViewById(R.id.tv_acc_z);

        /*
            * Gets data from the Intent
        */
        selectedActivity = (Activity) getIntent().getSerializableExtra("selectedActivity");

        tv_selected_activity_name.setText(selectedActivity.getName());
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
    }

    protected void onResume() {
        super.onResume();

        /* To make a beep */
        final ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);

        countDownTimer = new CountDownTimer(selectedActivity.getTime()*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                chronometer.setBase(SystemClock.elapsedRealtime() - millisUntilFinished);
            }

            @Override
            public void onFinish() {
                chronometer.setBase(SystemClock.elapsedRealtime());
                beep.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP);

                if(writer != null) {
                    try {
                        writer.close();
                        writer = null;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    public void onPause() {
        super.onPause();

        if(writer != null) {
            try {
                writer.close();
                writer = null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        x_acc.setText(String.format("%f", x));
        y_acc.setText(String.format("%f", y));
        z_acc.setText(String.format("%f", z));

        try {
            if(writer != null)
                writer.write(x+", "+y+", "+z+"\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void start() {

        accelerometerManager.registerListener(FinalActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);  // Qual usar: SENSOR_DELAY_NORMAL OU SENSOR_DELAY_FASTEST?
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);

        try {
            file = new File(getApplicationContext().getFilesDir(), "file.txt");
            writer = new FileWriter(file, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        countDownTimer.start();
    }

    private void reset() {

        countDownTimer.cancel();
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);

        if(writer != null) {
            try {
                writer.close();
                writer = null;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
