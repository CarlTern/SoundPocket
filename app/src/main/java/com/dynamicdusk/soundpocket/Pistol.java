package com.dynamicdusk.soundpocket;

import java.util.Calendar;


public class Pistol extends AccelerometerListener {


    private float xThreshold = 12;
    private float yThreshold = 8;
    private float zThreshold = 12;
    private boolean silenced = false;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public Pistol(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
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

    public void onShakeY(float force) {
        if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                silenced = !silenced;
                soundPlayer.playSound(SoundPlayer.SOUND_SCREW_ON_SILENCER);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            //jsHandler.alert("Force: " + force);
        }
    }

    public void onShakeZ(float force) {
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
           // soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            //timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }
}