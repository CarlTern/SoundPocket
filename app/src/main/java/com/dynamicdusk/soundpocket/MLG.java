package com.dynamicdusk.soundpocket;

import android.content.Context;

public class MLG implements AccelerometerListener {

    SoundPlayer soundPlayer;


    public void setSoundPlayer(SoundPlayer soundPlayer){
        this.soundPlayer = soundPlayer;
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {

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
