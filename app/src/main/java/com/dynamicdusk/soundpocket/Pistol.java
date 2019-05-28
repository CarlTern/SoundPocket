package com.dynamicdusk.soundpocket;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.Calendar;


public class Pistol extends AccelerometerListener {


    private boolean silenced = false;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;
    private int shots = 0;
    private long rightAcc;
    private long leftAcc;
    private long rightMove;
    private long leftMove;
    private long gyroZ;
    private long now;
    private MainActivity main = new MainActivity();

    public Pistol(){
        super.xAccThreshold = 12;
        super.yAccThreshold = 19;
        super.zAccThreshold = 25;
        super.zGyroThreshold = 5;
        timeStamp = Calendar.getInstance().getTimeInMillis();
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (soundPlayer.isSoundOn()) {
            if (force > 0) {
                rightAcc = now;
            } else {
                leftAcc = now;
            }
            if (now - rightAcc < 100 && now - leftAcc < 100) {
                if (rightAcc - leftAcc < 0) {
                    rightMove(force);
                } else {
                    leftMove(force);
                }
            }
        }
    }

    public void onAccY(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (soundPlayer.isSoundOn() && (now - timeStamp) > 500
        && now -rightAcc > 200 && now - leftAcc > 200) {
                silenced = !silenced;
                soundPlayer.playSound(SoundPlayer.SOUND_SCREW_ON_SILENCER);
<<<<<<< HEAD
                timeStamp = now;
=======
                timeStamp = Calendar.getInstance().getTimeInMillis();
>>>>>>> 00e2faf54628a4fac42874a74dd67e01e82b0e9f
        }
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn() && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_RELOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            shots=8;
        }
    }

    public void onGyroZ(float force){
        now = Calendar.getInstance().getTimeInMillis();
        gyroZ = now;
        if (now - rightMove < 200 && force < -2) {
            playShot();
        }
    }

    private void rightMove(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (now - gyroZ < 200 && now - leftMove > 500) {
            playShot();
        }
        rightMove = now;
    }

    private void leftMove(float force){
        now = Calendar.getInstance().getTimeInMillis();
        leftMove = now;
    }


    private void playShot(){
        now = Calendar.getInstance().getTimeInMillis();
        if(shots > 0 && now - timeStamp > 400) {
            if (!silenced) {
                soundPlayer.playSound(SoundPlayer.SOUND_PISTOL);
            } else {
                soundPlayer.playSound(SoundPlayer.SOUND_PISTOL_SILENCED);
            }
            shots--;
        } else if (now - timeStamp > 400){
            soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
        }
        timeStamp = now;
    }


    }