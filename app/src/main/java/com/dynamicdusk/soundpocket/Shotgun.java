package com.dynamicdusk.soundpocket;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Shotgun extends AccelerometerListener {

    private int shots = 0;
    private boolean loaded;
    private boolean magazineCocked = false;
    private boolean magazinePulledForward;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    //State booleand
    private boolean xAcc;
    private boolean zGyro;
    private long shotTime;

    public Shotgun(){
        super.xAccThreshold = 14;
        super.yAccThreshold = 8;
        super.zAccThreshold = 14;
        super.xGyroThreshold = 5;
        super.yGyroThreshold = 10;
        super.zGyroThreshold = 2.5f;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {

            if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
                if (zGyro == true && (Calendar.getInstance().getTimeInMillis() - shotTime < 200)){
                    playShot();
                    zGyro = false;
                    xAcc = false;
                } else {
                    xAcc = true;
                    shotTime = Calendar.getInstance().getTimeInMillis();
                }
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onAccY(float force) {
        if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            if (shots > 0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);
                timeStamp = Calendar.getInstance().getTimeInMillis();
                magazineCocked = true;
            } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 500){
                soundPlayer.playSound(SoundPlayer.SOUND_EMPTY_PUMP);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
            //jsHandler.alert("Force: " + force);
        }
    }

    public void onAccZ(float force) {
        //jsHandler.alert("Force: " + force);
    }

    public void onGyroX(float force){

    }
    public void onGyroY(float force){
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            if(shots<8) {
                shots++;
            }
        }
    }
    public void onGyroZ(float force){
        if (xAcc == true && (Calendar.getInstance().getTimeInMillis() - shotTime < 200)){
            playShot();
            zGyro = false;
            xAcc = false;
        } else {
            zGyro = true;
            shotTime = Calendar.getInstance().getTimeInMillis();
        }
    }

    private void playShot(){
        if(magazineCocked &&shots>0) {
            soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            magazineCocked = false;
            shots--;
            timeStamp = Calendar.getInstance().getTimeInMillis();
        } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }
}
