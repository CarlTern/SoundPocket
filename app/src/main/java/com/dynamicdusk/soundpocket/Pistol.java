package com.dynamicdusk.soundpocket;

import java.util.Calendar;


public class Pistol extends AccelerometerListener {


    private float xAccThreshold = 15;
    private float yAccThreshold = 9.5f;
    private float zAccThreshold = 12;
    private boolean silenced = false;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public Pistol(){
        super.xAccThreshold = xAccThreshold;
        super.yAccThreshold = yAccThreshold;
        super.zAccThreshold = zAccThreshold;
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {

        if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            if(!silenced) {
                soundPlayer.playSound(SoundPlayer.SOUND_PISTOL);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            } else {
                soundPlayer.playSound(SoundPlayer.SOUND_PISTOL_SILENCED);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                silenced = !silenced;
                soundPlayer.playSound(SoundPlayer.SOUND_SCREW_ON_SILENCER);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            //jsHandler.alert("Force: " + force);
        }
    }
    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
           // soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            //timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }
        public void onGyroX(float force) {
        }
    
        public void onGyroY(float force) {
        }
    
        public void onGyroZ(float force) {
        }
        //jsHandler.alert("Force: " + force);
    }