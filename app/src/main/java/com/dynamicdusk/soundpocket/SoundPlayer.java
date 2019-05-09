package com.dynamicdusk.soundpocket;

import android.content.Context;
import android.media.MediaPlayer;

import java.nio.channels.Pipe;

/**
 * Created by fredricbillow on 2019-04-08.
 */

public class SoundPlayer {
    //public static final int SOUND_SPRAY_PAINT_SHAKE = R.raw.spraypaintshake;
    public static final int SOUND_LIGHTSABER_ON= R.raw.lightsaberon;
    public static final int SOUND_LIGHTSABER_HIT = R.raw.lightsaberhit;
    public static final int SOUND_AIR_HORN = R.raw.airhorn;
    public static final int SOUND_DRY_FIRE = R.raw.dryfire;
    public static final int SOUND_EMPTY_PUMP = R.raw.emptypump;
    public static final int SOUND_AMMO_LOAD = R.raw.ammoload;
    public static final int SOUND_PEW_PEW = R.raw.pewpew;
    public static final int SOUND_PUNCH = R.raw.punch;
    public static final int SOUND_GUN_SHOT = R.raw.gunshot;
    public static final int SOUND_SHOTGUN_SHOT = R.raw.shotgunshot;
    public static final int SOUND_SHOTGUN_RELOAD = R.raw.shotgunreload;
    public static final int SOUND_OFF_I_GO_THEN = R.raw.offigothen;
    public static final int SOUND_RIGHT_O = R.raw.righto;
    public static final int SOUND_WORK_WORK = R.raw.workwork;
    public static final int SOUND_YES_MI_LORD = R.raw.yesmilord;
    public static final int SOUND_FIREBALL = R.raw.fireball;
    public static final int SOUND_COIN = R.raw.coin;
    public static final int SOUND_PIPE = R.raw.pipe;

    protected MediaPlayer mPlayer;
    protected boolean soundOn = false;
    Context context;

    public SoundPlayer(Context context) {
        this.context = context;
    }

    protected void initPlayer(int chosenSound) {

        mPlayer = MediaPlayer.create(context, chosenSound);


        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn() {
        this.soundOn = true;
    }

    public void setSoundOff() {
        this.soundOn = false;
    }

    public void playSound(int sound) {
        if (soundOn) {
            switch (sound) {
                case SOUND_PEW_PEW:
                    initPlayer(SOUND_PEW_PEW);
                    mPlayer.start();
                    break;
                case SOUND_PUNCH:
                    initPlayer(SOUND_PUNCH);
                    mPlayer.start();
                    break;
                case SOUND_GUN_SHOT:
                    initPlayer(SOUND_GUN_SHOT);
                    mPlayer.start();
                    break;
                case SOUND_SHOTGUN_SHOT:
                    initPlayer(SOUND_SHOTGUN_SHOT);
                    mPlayer.start();
                    break;
                case SOUND_SHOTGUN_RELOAD:
                    initPlayer(SOUND_SHOTGUN_RELOAD);
                    mPlayer.start();
                    break;
                case SOUND_OFF_I_GO_THEN:
                    initPlayer(SOUND_OFF_I_GO_THEN);
                    mPlayer.start();
                    break;
                case SOUND_YES_MI_LORD:
                    initPlayer(SOUND_YES_MI_LORD);
                    mPlayer.start();
                    break;
                case SOUND_WORK_WORK:
                    initPlayer(SOUND_WORK_WORK);
                    mPlayer.start();
                    break;
                case SOUND_PIPE:
                    initPlayer(SOUND_PIPE);
                    mPlayer.start();
                    break;
                case SOUND_FIREBALL:
                    initPlayer(SOUND_FIREBALL);
                    mPlayer.start();
                    break;

                    case SOUND_COIN:
                    initPlayer(SOUND_COIN);
                    mPlayer.start();
                    break;

                case SOUND_DRY_FIRE:
                    initPlayer(SOUND_DRY_FIRE);
                    mPlayer.start();
                    break;
                case SOUND_AMMO_LOAD:
                    initPlayer(SOUND_AMMO_LOAD);
                    mPlayer.start();
                    break;
                case SOUND_EMPTY_PUMP:
                    initPlayer(SOUND_EMPTY_PUMP);
                    mPlayer.start();
                    break;
                case SOUND_AIR_HORN:
                    initPlayer(SOUND_AIR_HORN);
                    mPlayer.start();
                    break;
                case SOUND_LIGHTSABER_HIT:
                    initPlayer(SOUND_LIGHTSABER_HIT);
                    mPlayer.start();
                    break;
                case SOUND_LIGHTSABER_ON:
                    initPlayer(SOUND_LIGHTSABER_ON);
                    mPlayer.start();
                    break;
                case -1:
            }
        }
    }
}
