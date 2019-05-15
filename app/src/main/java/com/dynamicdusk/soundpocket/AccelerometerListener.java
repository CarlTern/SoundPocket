package com.dynamicdusk.soundpocket;


public abstract class AccelerometerListener {
    protected boolean gyroCont = false;
    protected boolean accCont = false;
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
    public boolean isGyroCont(){
        return gyroCont;
    }
    public boolean isAccCont(){
        return accCont;
    }

    public void onAccX(float force){

    }

    public void onAccY(float force){

    }

    public void onAccZ(float force){
        
    }

    public void onGyroX(float force){
        
    }

    public void onGyroY(float force){
        
    }

    public void onGyroZ(float force){
        
    }

    public void onContGyro(float forceX, float forceY, float forceZ){
        
    }

    public void onContAcc(float forceX, float forceY, float forceZ){
        
    }

    public void killLoop(){

    }

}

