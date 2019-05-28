package com.dynamicdusk.soundpocket;

import android.content.Context;

import java.util.Calendar;

public class LightSaber extends AccelerometerListener {


    public SoundPlayer soundPlayer;
    private long timeStamp =0;
    private long now =0;
    private long sideMove =0;
    private long hitTime =0;
    private long moveStamp;
    private boolean isOn = false;
    private long uppAcc = 0;
    private long downAcc = 0;
    private long uppMove = 0;
    private long downMove = 0;

    public LightSaber(){

        super.xAccThreshold = 30;
        super.yAccThreshold = 22;
        super.zAccThreshold = 30;
        super.xGyroThreshold = 3f;
        super.yGyroThreshold = 3f;
        super.zGyroThreshold = 3f;

        timeStamp = Calendar.getInstance().getTimeInMillis();
        moveStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {

        now = Calendar.getInstance().getTimeInMillis();
        sideMove = now;
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500 && now - downAcc > 100) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_HIT);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hitTime = Calendar.getInstance().getTimeInMillis();
        }


    }

    public void onAccY(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if(soundPlayer.isSoundOn()){

            if (force < 0) {
                downAcc = now;
            } else {
                uppAcc = now;
            }

            if (now - downAcc < 80 && now - uppAcc < 80) {

                if (downAcc - uppAcc < 0) {
                    downMove(force);
                } else {
                    uppMove(force);

                }
            }
        }
    }

    public void onAccZ(float force) {

        now = Calendar.getInstance().getTimeInMillis();
        sideMove = now;
        if(isOn &&soundPlayer.isSoundOn()&& (now - timeStamp) > 500 && now - downAcc > 100) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_HIT);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hitTime = Calendar.getInstance().getTimeInMillis();
        }

    }

    public void killLoop(){
        soundPlayer.killLoop();
        isOn = false;
    }

    public void onGyroX(float force){

        now = Calendar.getInstance().getTimeInMillis();
        if(isOn &&soundPlayer.isSoundOn()&& (now - moveStamp) > 600) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_SWING_ONE);
            moveStamp = Calendar.getInstance().getTimeInMillis();
        }

    }
    public void onGyroY(float force){

        now = Calendar.getInstance().getTimeInMillis();
        if(isOn &&soundPlayer.isSoundOn()&& (now - moveStamp) > 600) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_SWING_TWO);
            moveStamp = Calendar.getInstance().getTimeInMillis();
        }

    }

    public void onGyroZ(float force){

        now = Calendar.getInstance().getTimeInMillis();
        if(isOn &&soundPlayer.isSoundOn()&& (now - moveStamp) > 600) {
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_SWING_TWO);
            moveStamp = Calendar.getInstance().getTimeInMillis();
        }

    }

    private void uppMove(float force){
        now = Calendar.getInstance().getTimeInMillis();
        if (!isOn && now - downMove > 500){
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_OPEN);
            soundPlayer.playLoop(SoundPlayer.SOUND_LIGHTSABER_PULSE);
            uppMove = now;
            isOn = true;
        }
    }
    private void downMove(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (isOn && now - hitTime > 400 && now -uppMove > 500) {
            isOn = false;

            try {
                soundPlayer.killSound();
            } catch (IllegalStateException exception) {

            }
            soundPlayer.playSound(SoundPlayer.SOUND_LIGHTSABER_CLOSE);
            try {
                Thread.sleep(950);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            killLoop();
            downMove = now;
        }
    }
    public boolean isOn(){
        return isOn;
    }

}
