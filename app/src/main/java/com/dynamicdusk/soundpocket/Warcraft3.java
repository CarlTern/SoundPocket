package com.dynamicdusk.soundpocket;

import android.content.Context;

import java.util.Calendar;

public class Warcraft3 extends AccelerometerListener {

    private float xThreshold = 12;
    private float yThreshold = 8;
    private float zThreshold = 12;
    SoundPlayer soundPlayer;
    private long timeStamp = 0;

    public Warcraft3(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
    }
    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_WORK_WORK);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_YES_MI_LORD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_OFF_I_GO_THEN);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }
    public void onGyroX(float force) {
    }

    public void onGyroY(float force) {
    }

    public void onGyroZ(float force) {
    }
}
