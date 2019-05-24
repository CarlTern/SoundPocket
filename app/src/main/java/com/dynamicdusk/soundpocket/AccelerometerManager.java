package com.dynamicdusk.soundpocket;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import java.util.List;

public class AccelerometerManager {

    private static Context context = null;

    private float xAccThreshold = 14;
    private float yAccThreshold = 7;
    private float zAccThreshold = 14;
    private float xGyroThreshold = 5;
    private float yGyroThreshold = 10;
    private float zGyroThreshold = 2.5f;
    private static int interval = 200;

    private static AccelerometerListener listener;
    private static Sensor accSensor;
    private static SensorManager acceleratorManager;
    private static SensorManager gyroManager;
    private static SensorManager rotationManager;
    private static Sensor gyroSensor;
    private static Sensor rotationSensor;
    private static Boolean supported;
    private static boolean running = false;

    public void stopListening() {
        running = false;
        try {
            if (acceleratorManager != null && accEventListener != null) {
                acceleratorManager.unregisterListener(accEventListener);
            }
        } catch (Exception e) {
        }
    }

    public static boolean isSupported(Context cntxt) {
        context = cntxt;
        if (supported == null) {
            if (context != null) {

                acceleratorManager = (SensorManager) context.
                        getSystemService(Context.SENSOR_SERVICE);


// Get all sensors in device

                List<Sensor> sensors = acceleratorManager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);

                supported = new Boolean(sensors.size() > 0);
            } else {
                supported = Boolean.FALSE;
            }
        }
        return supported;
    }

    public void changeAccThreshold(float xThreshold, float yThreshold, float zThreshold) {
        this.xAccThreshold = xThreshold;
        this.yAccThreshold = yThreshold;
        this.zAccThreshold = zThreshold;
    }
    public void changeGyroThreshold(float xThreshold, float yThreshold, float zThreshold) {
        this.xGyroThreshold = xThreshold;
        this.yGyroThreshold = yThreshold;
        this.zGyroThreshold = zThreshold;
    }

    /**
     * Registers a listener and start listening
     *
     * @param accelerometerListener callback for accelerometer events
     */


    public void startListening(AccelerometerListener accelerometerListener) {

        gyroManager = (SensorManager) context.
                getSystemService(Context.SENSOR_SERVICE);
        acceleratorManager = (SensorManager) context.
                getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> gyroSensors = acceleratorManager.getSensorList(Sensor.TYPE_GYROSCOPE);

        if (gyroSensors.size() > 0) {
            this.gyroSensor = gyroSensors.get(0);

            running = gyroManager.registerListener(
                    gyroEventListener, gyroSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }

        List<Sensor> accSensors = acceleratorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        if (accSensors.size() > 0) {
            this.accSensor = accSensors.get(0);

            running = acceleratorManager.registerListener(
                    accEventListener, accSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }

        List<Sensor> rotationSensors = acceleratorManager.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR);

        if (accSensors.size() > 0) {
            this.rotationSensor = rotationSensors.get(0);

            running = acceleratorManager.registerListener(
                    rotationEventListener, rotationSensor,
                    SensorManager.SENSOR_DELAY_GAME);
        }
        listener = accelerometerListener;
    }

    private SensorEventListener accEventListener = new SensorEventListener() {

        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float force = 0;
        private float forceX = 0;
        private float forceY = 0;
        private float forceZ = 0;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event) {

            now = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];


            if (lastUpdate == 0) {
                lastUpdate = now;
            } else {
                timeDiff = now - lastUpdate;
                if (timeDiff > 0) {
                    forceX = Math.abs(x);
                    forceY = Math.abs(y);
                    forceZ = Math.abs(z);

                    if (Float.compare(forceX, xAccThreshold) > 0) {
                            listener.onAccX(x);
                    }
                    if (Float.compare(forceY, yAccThreshold) > 0) {
                            listener.onAccY(y);
                    }
                    if (Float.compare(forceZ, zAccThreshold) > 0) {
                            listener.onAccZ(z);
                    }
                    if (listener.isAccCont()){
                        listener.onContAcc(x, y, z);
                    }
                    lastUpdate = now;
                }
            }
        }
    };


    private SensorEventListener gyroEventListener = new SensorEventListener() {

        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float forceX = 0;
        private float forceY = 0;
        private float forceZ = 0;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event) {


            now = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            timeDiff = now - lastUpdate;

            if (timeDiff > 0) {
                forceX = Math.abs(x);
                forceY = Math.abs(y);
                forceZ = Math.abs(z);

                if (Float.compare(forceX, xGyroThreshold) > 0) {
                    listener.onGyroX(x);
                }
                if (Float.compare(forceY, yGyroThreshold) > 0) {
                    listener.onGyroY(y);
                }
                if (Float.compare(forceZ, zGyroThreshold) > 0) {
                    listener.onGyroZ(z);
                }
                if (listener.isGyroCont()){
                    listener.onContGyro(x, y, z);
                }
                lastUpdate = now;
            }
        }
    };

    private SensorEventListener rotationEventListener = new SensorEventListener() {

        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float force = 0;
        private float forceX = 0;
        private float forceY = 0;
        private float forceZ = 0;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event) {

            now = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            if (lastUpdate == 0) {
                lastUpdate = now;
            } else {
                timeDiff = now - lastUpdate;
                if (timeDiff > 0) {
                    forceX = Math.abs(x );
                    forceY = Math.abs(y);
                    forceZ = Math.abs(z);

                    if (Float.compare(forceX, xAccThreshold) > 0) {

                    }
                    if (Float.compare(forceY, yAccThreshold) > 0) {

                    }
                    if (Float.compare(forceZ, zAccThreshold) > 0) {

                    }
                    if (listener.isAccCont()){

                    }
                    lastUpdate = now;
                }
            }
        }
    };
}


