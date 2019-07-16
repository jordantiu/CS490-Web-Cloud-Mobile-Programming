package com.example.rohithkumar.cameramapsapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_map, button_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] perms= { Manifest.permission.ACCESS_FINE_LOCATION };
        requestPermissions(perms, 1);
    }

    public void onPhotoClick(View v) {
        //This code redirects the from main page to the maps page.
        Intent redirect = new Intent(MainActivity.this, CameraActivity.class);
        startActivity(redirect);
    }

    public void onLocationClick(View v) {
        //This code redirects to the photo activity.
        Intent redirect = new Intent(MainActivity.this, MyMapsActivity.class);
        startActivity(redirect);
    }

    public void onSensorClick(View v) {
        //This code redirects to the sensor activity.
        Intent redirect = new Intent(MainActivity.this, SensorActivity.class);
        startActivity(redirect);
    }

    public void onDestinationClick(View v) {
        //This code redirects to the destination activity.
        Intent redirect = new Intent(MainActivity.this, DestinationActivity.class);
        startActivity(redirect);
    }
}
