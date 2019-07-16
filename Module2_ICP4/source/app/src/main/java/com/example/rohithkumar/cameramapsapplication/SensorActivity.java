package com.example.rohithkumar.cameramapsapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;

    TextView accX;
    TextView accY;
    TextView accZ;

//    TextView gyX;
//    TextView gyY;
//    TextView gyZ;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
//        view.setBackgroundColor(Color.GREEN);

        accX = (TextView) findViewById(R.id.acc_x);
        accY = (TextView) findViewById(R.id.acc_y);
        accZ = (TextView) findViewById(R.id.acc_z);

//        gyX = (TextView) findViewById(R.id.gy_x);
//        gyY = (TextView) findViewById(R.id.gy_y);
//        gyZ = (TextView) findViewById(R.id.gy_z);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
//        lastUpdate = System.currentTimeMillis();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accX.setText("X: " + x);
            accY.setText("Y: " + y);
            accZ.setText("Z: " + z);
        }
//        else {
//            gyX.setText("X: " + x);
//            gyY.setText("Y: " + y);
//            gyZ.setText("Z: " + z);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
//        ArrayList<Sensor> sensors = new ArrayList<Sensor>(sensorManager.getSensorList(Sensor.TYPE_ALL));

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

