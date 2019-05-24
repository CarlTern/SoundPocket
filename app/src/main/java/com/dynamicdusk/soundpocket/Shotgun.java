package com.dynamicdusk.soundpocket;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


public class Shotgun extends AccelerometerListener {


    private int shots = 0;
    private boolean loaded;
    private boolean magazineCocked = false;
    private boolean magazinePulledForward;
    private long timeStamp = 0;
    private long downAcc = 0;
    private long uppAcc = 0;
    private SoundPlayer soundPlayer;
    private long sideMove;
    //State booleand
    private long accX;
    private long gyroZ;
    private long drawBack;
    private long shotTime;
    long now;
    private MainActivity main = new MainActivity();

    public Shotgun(){
        super.xAccThreshold = 16;
        super.yAccThreshold = 6;
        super.zAccThreshold = 20;
        super.xGyroThreshold = 20;
        super.yGyroThreshold = 8;
        super.zGyroThreshold = 4;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        accX = Calendar.getInstance().getTimeInMillis();
        if (soundPlayer.isSoundOn() && now - timeStamp > 400) {
            if (now - gyroZ < 200){
                playShot();
            }
        }
    }

    public void onAccY(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (soundPlayer.isSoundOn()) {
            if (shots > 0) {
                if (force < 0) {
                    downAcc = now;
                } else {
                    uppAcc = now;
                }
                if (now - downAcc < 100 && now - uppAcc < 100) {
                    if (downAcc - uppAcc < 0) {
                        downMove(force);
                    } else {
                        uppMove(force);
                    }
                }
            }
        }
    }

    public void onAccZ(float force) {
    }
    // TODO GYRO FIX
    public void onGyroY(float force){
        now = Calendar.getInstance().getTimeInMillis();
        if(soundPlayer.isSoundOn()
                && (now - timeStamp) > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            if(shots<8) {
                shots++;
            }
        }
    }
    public void onGyroZ(float force){
        now = Calendar.getInstance().getTimeInMillis();
        gyroZ = now;
        if (now - accX < 200) {
            playShot();
        }
    }

    private void playShot(){
        now = Calendar.getInstance().getTimeInMillis();
        if(magazineCocked && shots>0 && now - timeStamp > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
            magazineCocked = false;
            shots--;
        } else if ( now - timeStamp > 400) {
            soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
        }
        timeStamp = now;
    }

    private void downMove(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (now - timeStamp > 300){
            drawBack = now;
        }
    }

    private void uppMove(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        if (now - drawBack < 500 && now - timeStamp > 300) {
            if (shots > 0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);
                magazineCocked = true;
                timeStamp = now;
            } else {
                soundPlayer.playSound((SoundPlayer.SOUND_EMPTY_PUMP));
                timeStamp = now;
            }
        }
    }

    public void vibrate(){
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) main.getSystemService(main.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            ((Vibrator) main.getSystemService(main.VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}
