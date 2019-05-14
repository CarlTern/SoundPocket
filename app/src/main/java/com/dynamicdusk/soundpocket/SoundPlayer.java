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



    protected MediaPlayer mPlayer;
    protected boolean soundOn = true;
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

    public void setLoopingOn() {
        mPlayer.setLooping(true);
    }

    public void setLoopingOff() {
        mPlayer.setLooping(false);
    }

    public void setVolume(int newVolume) {
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - newVolume) / Math.log(MAX_VOLUME)));
        mPlayer.setVolume(volume, volume);
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
                case SOUND_PISTOL_SILENCED:
                    initPlayer(SOUND_PISTOL_SILENCED);
                    mPlayer.start();
                    break;
                case SOUND_PISTOL:
                    initPlayer(SOUND_PISTOL);
                    mPlayer.start();
                    break;
                case SOUND_SCREW_ON_SILENCER:
                    initPlayer(SOUND_SCREW_ON_SILENCER);
                    mPlayer.start();
                    break;
                case SOUND_POWER_UP:
                    initPlayer(SOUND_POWER_UP);
                    mPlayer.start();
                    break;
                case SOUND_STAGE_WON:
                    initPlayer(SOUND_STAGE_WON);
                    mPlayer.start();
                    break;
                case SOUND_FART_ONE:
                    initPlayer(SOUND_FART_ONE);
                    mPlayer.start();
                    break;
                case SOUND_FART_TWO:
                    initPlayer(SOUND_FART_TWO);
                    mPlayer.start();
                    break;
                case SOUND_FART_THREE:
                    initPlayer(SOUND_FART_THREE);
                    mPlayer.start();
                    break;
                case SOUND_FART_FOUR:
                    initPlayer(SOUND_FART_FOUR);
                    mPlayer.start();
                    break;
                case SOUND_FART_FIVE:
                    initPlayer(SOUND_FART_FIVE);
                    mPlayer.start();
                    break;
                case SOUND_FART_SIX:
                    initPlayer(SOUND_FART_SIX);
                    mPlayer.start();
                    break;
                case SOUND_FART_SEVEN:
                    initPlayer(SOUND_FART_SEVEN);
                    mPlayer.start();
                    break;
                case SOUND_SNARE:
                    initPlayer(SOUND_SNARE);
                    mPlayer.start();
                    break;
                case SOUND_TOM:
                    initPlayer(SOUND_TOM);
                    mPlayer.start();
                    break;
                case SOUND_CYMBAL:
                    initPlayer(SOUND_CYMBAL);
                    mPlayer.start();
                    break;
                case SOUND_LIGHTSABER_OPEN:
                    initPlayer(SOUND_LIGHTSABER_OPEN);
                    mPlayer.start();
                    break;
                case SOUND_LIGHTSABER_PULSE:
                    initPlayer(SOUND_LIGHTSABER_PULSE);
                    mPlayer.start();
                    break;
                case SOUND_LIGHTSABER_CLOSE:
                    initPlayer(SOUND_LIGHTSABER_CLOSE);
                    mPlayer.start();
                    break;
                case SOUND_MENU_AIRHORN:
                    initPlayer(SOUND_MENU_AIRHORN);
                    mPlayer.start();
                    break;
                case SOUND_MENU_DRUMKIT:
                    initPlayer(SOUND_MENU_DRUMKIT);
                    mPlayer.start();
                    break;
                case SOUND_MENU_FARTPRANK:
                    initPlayer(SOUND_MENU_FARTPRANK);
                    mPlayer.start();
                    break;
                case SOUND_MENU_MARIO:
                    initPlayer(SOUND_MENU_MARIO);
                    mPlayer.start();
                    break;
                case SOUND_MENU_PISTOL:
                    initPlayer(SOUND_MENU_PISTOL);
                    mPlayer.start();
                    break;
                case SOUND_MENU_SHOTGUN:
                    initPlayer(SOUND_MENU_SHOTGUN);
                    mPlayer.start();
                    break;
                case SOUND_MENU_STARWARS:
                    initPlayer(SOUND_MENU_STARWARS);
                    mPlayer.start();
                    break;
                case SOUND_MENU_WARCRAFT:
                    initPlayer(SOUND_MENU_WARCRAFT);
                    mPlayer.start();
                    break;
                case SOUND_ITS_A_ME:
                    initPlayer(SOUND_ITS_A_ME);
                    mPlayer.start();
                    break;
                case SOUND_MAMMA_MIA:
                    initPlayer(SOUND_MAMMA_MIA);
                    mPlayer.start();
                    break;
                case SOUND_BOING:
                    initPlayer(SOUND_BOING);
                    mPlayer.start();
                    break;

                case -1:
            }
        }
    }
}
