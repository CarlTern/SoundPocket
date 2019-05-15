package com.dynamicdusk.soundpocket;

import android.content.Context;

import java.util.Calendar;

public class LightSaber extends AccelerometerListener {


    private float xAccThreshold = 12;
    private float yAccThreshold = 8;
    private float zAccThreshold = 12;
    SoundPlayer soundPlayer;
    private long timeStamp =0;
    private boolean isOn = false;

    public LightSaber(){
        super.xAccThreshold = xAccThreshold;
        super.yAccThreshold = yAccThreshold;
        super.zAccThreshold = zAccThreshold;
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        long now = Calendar.getInstance().getTimeInMillis();
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_HIT);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if (!isOn && soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_OPEN);
            soundPlayer.playLoop(SoundPlayer.SOUND_LIGHTSABER_PULSE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            isOn = true;
        } else if (isOn && soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_CLOSE);
            soundPlayer.killLoop();
            timeStamp = Calendar.getInstance().getTimeInMillis();
            isOn = false;
        }
        //jsHandler.alert("Force: " + force);
    }
    public void onAccZ(float force) {
    }

    public void onGyroX(float force){

    }
    public void onGyroY(float force){

    }
    public void onGyroZ(float force){

    }
}
