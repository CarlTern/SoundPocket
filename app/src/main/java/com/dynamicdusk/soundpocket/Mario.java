package com.dynamicdusk.soundpocket;

import java.util.Calendar;

public class Mario extends AccelerometerListener {


    private float xThreshold = 12;
    private float yThreshold = 8;
    private float zThreshold = 12;
    int hits = 0;
    boolean levelUp = false;
    int points = 0;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public Mario(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    public void onAccX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_FIREBALL);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hits++;
            if(levelUp && hits >10){
                soundPlayer.playSound(SoundPlayer.SOUND_STAGE_WON);
                levelUp = false;
            }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {

        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_COIN);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            points++;
            if(points>10){
                soundPlayer.playSound(SoundPlayer.SOUND_POWER_UP);
                levelUp =true;
                points = 5;

            }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_BOING);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onGyroX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_PIPE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }

    public void onGyroY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_MAMMA_MIA);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }


    public void onGyroZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_RIGHT_O);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }
}
