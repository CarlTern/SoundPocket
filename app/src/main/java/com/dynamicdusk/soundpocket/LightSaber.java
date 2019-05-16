package com.dynamicdusk.soundpocket;

import android.content.Context;

import java.util.Calendar;

public class LightSaber extends AccelerometerListener {


    private float xAccThreshold = 12;
    private float yAccThreshold = 8;
    private float zAccThreshold = 12;
    public SoundPlayer soundPlayer;
    private long timeStamp =0;
    private long now =0;
    private long sideMove =0;
    private long hitTime =0;
    private boolean isOn = false;

    public LightSaber(){
        super.xAccThreshold = xAccThreshold;
        super.yAccThreshold = yAccThreshold;
        super.zAccThreshold = zAccThreshold;
        super.xGyroThreshold = 1;
        super.yGyroThreshold = 1;
        super.zGyroThreshold = 1;
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_HIT);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hitTime = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        now =Calendar.getInstance().getTimeInMillis();
        if(!isOn && soundPlayer.isSoundOn()
                && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000
                && now - hitTime > 400
                && now - sideMove > 150){
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_OPEN);
            soundPlayer.playLoop(SoundPlayer.SOUND_LIGHTSABER_PULSE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            isOn = true;
        } else if (isOn && soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_CLOSE);
            try {
                Thread.sleep(950);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            killLoop();
            timeStamp = Calendar.getInstance().getTimeInMillis();

        }
        //jsHandler.alert("Force: " + force);
    }
    public void onAccZ(float force) {
        sideMove = Calendar.getInstance().getTimeInMillis();
    }

    public void killLoop(){
        soundPlayer.killLoop();
        isOn = false;
    }

    public void onGyroX(float force){
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_SWING_ONE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hitTime = Calendar.getInstance().getTimeInMillis();
        }
    }
    public void onGyroY(float force){

    }
    public void onGyroZ(float force){
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500) {

            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_SWING_TWO);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hitTime = Calendar.getInstance().getTimeInMillis();
        }
    }
}
