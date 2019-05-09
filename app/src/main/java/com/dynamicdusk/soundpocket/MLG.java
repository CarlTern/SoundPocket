package com.dynamicdusk.soundpocket;

import android.content.Context;

public class MLG extends AccelerometerListener {

    private float xThreshold = 12;
    private float yThreshold = 8;
    private float zThreshold = 12;
    SoundPlayer soundPlayer;

    public MLG(){
        super.xThreshold = xThreshold;
        super.yThreshold = yThreshold;
        super.zThreshold = zThreshold;
    }
    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    @Override
    public void onShake(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(-1);
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeX(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(SoundPlayer.SOUND_AIR_HORN);
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeY(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(SoundPlayer.SOUND_AIR_HORN);
        }
        //jsHandler.alert("Force: " + force);
    }

    public void onShakeZ(float force) {
        if(soundPlayer.isSoundOn()) {
            soundPlayer.playSound(SoundPlayer.SOUND_AIR_HORN);
        }
        //jsHandler.alert("Force: " + force);
    }
}
