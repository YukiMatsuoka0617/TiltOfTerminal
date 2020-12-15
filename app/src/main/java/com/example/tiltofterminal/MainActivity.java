package com.example.tiltofterminal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener {

    SensorManager mSensorManager;
    TextView textView,textView2,textView3,textView4;
    float accX, accY, accZ;
    float gyroX, gyroY, gyroZ;
    float rvX, rvY, rvZ;
    Intent intent2, intent3;
    Button bt1,bt2;
    List<Float> gyroXCal = new ArrayList<Float>();
    List<Float> gyroYCal = new ArrayList<Float>();
    List<Float> gyroCal = new ArrayList<Float>();

    float thresholdY,thresholdX = -2;
    float gyroYMax,gyroXMax;

    boolean mode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        textView = findViewById(R.id.text_view);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView4.setText(""+mode);

        bt1  = findViewById(R.id.button1);
        bt1.setOnClickListener(this);
        bt2  = findViewById(R.id.button2);
        bt2.setOnClickListener(this);

        intent2 = new Intent(this,MainActivity2.class);
        intent3 = new Intent(this,MainActivity3.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor accel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor gyro = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor rv = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mSensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, rv, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(mode){
            ///calibration
            switch(sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accX = sensorEvent.values[0];
                    accY = sensorEvent.values[1];
                    accZ = sensorEvent.values[2];

                    String acc = "Accelerometer\n"
                            + " X: " + accX + "\n"
                            + " Y: " + accY + "\n"
                            + " Z: " + accZ;
                    textView.setText(acc);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    gyroX = sensorEvent.values[0] * 5;
                    gyroY = sensorEvent.values[1] * 5;
                    gyroZ = sensorEvent.values[2] * 5;

                    String gyro = "Gyro\n"
                            + " X: " + gyroX + "\n"
                            + " Y: " + gyroY + "\n"
                            + " Z: " + gyroZ;
                    textView2.setText(gyro);
                    Log.d("test", "onSensorChanged: \n" + "X:" + gyroX + "\n" + "Y:" + gyroY + "\n" + "Z:" + gyroZ + "\n");
                    break;
            }
//                case Sensor.TYPE_ROTATION_VECTOR:
//                    rvX = sensorEvent.values[0];
//                    rvY = sensorEvent.values[1];
//                    rvZ = sensorEvent.values[2];
//
//                    String rv = "RotationVector\n"
//                            + " X: " + rvX + "\n"
//                            + " Y: " + rvY + "\n"
//                            + " Z: " + rvZ;
//                    textView3.setText(rv);
//                    Log.d("test", "onSensorChanged: \n" + "X:" + rvX + "\n" + "Y:" + rvY + "\n" + "Z:" + rvZ + "\n");
//                    break;
            if(gyroCal.size() < 5 ){
                if(accX < -2){
                    gyroCal.add(gyroY);
                }
            }
            else if(5 <= gyroCal.size() && gyroCal.size() < 10){
                if(accY > 5)
                    gyroCal.add(gyroX);
            }

            textView3.setText(""+gyroCal);
            if(gyroCal.size() == 10){
                thresholdY = (gyroCal.get(0) + gyroCal.get(1) + gyroCal.get(2) + gyroCal.get(3) + gyroCal.get(4))/5;
                thresholdX = (gyroCal.get(5) + gyroCal.get(6) + gyroCal.get(7) + gyroCal.get(8) + gyroCal.get(9))/5;
            }

        }

        else{
            switch(sensorEvent.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    accX = sensorEvent.values[0];
                    accY = sensorEvent.values[1];
                    accZ = sensorEvent.values[2];

                    String acc = "Accelerometer\n"
                            + " X: " + accX + "\n"
                            + " Y: " + accY + "\n"
                            + " Z: " + accZ;
                    textView.setText(acc);
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    gyroX = sensorEvent.values[0] * 5;
                    gyroY = sensorEvent.values[1] * 5;
                    gyroZ = sensorEvent.values[2] * 5;

                    String gyro = "Gyro\n"
                            + " X: " + gyroX + "\n"
                            + " Y: " + gyroY + "\n"
                            + " Z: " + gyroZ;
                    textView2.setText(gyro);
                    Log.d("test", "onSensorChanged: \n" + "X:" + gyroX + "\n" + "Y:" + gyroY + "\n" + "Z:" + gyroZ + "\n");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    rvX = sensorEvent.values[0];
                    rvY = sensorEvent.values[1];
                    rvZ = sensorEvent.values[2];

                    String rv = "RotationVector\n"
                            + " X: " + rvX + "\n"
                            + " Y: " + rvY + "\n"
                            + " Z: " + rvZ;
                    textView3.setText(rv);
                    Log.d("test", "onSensorChanged: \n" + "X:" + rvX + "\n" + "Y:" + rvY + "\n" + "Z:" + rvZ + "\n");
                    break;
            }
            if(accX < -2 && gyroY < thresholdY){
                startActivity(intent2);
                accX = 0;
                gyroY = 0;
            }
            else if(accY > 5 && gyroX < thresholdX){
                startActivity(intent3);
                accY = 0;
                gyroX = 0;
            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button1:
                mode = false;
                textView4.setText(""+mode);
                break;
            case R.id.button2:
                mode = true;
                textView4.setText(""+mode);
                break;

        }
    }

}