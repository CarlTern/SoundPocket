package com.dynamicdusk.soundpocket;

import java.util.Calendar;

public class Lasso extends AccelerometerListener{
    private long timeStamp = 0;
    private long timeStampSpin = 0;
    private SoundPlayer soundPlayer;
    private int spinLevel;

    public Lasso(){
        super.xAccThreshold = 60;
        super.yAccThreshold = 8;
        super.zAccThreshold = 12;
        super.xGyroThreshold = 2f;
        super.yGyroThreshold = 2f;
        super.zGyroThreshold = 2f;
        timeStamp = Calendar.getInstance().getTimeInMillis();
        spinLevel = 0;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        if (soundPlayer.isSoundOn() && spinLevel == 3 && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000) {
            soundPlayer.killSound();
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_THROW);
            spinLevel=0;
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }

    public void onAccY(float force) {

    }
    public void onAccZ(float force) {
    }
    public void onGyroX(float force) {
    }

    public void onGyroY(float force) {
    }

    public void onGyroZ(float force) {
       if(Calendar.getInstance().getTimeInMillis() - timeStampSpin > 2000){
        spinLevel =0;
        }
        if(soundPlayer.isSoundOn() && spinLevel==0 &&(Calendar.getInstance().getTimeInMillis() - timeStampSpin) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN);
            timeStampSpin = Calendar.getInstance().getTimeInMillis();
            spinLevel=1;
        }else if (soundPlayer.isSoundOn() && spinLevel==1 &&(Calendar.getInstance().getTimeInMillis() - timeStampSpin)>1000){
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_MORE);
            timeStampSpin = Calendar.getInstance().getTimeInMillis();
            spinLevel=2;
        }else if (soundPlayer.isSoundOn() && spinLevel>=2 &&(Calendar.getInstance().getTimeInMillis() - timeStampSpin)>1000){
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_MOST);
            timeStampSpin = Calendar.getInstance().getTimeInMillis();
            spinLevel =3;
        }
    }
}
