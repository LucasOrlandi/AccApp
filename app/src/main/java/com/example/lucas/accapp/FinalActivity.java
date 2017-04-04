package com.example.lucas.accapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

public class FinalActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager gyroscopeManager;
    private Sensor gyroscope;

    private SensorManager magnetometerManager;
    private Sensor magnetometer;

    private SensorManager rotationVectorManager;
    private Sensor rotationVector;

    private SensorManager accelerometerManager;
    private Sensor accelerometer;

    private Button button_start;
    private Button button_reset;

    private Chronometer chronometer;
    private Activity selectedActivity;  // Activity selected by the user
    private CountDownTimer countDownTimer;  // Makes a timer

    private Accelerometer acc_file;
    private Gyroscope gyro_file;
    private Magnetometer magn_file;
    private RotationVector rv_file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        /*
            * Makes the gyroscope sensor ready
            *
            * Use TYPE_GYROSCOPE to get the rate of rotation with drift compensation;
            * Use TYPE_GYROSCOPE_UNCALIBRATED to get the rate of rotation without drift compensation.
        */
        gyroscopeManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = gyroscopeManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        /* Makes the magnetometer sensor ready */
        magnetometerManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = magnetometerManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        /* Makes the rotation vector sensor ready */
        rotationVectorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationVector = rotationVectorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        /*
            * Makes the accelerometer sensor ready
            *
            * Use TYPE_ACCELEROMETER to the acceleration force, including the force of gravity;
            * Use TYPE_LINEAR_ACCELERATION to the acceleration force, excluding the force of gravity.
        */
        accelerometerManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = accelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /* Makes the views ready to be used */
        TextView tv_selected_activity_name = (TextView) findViewById(R.id.tv_selected_activity);
        button_start = (Button) findViewById(R.id.button_start);
        button_reset = (Button) findViewById(R.id.button_reset);

        chronometer = (Chronometer) findViewById(R.id.chrono_activity_time);

        /* Gets data from the Intent */
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

                accelerometerManager.unregisterListener(FinalActivity.this);
                accelerometerManager.flush(FinalActivity.this);
                closeAllFiles();

                /* Sends file to database */
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

    protected void onPause() {
        super.onPause();

        countDownTimer.cancel();
    }

    protected void onStop() {
        super.onStop();

        countDownTimer.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            acc_file.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gyro_file.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            magn_file.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            float scalar_componet = event.values[3];
            rv_file.write(x, y, z, scalar_componet);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void start() {

        createAllFiles();

        gyroscopeManager.registerListener(FinalActivity.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        magnetometerManager.registerListener(FinalActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        rotationVectorManager.registerListener(FinalActivity.this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        accelerometerManager.registerListener(FinalActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
        countDownTimer.start();
    }

    private void reset() {

        countDownTimer.cancel();
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
    }

    private void createAllFiles() {

        acc_file= new Accelerometer(Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_accelerometer) + getResources().getString(R.string.file_extension));
        gyro_file = new Gyroscope(Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_gyroscope) + getResources().getString(R.string.file_extension));
        magn_file = new Magnetometer(Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_magnetometer) + getResources().getString(R.string.file_extension));
        rv_file = new RotationVector(Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_rotationVector) + getResources().getString(R.string.file_extension));
    }

    private void closeAllFiles() {

        if(acc_file.exist())
            acc_file.close();

        if(gyro_file.exist())
            gyro_file.close();

        if(magn_file.exist())
            magn_file.close();

        if(rv_file.exist())
            rv_file.close();
    }
}
