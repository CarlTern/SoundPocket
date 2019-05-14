package com.dynamicdusk.soundpocket;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Shotgun extends AccelerometerListener {

    private float xThreshold = 12;
    private float yThreshold = 4;
    private float zThreshold = 12;
    private int shots = 0;
    private boolean loaded;
    private boolean magazineCocked = false;
    private boolean magazinePulledForward;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public Shotgun(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {

            if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                if(magazineCocked &&shots>0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
                timeStamp = Calendar.getInstance().getTimeInMillis();
                magazineCocked = false;
                shots--;
            } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                    soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
                    timeStamp = Calendar.getInstance().getTimeInMillis();
                }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            if (shots > 0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);
                timeStamp = Calendar.getInstance().getTimeInMillis();
                magazineCocked = true;
            } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 500){
                soundPlayer.playSound(SoundPlayer.SOUND_EMPTY_PUMP);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
            //jsHandler.alert("Force: " + force);
        }
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            if(shots<8) {
                shots++;
            }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onGyroX(float force){

    }
    public void onGyroY(float force){
        
    }
    public void onGyroZ(float force){
        
    }
}
