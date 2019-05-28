package com.dynamicdusk.soundpocket;

import java.util.Calendar;
import java.util.Random;

public class FartPrank extends AccelerometerListener {


    private long timeStamp = 0;
    private SoundPlayer soundPlayer;
    Random rand = new Random();

    public FartPrank(){
        super.xAccThreshold = 3f;
        super.yAccThreshold = 3f;
        super.zAccThreshold = 3f;
        super.xGyroThreshold = 3f;
        super.yGyroThreshold = 3f;
        super.zGyroThreshold = 3f;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    public void onAccX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) >  5000) {
           playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
           playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onGyroX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
            }

    public void onGyroY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
            }

    public void onGyroZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
            }

    private void playRandomSound(){
        switch(rand.nextInt(7)+1){
            case 1:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_ONE);
                break;
            case 2:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_TWO);
                break;
            case 3:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_THREE);
                break;
            case 4:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_FOUR);
                break;
            case 5:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_FIVE);
                break;
            case 6:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_SIX);
                break;
            case 7:
                soundPlayer.playSound(SoundPlayer.SOUND_FART_SEVEN);
                break;
        }
    }
}