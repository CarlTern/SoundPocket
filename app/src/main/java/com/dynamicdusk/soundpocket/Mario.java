package com.dynamicdusk.soundpocket;

import java.util.Calendar;

public class Mario extends AccelerometerListener {

    int hits = 0;
    boolean levelUp = false;
    int points = 0;
    private long timeStamp = 0;
    private long timeStampUpDown = 0;
    private SoundPlayer soundPlayer;
    private long now =0;
    private long uppAcc = 0;
    private long downAcc = 0;

    public Mario(){
        super.xAccThreshold = 39;
        super.yAccThreshold = 11;
        super.zAccThreshold = 12;
        super.xGyroThreshold = 5;
        super.yGyroThreshold = 6;
        super.zGyroThreshold = 2.8f;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }


    public void onAccX(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_YAHOO);
            timeStamp = Calendar.getInstance().getTimeInMillis();

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

                if (downAcc - uppAcc < 1 && (now - timeStampUpDown) > 400) {
                    downMove(force);
                } else if((now - timeStampUpDown) > 400){
                    uppMove(force);

                }
            }
        }
    /*
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_COIN);
            points++;
            if(points>10){
                soundPlayer.playSound(SoundPlayer.SOUND_POWER_UP);
                levelUp =true;
                points = 5;
            }
            timeStamp = Calendar.getInstance().getTimeInMillis();

        }
        */
    }

    public void onAccZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_BOING);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }

    public void onGyroX(float force) {
        /*if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_PIPE);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
        */
    }

    public void onGyroY(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_MAMMA_MIA);
            timeStamp = Calendar.getInstance().getTimeInMillis();
        }
    }
    public void onGyroZ(float force) {
        if(soundPlayer.isSoundOn()&& (Calendar.getInstance().getTimeInMillis() - timeStamp) > 500) {
            soundPlayer.playSound(SoundPlayer.SOUND_FIREBALL);
            timeStamp = Calendar.getInstance().getTimeInMillis();
            hits++;
            if(levelUp && hits >10){
                soundPlayer.playSound(SoundPlayer.SOUND_STAGE_WON);
                levelUp = false;
            }
        }
    }

    private void uppMove(float force){
            now = Calendar.getInstance().getTimeInMillis();
            soundPlayer.playSound(SoundPlayer.SOUND_COIN);
            points++;
            if(points>30){
                soundPlayer.playSound(SoundPlayer.SOUND_POWER_UP);
                levelUp =true;
                points = 0;
            }
            timeStampUpDown = now;

    }
    private void downMove(float force) {
        now = Calendar.getInstance().getTimeInMillis();
            soundPlayer.playSound(SoundPlayer.SOUND_PIPE);
            timeStampUpDown = now;
    }
}
