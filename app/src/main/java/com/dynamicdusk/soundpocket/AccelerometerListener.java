package com.dynamicdusk.soundpocket;


public abstract class AccelerometerListener {
    protected float xThreshold;
    protected float yThreshold;
    protected float zThreshold;

    public abstract void setSoundPlayer(SoundPlayer soundPlayer);

    public float[] getThresholds(){
        return new float[]{xThreshold, yThreshold, zThreshold};
    }

    public abstract void onAccelerationChanged(float x, float y, float z);

    public abstract void onShake(float force);

    public abstract void onShakeX(float force);

    public abstract void onShakeY(float force);

    public abstract void onShakeZ(float force);

    }

