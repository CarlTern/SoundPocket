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
    private boolean xAcc;
    private boolean zGyro;
    private long shotTime;
    long now;
    private MainActivity main = new MainActivity();

    public Shotgun(){
        super.xAccThreshold = 14;
        super.yAccThreshold = 14;
        super.zAccThreshold = 6;
        super.xGyroThreshold = 5;
        super.yGyroThreshold = 2.6f;
        super.zGyroThreshold = 4;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    public void onAccX(float force) {

            if (soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400) {
                if (zGyro == true && (Calendar.getInstance().getTimeInMillis() - shotTime < 200)){
                    playShot();
                    zGyro = false;
                    xAcc = false;
                } else {
                    xAcc = true;
                    shotTime = Calendar.getInstance().getTimeInMillis();
                }
        }
    }

    public void onAccY(float force) {
        long now = Calendar.getInstance().getTimeInMillis();
        if (soundPlayer.isSoundOn()
            && (now - timeStamp) > 400
            && now - shotTime > 400
            && now - sideMove > 300) {
            if (shots > 0) {
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);       
                magazineCocked = true;
            } else if ((now - timeStamp) > 500){
                soundPlayer.playSound(SoundPlayer.SOUND_EMPTY_PUMP);
            }
            timeStamp = now;
        }
    }

    public void onAccZ(float force) {
        now = Calendar.getInstance().getTimeInMillis();
        sideMove = now;
    }

    public void onGyroY(float force){
        now = Calendar.getInstance().getTimeInMillis();
        if(soundPlayer.isSoundOn()
                && (Calendar.getInstance().getTimeInMillis() - timeStamp) > 400
                && now - sideMove < 150) {

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
        now = Calendar.getInstance().getTimeInMillis();
        if(magazineCocked &&shots>0
                && now - sideMove > 50) {
            soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
            //vibrate();
            timeStamp = Calendar.getInstance().getTimeInMillis();
            magazineCocked = false;
            shots--;
            timeStamp = Calendar.getInstance().getTimeInMillis();
        } else if ((Calendar.getInstance().getTimeInMillis() - timeStamp) > 400
                && now - sideMove > 50) {
            soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }

    private void downMove(float force) {

    }

    private void uppMove(float force) {

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
