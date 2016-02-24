package com.example.heath.hw_10;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private ImageView mCompassImgImageView;
    private ImageView mArrowImgImageView;
    private LocationManager locationManager;
    private String provider;
    private EditText mLongtitudeEditText;
    private EditText mLatitudeEditText;
    private EditText mAddressEditText;
    private Button mLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompassImgImageView = (ImageView) findViewById(R.id.compass_img);
        mArrowImgImageView = (ImageView) findViewById(R.id.arrow_img);
        mLongtitudeEditText = (EditText) findViewById(R.id.longtitude);
        mLatitudeEditText = (EditText) findViewById(R.id.latitude);
        mAddressEditText = (EditText) findViewById(R.id.address);
        mLocationButton = (Button) findViewById(R.id.Location);


        mCompassImgImageView = (ImageView) findViewById(R.id.compass_img);
        mArrowImgImageView = (ImageView) findViewById(R.id.arrow_img);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor acceleromteterSensor = sensorManager.getDefaultSensor(Sensor
                .TYPE_ACCELEROMETER);
        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor
                .TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(listener, magneticSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, acceleromteterSensor,
                SensorManager.SENSOR_DELAY_NORMAL);


        mLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                List<String> providers = locationManager.getProviders(true);
                if (providers.contains(LocationManager.GPS_PROVIDER)) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    // 当没有可用的位置提供器时，弹出Toast提示用户
                    Toast.makeText(MainActivity.this, "No location provider " +
                            "to use",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Location location = locationManager.getLastKnownLocation
                        (provider);
                if (location != null) {
                    showLocation(location);
                }
                locationManager.requestLocationUpdates(provider, 5000, 1,
                        locationListener);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
        if (locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {

        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];

        private float lastRotateDegree = 0f;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                accelerometerValues = event.values.clone();
            } else if (event.sensor.getType() == Sensor
                    .TYPE_MAGNETIC_FIELD) {
                magneticValues = event.values.clone();
            }
            float[] R = new float[9];
            float[] values = new float[9];
            SensorManager.getRotationMatrix(R, null, accelerometerValues,
                    magneticValues);
            SensorManager.getOrientation(R, values);
            float rotateDegree = -(float) Math.toDegrees(values[0]);
            if (Math.abs(rotateDegree - lastRotateDegree) > 3) {
                RotateAnimation animation = new RotateAnimation
                        (lastRotateDegree, rotateDegree, Animation
                                .RELATIVE_TO_SELF, 0.5f, Animation
                                .RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                animation.setDuration(500);
                mCompassImgImageView.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void showLocation(Location location) {
        mLongtitudeEditText.setText("" + location.getLongitude());
        mLatitudeEditText.setText("" + location.getLatitude());
    }
}
