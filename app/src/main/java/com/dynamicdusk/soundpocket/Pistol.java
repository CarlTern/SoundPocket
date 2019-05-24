package com.dynamicdusk.soundpocket;

import java.util.Calendar;


public class Pistol extends AccelerometerListener {


    private float xAccThreshold = 40;
    private float yAccThreshold = 19;
    private float zAccThreshold = 25;
    private boolean silenced = false;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;
    private int shots = 0;

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

        if (soundPlayer.isSoundOn()&&(Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
               if(shots>0) {
                   if (!silenced) {
                       soundPlayer.playSound(SoundPlayer.SOUND_PISTOL);
                       timeStamp = Calendar.getInstance().getTimeInMillis();
                   } else {
                       soundPlayer.playSound(SoundPlayer.SOUND_PISTOL_SILENCED);
                       timeStamp = Calendar.getInstance().getTimeInMillis();
                   }
                   shots--;
               } else{
                   soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
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
            //soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            soundPlayer.playSound(SoundPlayer.SOUND_RELOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            shots=8;
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