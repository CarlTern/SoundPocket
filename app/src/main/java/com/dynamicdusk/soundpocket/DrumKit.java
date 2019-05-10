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

    @Override
    public void onShake(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(-1);
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_SNARE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_CYMBAL);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_TOM);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }
}
