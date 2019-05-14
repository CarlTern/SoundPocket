package com.dynamicdusk.soundpocket;


public abstract class AccelerometerListener {
    protected float xThreshold;
    protected float yThreshold;
    protected float zThreshold;

    public abstract void setSoundPlayer(SoundPlayer soundPlayer);

    public float[] getThresholds(){
        return new float[]{xThreshold, yThreshold, zThreshold};
    }

    public abstract void onAccX(float force);

    public abstract void onAccY(float force);

    public abstract void onAccZ(float force);

    public abstract void onGyroX(float force);

    public abstract void onGyroY(float force);

    public abstract void onGyroZ(float force);



    }

