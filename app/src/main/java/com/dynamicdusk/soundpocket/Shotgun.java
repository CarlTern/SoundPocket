package com.dynamicdusk.soundpocket;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Shotgun implements AccelerometerListener {

    private int shots = 0;
    private boolean loaded;
    private boolean magazineCocked = false;
    private boolean magazinePulledForward;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public Shotgun(){
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

    }

    @Override
    public void onShake(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(-1);
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeX(float force) {

            if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                if(magazineCocked &&shots>0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
                timeStamp = Calendar.getInstance().getTimeInMillis();
                magazineCocked = false;
                shots--;
            } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 500){
                    soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
                    timeStamp = Calendar.getInstance().getTimeInMillis();
                }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeY(float force) {
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

    public void onShakeZ(float force) {
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            if(shots<8) {
                shots++;
            }
        }
        //jsHandler.alert("Force: " + force);
    }
}
