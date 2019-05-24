package com.dynamicdusk.soundpocket;

import java.util.Calendar;

public class Lasso extends AccelerometerListener{
    private long timeStamp = 0;
    private long timeStampSpin = 0;
    private SoundPlayer soundPlayer;
    private int spinLevel;
    private boolean firstSpin = true;

    public Lasso(){
        super.xAccThreshold = 60;
        super.yAccThreshold = 8;
        super.zAccThreshold = 12;
        super.xGyroThreshold = 2f;
        super.yGyroThreshold = 2f;
        super.zGyroThreshold = 2f;
        /*super.xAccThreshold = 20;
        super.yAccThreshold = 8;
        super.zAccThreshold = 12;
        super.xGyroThreshold = 5;
        super.yGyroThreshold = 10;
        super.zGyroThreshold = 5;
        */
        timeStamp = Calendar.getInstance().getTimeInMillis();
        spinLevel = 0;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        if (soundPlayer.isSoundOn() && spinLevel == 3 && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_THROW);
            spinLevel=0;
            timeStamp = Calendar.getInstance().getTimeInMillis();
            firstSpin=true;
            //jsHandler.alert("Force: " + force);
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

       /* if(Calendar.getInstance().getTimeInMillis() - timeStamp>1500 && firstSpin==false){
            if(spinLevel==1){
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_REVERSE);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            } else if(spinLevel==2){
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_MORE_REVERSE);
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_REVERSE);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            } else if(spinLevel==3){
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_MOST_REVERSE);
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_MORE_REVERSE);
                soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN_REVERSE);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
            spinLevel=0;
            firstSpin=true;
        }
        */
        if(soundPlayer.isSoundOn() && spinLevel==0 &&(Calendar.getInstance().getTimeInMillis() - timeStampSpin) > 1000) {
            soundPlayer.playSound(SoundPlayer.SOUND_LASSO_SPIN);
            timeStampSpin = Calendar.getInstance().getTimeInMillis();
            spinLevel=1;
            firstSpin=false;
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
    //jsHandler.alert("Force: " + force);
}
