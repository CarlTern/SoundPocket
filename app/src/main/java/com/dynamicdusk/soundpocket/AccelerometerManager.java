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

    private float xAccThreshold = 12;
    private float yAccThreshold = 5;
    private float zAccThreshold = 12;
    private float xGyroThreshold = 5;
    private float yGyroThreshold = 5;
    private float zGyroThreshold = 5;
    private static int interval = 200;

    private static AccelerometerListener listener;
    private static Sensor accSensor;
    private static SensorManager acceleratorManager;
    private static SensorManager gyroManager;
    private static Sensor gyroSensor;
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

        List<Sensor> gyroSensors = acceleratorManager.getSensorList(
                Sensor.TYPE_GYROSCOPE);

        if (gyroSensors.size() > 0) {
            this.gyroSensor = gyroSensors.get(0);

            running = gyroManager.registerListener(
                    gyroEventListener, gyroSensor,
                    SensorManager.SENSOR_DELAY_GAME);

        List<Sensor> accSensors = acceleratorManager.getSensorList(
                Sensor.TYPE_ACCELEROMETER);

        if (accSensors.size() > 0) {
            this.accSensor = accSensors.get(0);

            running = acceleratorManager.registerListener(
                    accEventListener, accSensor,
                    SensorManager.SENSOR_DELAY_GAME);

                listener = accelerometerListener;
            }
        }
    }

    private SensorEventListener accEventListener = new SensorEventListener() {

        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float lastX = 0;
        private float lastY = 0;
        private float lastZ = 0;
        private float force = 0;
        private float forceX = 0;
        private float forceY = 0;
        private float forceZ = 0;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event) {
// use the event timestamp as reference
// so the manager precision won't depends
// on the AccelerometerListener implementation
// processing time
            now = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

// if not interesting in shake events
// just remove the whole if then else block
            if (lastUpdate == 0) {
                lastUpdate = now;
                lastX = x;
                lastY = y;
                lastZ = z;
            } else {
                timeDiff = now - lastUpdate;
                if (timeDiff > 0) {
                    forceX = Math.abs(x - lastX );
                    forceY = (y - lastY );
                    forceZ = Math.abs(z - lastZ );

                    if (Float.compare(forceX, xAccThreshold) > 0) {
                            listener.onShakeX(force);
                    }
                    if (Float.compare(forceY, yAccThreshold) > 0) {
                            listener.onShakeY(force);
                    }
                    if (Float.compare(forceZ, zAccThreshold) > 0) {
                            listener.onShakeZ(force);
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
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
        private float lastX = 0;
        private float lastY = 0;
        private float lastZ = 0;
        private float force = 0;
        private float forceX = 0;
        private float forceY = 0;
        private float forceZ = 0;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event) {
// use the event timestamp as reference
// so the manager precision won't depends
// on the AccelerometerListener implementation
// processing time
            now = event.timestamp;
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

// if not interesting in shake events
// just remove the whole if then else block
            if (lastUpdate == 0) {
                lastUpdate = now;
                lastX = x;
                lastY = y;
                lastZ = z;
            } else {
                timeDiff = now - lastUpdate;
                if (timeDiff > 0) {
                    forceX = Math.abs(x - lastX );
                    forceY = (y - lastY );
                    forceZ = Math.abs(z - lastZ );

                    if (Float.compare(forceX, xGyroThreshold) > 0) {
                        listener.onShakeX(force);
                    }
                    if (Float.compare(forceY, yGyroThreshold) > 0) {
                        listener.onShakeY(force);
                    }
                    if (Float.compare(forceZ, zGyroThreshold) > 0) {
                        listener.onShakeZ(force);
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                    lastUpdate = now;
                }
            }
        }
    };
}


