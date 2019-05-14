package com.dynamicdusk.soundpocket;


public abstract class AccelerometerListener {
    protected float xAccThreshold = 12;
    protected float yAccThreshold = 7;
    protected float zAccThreshold = 12;
    protected float xGyroThreshold = 5;
    protected float yGyroThreshold = 10;
    protected float zGyroThreshold = 2.5f;
    
    public abstract void setSoundPlayer(SoundPlayer soundPlayer);

    public float[] getAccThresholds(){
        return new float[]{xAccThreshold, yAccThreshold, zAccThreshold};
    }
    public float[] getGyroThresholds(){
        return new float[]{xGyroThreshold, yGyroThreshold, zGyroThreshold};
    }

    public abstract void onAccX(float force);

    public abstract void onAccY(float force);

    public abstract void onAccZ(float force);

    public abstract void onGyroX(float force);

    public abstract void onGyroY(float force);

    public abstract void onGyroZ(float force);



    }

