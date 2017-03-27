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

import java.io.Serializable;

public class FinalActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager accelerometerManager;
    private Sensor accelerometer;

    private TextView x_acc;
    private TextView y_acc;
    private TextView z_acc;

    private Chronometer chronometer;
    private Activity selectedActivity;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        accelerometerManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = accelerometerManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        TextView tv_selected_activity_name = (TextView) findViewById(R.id.tv_selected_activity);
        Button button_start = (Button) findViewById(R.id.button_start);
        Button button_reset = (Button) findViewById(R.id.button_reset);

        chronometer = (Chronometer) findViewById(R.id.chrono_activity_time);
        x_acc = (TextView) findViewById(R.id.tv_acc_x);
        y_acc = (TextView) findViewById(R.id.tv_acc_y);
        z_acc = (TextView) findViewById(R.id.tv_acc_z);

        selectedActivity = (Activity) getIntent().getSerializableExtra("selectedActivity");
        tv_selected_activity_name.setText(selectedActivity.getName());

        chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);

        /* To make a beep */
//        final ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
//        beep.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, 1000);

        countDownTimer = new CountDownTimer(selectedActivity.getTime()*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                chronometer.setBase(SystemClock.elapsedRealtime() - millisUntilFinished);
            }

            @Override
            public void onFinish() {
                chronometer.setBase(SystemClock.elapsedRealtime());
//                beep.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP);
            }
        };

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accelerometerManager.registerListener(FinalActivity.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);  // Qual usar: SENSOR_DELAY_NORMAL OU SENSOR_DELAY_FASTEST?
                chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
                countDownTimer.start();
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                chronometer.setBase(SystemClock.elapsedRealtime() - selectedActivity.getTime()*1000);
            }
        });
    }

    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        x_acc.setText(String.format("%f", event.values[0]));
        y_acc.setText(String.format("%f", event.values[1]));
        z_acc.setText(String.format("%f", event.values[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
