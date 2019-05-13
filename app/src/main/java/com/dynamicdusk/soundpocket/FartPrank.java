package com.dynamicdusk.soundpocket;

import java.util.Calendar;
import java.util.Random;

public class FartPrank extends AccelerometerListener {


    private float xThreshold = 12;
    private float yThreshold = 8;
    private float zThreshold = 12;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;
    Random rand = new Random();

    public FartPrank(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    @Override
    public void onShake(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) >  5000) {
           playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
           playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 5000) {
            playRandomSound();
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        //jsHandler.alert("Force: " + force);
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