package com.dynamicdusk.soundpocket;

import android.content.Context;
import android.hardware.Sensor;
import android.media.MediaPlayer;

import java.nio.channels.Pipe;

/**
 * Created by fredricbillow on 2019-04-08.
 */

public class SoundPlayer {
    private final static int MAX_VOLUME = 100;
    //public static final int SOUND_SPRAY_PAINT_SHAKE = R.raw.spraypaintshake;
    public static final int SOUND_PISTOL_SILENCED= R.raw.silenced;
    public static final int SOUND_PISTOL= R.raw.pistol;
    public static final int SOUND_SCREW_ON_SILENCER= R.raw.screw;
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
    public static final int SOUND_POWER_UP = R.raw.powerup;
    public static final int SOUND_STAGE_WON = R.raw.stagewon;
    public static final int SOUND_FART_ONE = R.raw.fart1;
    public static final int SOUND_FART_TWO = R.raw.fart2;
    public static final int SOUND_FART_THREE = R.raw.fart3;
    public static final int SOUND_FART_FOUR = R.raw.fart4;
    public static final int SOUND_FART_FIVE = R.raw.fart5;
    public static final int SOUND_FART_SIX = R.raw.fart6;
    public static final int SOUND_FART_SEVEN = R.raw.fart7;
    public static final int SOUND_SNARE = R.raw.snare;
    public static final int SOUND_CYMBAL = R.raw.cymbal;
    public static final int SOUND_TOM = R.raw.tom;
    public static final int SOUND_LIGHTSABER_OPEN = R.raw.open;
    public static final int SOUND_LIGHTSABER_PULSE = R.raw.pulse;
    public static final int SOUND_LIGHTSABER_CLOSE = R.raw.close;
    public static final int SOUND_MENU_WARCRAFT = R.raw.menuwarcraft;
    public static final int SOUND_MENU_AIRHORN = R.raw.menuairhorn;
    public static final int SOUND_MENU_FARTPRANK= R.raw.menufartprank;
    public static final int SOUND_MENU_MARIO = R.raw.menumario;
    public static final int SOUND_MENU_PISTOL = R.raw.menupistol;
    public static final int SOUND_MENU_SHOTGUN= R.raw.menushotgun;
    public static final int SOUND_MENU_STARWARS = R.raw.menustarwars;
    public static final int SOUND_MENU_DRUMKIT = R.raw.menudrumkit;
    public static final int SOUND_ITS_A_ME= R.raw.itsame;
    public static final int SOUND_MAMMA_MIA= R.raw.mammamia;
    public static final int SOUND_BOING= R.raw.boing;
    public static final int SOUND_YAHOO= R.raw.yahoo;
    public static final int SOUND_LIGHTSABER_SWING_ONE= R.raw.lightsaberswing;
    public static final int SOUND_LIGHTSABER_SWING_TWO= R.raw.lightsaberswing2;




    protected MediaPlayer mPlayer;
    protected boolean soundOn = true;
    Context context;
    private MediaPlayer looper;

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

    public void setVolume(int newVolume) {
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - newVolume) / Math.log(MAX_VOLUME)));
        mPlayer.setVolume(volume, volume);
    }

    public void playLoop(int sound){
        initPlayer(sound);
        looper = mPlayer;
        looper.setLooping(true);
        looper.start();
    }

    public void killLoop(){
        looper.setLooping(false);
        looper.stop();
        looper.release();
    }

    public void playSound(int sound) {
        if (soundOn) {
            initPlayer(sound);
        }
        mPlayer.start();
    }
}