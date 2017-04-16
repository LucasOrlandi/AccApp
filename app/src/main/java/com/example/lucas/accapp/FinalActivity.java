package com.example.lucas.accapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinalActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor rotationVector;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private Sensor gyroscope;

    private String date;

    private Button button_start;
    private Button button_reset;

    private Chronometer chronometer;
    private Activity selectedActivity;  // Activity selected by the user
    private CountDownTimer countDownTimer;  // Makes a timer

    private AccelerometerFileManager acc_fm;
    private GyroscopeFileManager gyro_fm;
    private MagnetometerFileManager magn_fm;
    private RotationVectorFileManager rv_fm;

    private LocationManager locationManager;
    private Location location;
    private StorageManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        sm = new StorageManager(FirebaseStorage.getInstance().getReference());

        /*
            * Makes the gyroscope sensor ready
            *
            * Use TYPE_GYROSCOPE to get the rate of rotation with drift compensation;
            * Use TYPE_GYROSCOPE_UNCALIBRATED to get the rate of rotation without drift compensation.
        */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        /* Makes the magnetometer sensor ready */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        /* Makes the rotation vector sensor ready */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        /*
            * Makes the accelerometer sensor ready
            *
            * Use TYPE_ACCELEROMETER to the acceleration force, including the force of gravity;
            * Use TYPE_LINEAR_ACCELERATION to the acceleration force, excluding the force of gravity.
        */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        /* Makes the views ready to be used */
        TextView tv_selected_activity_name = (TextView) findViewById(R.id.tv_selected_activity);
        button_start = (Button) findViewById(R.id.button_start);
        button_reset = (Button) findViewById(R.id.button_reset);

        chronometer = (Chronometer) findViewById(R.id.chrono_activity_time);

        /* Gets data from the Intent */
        selectedActivity = (Activity) getIntent().getSerializableExtra("selectedActivity");

        tv_selected_activity_name.setText(selectedActivity.getName());
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime() * 1000);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        } else {
            location = new Location(locationManager.GPS_PROVIDER);
            location.setLatitude(0);
            location.setLongitude(0);
        }

        Date mDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy--hh-mm-ss");
        date = sdf.format(mDate);
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

                stopSensorListener();
                closeAllFiles();

                /* Sends file to firebase's storage */
                storeAllFiles();
                deleteAllFiles();
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

        stopSensorListener();
        closeAllFiles();
        deleteAllFiles();
        countDownTimer.cancel();
    }

    protected void onStop() {
        super.onStop();

        stopSensorListener();
        closeAllFiles();
        deleteAllFiles();
        countDownTimer.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            acc_fm.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gyro_fm.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            magn_fm.write(x, y, z);
        }

        if(sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            float scalar_componet = event.values[3];
            rv_fm.write(x, y, z, scalar_componet);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void start() {

        createAllFiles();
        writeHeaders();
        startSensorListener();

        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
        countDownTimer.start();
    }

    private void reset() {

        stopSensorListener();
        countDownTimer.cancel();
        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
    }

    private void createAllFiles() {

        String acc_file = Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_accelerometer) + getResources().getString(R.string.file_extension);
        String gyro_file = Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_gyroscope) + getResources().getString(R.string.file_extension);
        String magn_file = Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_magnetometer) + getResources().getString(R.string.file_extension);
        String rv_file = Build.SERIAL + "-" + selectedActivity.getName().replaceAll("\\s+", "") + "-" + System.currentTimeMillis() + "-" + getResources().getString(R.string.sensor_rotationVector) + getResources().getString(R.string.file_extension);

        acc_fm = new AccelerometerFileManager(getApplicationContext(), acc_file);
        gyro_fm = new GyroscopeFileManager(getApplicationContext(), gyro_file);
        magn_fm = new MagnetometerFileManager(getApplicationContext(), magn_file);
        rv_fm = new RotationVectorFileManager(getApplicationContext(), rv_file);
    }

    private void closeAllFiles() {

        if(acc_fm.exist())
            acc_fm.close();

        if(gyro_fm.exist())
            gyro_fm.close();

        if(magn_fm.exist())
            magn_fm.close();

        if(rv_fm.exist())
            rv_fm.close();
    }

    private void deleteAllFiles() {

        acc_fm.delete();
        gyro_fm.delete();
        magn_fm.delete();
        rv_fm.delete();
    }

    private void storeAllFiles() {

        sm.saveFile(getResources().getString(R.string.sensor_accelerometer), acc_fm.getFile());
        sm.saveFile(getResources().getString(R.string.sensor_gyroscope), gyro_fm.getFile());
        sm.saveFile(getResources().getString(R.string.sensor_magnetometer), magn_fm.getFile());
        sm.saveFile(getResources().getString(R.string.sensor_rotationVector), rv_fm.getFile());
    }

    private void startSensorListener() {

        sensorManager.registerListener(FinalActivity.this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(FinalActivity.this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(FinalActivity.this, rotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(FinalActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stopSensorListener() {

        sensorManager.unregisterListener(FinalActivity.this);
        sensorManager.flush(FinalActivity.this);
    }

    private void writeHeaders() {

        acc_fm.writeHeader(selectedActivity.getName().replaceAll("\\s+", ""), date, Build.MODEL, location.getLatitude(), location.getLongitude());
    }
}
