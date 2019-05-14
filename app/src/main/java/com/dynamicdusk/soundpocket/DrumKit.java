package com.dynamicdusk.soundpocket;

import android.content.Context;

import java.util.Calendar;

public class DrumKit extends AccelerometerListener {


    private float xThreshold = 12;
    private float yThreshold = 4;
    private float zThreshold = 12;
    SoundPlayer soundPlayer;
    private long timeStamp =0;

    public DrumKit(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    public void onAccX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_SNARE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_CYMBAL);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_TOM);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }
    public void onGyroX(float force) {
    }

    public void onGyroY(float force) {
    }

    public void onGyroZ(float force) {
    }
}
