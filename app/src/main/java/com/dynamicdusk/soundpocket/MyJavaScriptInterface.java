package com.dynamicdusk.soundpocket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.speech.RecognizerIntent;

import java.io.Console;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by fredricbillow on 2019-04-07.
 */

public class MyJavaScriptInterface {
    WebView webView;
    Context context;
    SoundPlayer soundPlayer;
    MainActivity mainActivity;
    String specificSoundState = "";
    public static Boolean betaPacksActiveState = false;


    public MyJavaScriptInterface(WebView w, Context context, SoundPlayer soundPlayer, MainActivity mainActivity) {
        this.webView = w;
        this.context = context;
        this.soundPlayer = soundPlayer;
        this.mainActivity = mainActivity;
    }

    //------Beta back functions
    @JavascriptInterface
    public void activateBetaPacks() {
        betaPacksActiveState = true;
    }

    @JavascriptInterface
    public void inactivateBetaPacks() {
        betaPacksActiveState = false;
    }
    @JavascriptInterface
    public void getBetaPackState() {
        runJavaScript("callbackgetBetaPackState(" + betaPacksActiveState + ")");
    }


    @JavascriptInterface
    public void setSoundOn(boolean bool) {
        //alert("set soundOn to " + bool);

        if(bool) {
            soundPlayer.setSoundOn();
        } else {
            soundPlayer.setSoundOff();
            mainActivity.starWarsCheck();
        }
    }




    @JavascriptInterface
    public void setSound(String key) {
        System.out.println("----------------------setSound: " + key);
        mainActivity.setPackage(key);
    }

    @JavascriptInterface
    public void setPackage(String key) {
        if(key.equals("Mario") && soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_ITS_A_ME));
        }else if (key.equals("StarWars")&& soundPlayer.isSoundOn() ){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_THE_FORCE_IS_WITH_YOU));
        }else if (key.equals("Pistol")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_JAMES_BOND_THEME));
        }else if (key.equals("Shotgun")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_ILL_BE_BACK));
        }else if (key.equals("Warcraft")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_RIGHT_O));
        }else if (key.equals("DrumKit")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_COMEDY_DRUM));
        }else if (key.equals("Lasso")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_WHISTLE));
        }else if (key.equals("FartPrank")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_FART_FIVE));
        }else if (key.equals("Air horn")&& soundPlayer.isSoundOn()){
            soundPlayer.playSpecificSound((SoundPlayer.SOUND_EXPLOSION));
        }
        System.out.println("----------------------set package: " + key);
        goBack();
        mainActivity.setPackage(key);
        System.out.println("--------setPackage key: " + key);
    }


    @JavascriptInterface
    public void alert(String message) {
        new AlertDialog.Builder(context)
                .setTitle("SoundPocket")
                .setMessage(message)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @JavascriptInterface
    public void getTime() {
        Date currentTime = Calendar.getInstance().getTime();
        String strDate = currentTime.toString();
        runJavaScript("callbackTimeFromAndroid(\"" + strDate + "\")");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }

    @JavascriptInterface
    public void goToSoundList() {
        loadNewHTML("soundlist.html");
    }

    @JavascriptInterface
    public void goToSettings() {
        loadNewHTML("settings.html");
    }

    /*
    @JavascriptInterface
    public void goToPackageInstruction(String specificSound) {
        System.out.println("-------------------------------goToPackageInstruction");
        System.out.println("-------------------------------goToPackageInstruction, specificSound: " + specificSound);

        specificSoundState = specificSound;
        loadNewHTML("package_instructions.html");
        System.out.println("-------------------------------instructions");

        //runJavaScript("window.location = 'package_instructions_war.html';");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }
    */

    @JavascriptInterface
    public void getSpecificSoundInfo(String specificSound) {
        System.out.println("-------------------------------getSpecificSoundInfo");

        String startPositionText = "";
        String endPositionText = "";
        String howToText = "";

        switch(specificSound) {
            //------------Shotgun
            case "Fire":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing 45 degrees up.";
                howToText = "With a quick motion, tilt your phone 45 degrees up (Your shotgun must be loaded and pumped in order to fire).";
                break;
            case "Dry Fire":
                startPositionText = "Pointing straight forward";
                endPositionText = "Pointing 45 degrees up";
                howToText = "Hold your phone pointing forward horizontally. With a quick motion, tilt it 45 degrees up (It must be unloaded or out of shots in order to dry-fire).";
                break;
            case "Pump":
                startPositionText = "Pointing straight forward away from body.";
                endPositionText = "Pointing straight forward away from body.";
                howToText = "With a quick jerking motion, move your phone towards your body and then away from it again (Your shotgun must be loaded in order to pump it).";
                break;
            case "Empty Pump":
                startPositionText = "Pointing straight forward away from body.";
                endPositionText = "Pointing straight forward away from body.";
                howToText = "With a quick jerking motion, move your phone towards your body and then away from it again (Your shotgun must be unloaded in order to empty-pump it).";
                break;
            case "Ammo Refill":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward, tilted 90 degrees to the right or left.";
                howToText = "In a quick jerking motion, tilt your phone 90 degrees to the left or right.";
                break;

            //------------Mario
            case "Jump":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "In a quick jerking motion, move your phone upwards.";
                break;
            case "Pipe":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "In a quick jerking motion, move your phone downwards.";
                break;
            case "Fireball":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing 45 degrees up.";
                howToText = "With a quick motion, tilt your phone 45 degrees up.";
                break;
            case "Yahoo":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "In a quick motion, move your phone straight up.";
                break;
            case "MammaMia":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward, tilted 90 degrees to the right or left.";
                howToText = "In a quick jerking motion, tilt your phone 90 degrees to the left or right.";
                break;
            case "Boing":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "In a quick motion, move your phone sideways.";
                break;

            //------------Drumkit
            case "Snare":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a quick motion, tilt your phone 45 degrees down so that it's now pointing horizontally forward.";
                break;
            case "Cymbal":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "In a quick jerking motion, move your phone forward away from your body.";
                break;
            case "Tom":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "In a quick motion, move your phone sideways.";
                break;

            //------------Star Wars
            case "Open":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "In a quick jerking motion, move your phone upwards (this activates the lightsaber.)";
                break;
            case "Close":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "In a quick jerking motion, move your phone downwards (this deactivates the lightsaber).";
                break;
            case "Hit":
                startPositionText = "Pointing 90 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a quick motion, tilt your phone 90 degrees down so that it's now pointing horizontally forward (The lightsaber needs to be activated in order to hit).";
                break;
            case "Swing One":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction (The lightsaber needs to be activated in order to swing).";
                break;
            case "Swing Two":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction (The lightsaber needs to be activated in order to swing).";
                break;

            //------------Pistol
            case "Shoot":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing 45 degrees up.";
                howToText = "With a quick motion, tilt your phone 45 degrees up (Your pistol must be loaded and the silencer must be off in order to shoot loudly).";
                break;
            case "Shoot Silenced":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing 45 degrees up.";
                howToText = "With a quick motion, tilt your phone 45 degrees up (Your pistol must be loaded and the silencer must be on in order to shoot silenced).";
                break;
            case "Screw On Silencer":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "Hit the top of the phone with your hand so that the phone moves towards you.";
                break;
            case "Reload":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a quick motion, move your phone sideways.";
                break;

            //------------Lasso
            case "Spin":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "Move your phone forwards and backwards in small movements, while pointing it vertically upwards in order to speed up the lasso.";
                break;
            case "Throw":
                startPositionText = "Pointing 90 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a quick motion, tilt your phone 90 degrees down so that it's now pointing horizontally forward. The lasso needs to have been spun up for at least 4 seconds in order to be able to throw it.";
                break;

            //------------Warcraft
            case "Work Work":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a quick motion, tilt your phone 45 degrees down so that it's now pointing horizontally forward.";
                break;
            case "Yes Mi Lord":
                startPositionText = "Pointing vertically up.";
                endPositionText = "Pointing vertically up.";
                howToText = "In a quick jerking motion, move your phone straight up.";
                break;
            case "Off I Go Then":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing straight forward.";
                howToText = "In a quick motion, move your phone sideways.";
                break;

            //------------Airhorn
            case "Airhorn":
                startPositionText = "Pointing straight forward.";
                endPositionText = "Pointing 45 degrees up.";
                howToText = "Tilt your phone upwards.";
                break;

            //------------FartPrank
            case "Fart 1":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 2":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 3":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 4":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 5":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 6":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;
            case "Fart 7":
                startPositionText = "Pointing 45 degrees up.";
                endPositionText = "Pointing straight forward.";
                howToText = "With a gentle motion, tilt your phone in any direction. Prank your friend by putting your phone in their back-pocket!";
                break;

                //----------etc.
        }
        runJavaScript("callbackSpecificSoundInfo(\"" + startPositionText + "\"," + "\"" + endPositionText + "\"," + "\"" + howToText + "\")");
        System.out.println("callbackSpecificSoundInfo(\"" + startPositionText + "\"," + "\"" + endPositionText + "\"," + "\"" + howToText + "\")");
        /*
        runJavaScript("callbackSpecificSoundInfo(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + startPositionText + "\"," + "\"" + endPositionText + "\"" + howToText + "\")");
        System.out.println("callbackSpecificSoundInfo(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + startPositionText + "\"," + "\"" + endPositionText + "\"" + howToText + "\")");
        */
    }

    @JavascriptInterface
    public void playSpecificSound(String sound) {

        switch(sound) {
            //------------Shotgun
            case "Fire":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
                break;
            case "Dry Fire":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_DRY_FIRE);
                break;
            case "Pump":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);
                break;
            case "Empty Pump":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_EMPTY_PUMP);
                break;
            case "Ammo Refill":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_AMMO_LOAD);
                break;

            //------------Mario
            case "Jump":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_COIN);
                break;
            case "Pipe":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_PIPE);
                break;
            case "Fireball":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FIREBALL);
                break;
            case "Yahoo":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_YAHOO);
                break;
            case "MammaMia":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_MAMMA_MIA);
                break;
            case "Boing":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_BOING);
                break;

            //------------DrumKit
            case "Snare":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_SNARE);
                break;
            case "Cymbal":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_CYMBAL);
                break;
            case "Tom":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_TOM);
                break;

            //------------Star Wars
            case "Open":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LIGHTSABER_OPEN);
                break;
            case "Close":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LIGHTSABER_CLOSE);
                break;
            case "Hit":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LIGHTSABER_HIT);
                break;
            case "Swing One":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LIGHTSABER_SWING_ONE);
                break;
            case "Swing Two":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LIGHTSABER_SWING_TWO);
                break;

            //------------Warcraft
            case "Work Work":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_WORK_WORK);
                break;
            case "Yes Mi Lord":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_YES_MI_LORD);
                break;
            case "Off I Go Then":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_OFF_I_GO_THEN);
                break;

            //------------Pistol
            case "Shoot":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_PISTOL);
                break;
            case "Shoot Silenced":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_PISTOL_SILENCED);
                break;
            case "Screw On Silencer":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_SCREW_ON_SILENCER);
                break;
            case "Reload":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_RELOAD);
                break;

            //------------Lasso
            case "Spin":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LASSO_LONG);
                break;
            case "Throw":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LASSO_THROW);
                break;

            //------------Air horn
            case "Airhorn":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_AIR_HORN);
                break;

            //------------Fart Joke
            case "Fart 1":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_ONE);
                break;
            case "Fart 2":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_TWO);
                break;
            case "Fart 3":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_THREE);
                break;
            case "Fart 4":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_FOUR);
                break;
            case "Fart 5":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_FIVE);
                break;
            case "Fart 6":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_SIX);
                break;
            case "Fart 7":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_FART_SEVEN);
                break;
        }
    }



    @JavascriptInterface
    public void goToChoosePackage() {
        System.out.println("-------------------------------goToChoosePackage");
        loadNewHTML("browse.html");
    }

    @JavascriptInterface
    public void getCurrentPackage() {
        String currentPack = mainActivity.getCurrentPackage();
        String filename = "";

        switch(currentPack) {
            case "Shotgun":
                filename = "shotgun.svg";
                break;
            case "Mario":
                filename = "mario.svg";
                break;
            case "FartPrank":
                filename = "fartprank.svg";
                break;
            case "DrumKit":
                filename = "drumkit.svg";
                break;
            case "StarWars":
                filename = "starwars.svg";
                break;
            case "Pistol":
                filename = "pistol.svg";
                break;
            case "Lasso":
                filename = "lasso.svg";
                break;
            case "Warcraft":
                filename = "warcraft.svg";
                break;
            case "Air horn":
                filename = "air horn.svg";
                break;
        }
        runJavaScript("callbackCurrentPackage(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + filename + "\")");
        System.out.println("callbackCurrentPackage(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + filename + "\")");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }

    @JavascriptInterface
    public void getSoundStatus() {
        String isSoundOn;
        if(soundPlayer.isSoundOn()) {
            isSoundOn = "true";
        } else{
            isSoundOn = "false";
        }
        runJavaScript("callbackSoundStatus(\"" + isSoundOn + "\")");
        System.out.println("callbackSoundStatus(\"" + isSoundOn + "\")");
    }

    @JavascriptInterface
    public void toggleVoice() {
        if(mainActivity.getVoiceStatus()){
            mainActivity.stopVoice();
        } else if(mainActivity.getVoiceStatus()==false){
            mainActivity.startVoice();
        }
    }
    @JavascriptInterface
    public void getVoiceStatus() {
        String isVoiceOn;
        if(mainActivity.getVoiceStatus()) {
            isVoiceOn = "true";
        } else{
            isVoiceOn = "false";
        }
        runJavaScript("callbackVoiceStatus(\"" + isVoiceOn + "\")");
        System.out.println("callbackVoiceStatus(\"" + isVoiceOn + "\")");
    }

    @JavascriptInterface
    public void getPackageList() {
        System.out.println("------getPackageList");
        String[] currentPackageList = mainActivity.getPackages();
        StringBuilder stringArray = new StringBuilder();
        stringArray.append("[");
        for (String s : currentPackageList) {
            stringArray.append("\"");
            stringArray.append(s);
            stringArray.append("\"");
            stringArray.append(",");
        }
        stringArray.deleteCharAt(stringArray.length() -1);
        stringArray.append("]");

        runJavaScript("callbackPackageList(" + stringArray.toString() + ", " + betaPacksActiveState + ")");
        System.out.println("callbackPackageList(\"" + stringArray.toString() + "\")");

    }

    @JavascriptInterface
    public void getSoundList() {
        String[] currentSoundList = mainActivity.getCurrentPackageSoundList();
        StringBuilder stringArray = new StringBuilder();
        stringArray.append("[");
        for (String s : currentSoundList) {
            stringArray.append("\"");
            stringArray.append(s);
            stringArray.append("\"");
            stringArray.append(",");
        }
        stringArray.deleteCharAt(stringArray.length() -1);
        stringArray.append("]");

        runJavaScript("callbackSoundList(" + stringArray.toString() + ")");
        System.out.println("callbackSoundList(" + stringArray.toString() + ")");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }


    //methods to make the webview function calls to run on the same thread as UI.

    private void runJavaScript(String jsFunction) {
        final String fJSFunction = jsFunction;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.evaluateJavascript("javascript: " + fJSFunction,
                        null);            }
        });
    }

    private void loadNewHTML(String filename) {
        final String filenameFinal = filename;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("file:///android_asset/www/" + filenameFinal);
            }
        });
    }

    private void goBack() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.goBack();
            }
        });
    }

    @JavascriptInterface
    public void setLock(boolean lock){
        mainActivity.setLock(lock);
    }
}