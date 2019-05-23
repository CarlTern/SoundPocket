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
        }
    }




    @JavascriptInterface
    public void setSound(String key) {
        System.out.println("----------------------setSound: " + key);
        mainActivity.setPackage(key);
    }

    @JavascriptInterface
    public void setPackage(String key) {
        if(key.equals("Mario")){
            soundPlayer.playSound(SoundPlayer.SOUND_ITS_A_ME);
        } else if (key.equals("Star Wars")){
            soundPlayer.playSound(SoundPlayer.SOUND_THE_FORCE_IS_WITH_YOU);
        } else if (key.equals("Pistol")){
            soundPlayer.playSound(SoundPlayer.SOUND_JAMES_BOND_THEME);
        }else if (key.equals("Shotgun")){
            soundPlayer.playSound(SoundPlayer.SOUND_ILL_BE_BACK);
        }
        else if (key.equals("Warcraft")){
            soundPlayer.playSound(SoundPlayer.SOUND_RIGHT_O);
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

    @JavascriptInterface
    public void getSpecificSoundInfo() {
        System.out.println("-------------------------------getSpecificSoundInfo");

        String packageName = mainActivity.getPackageName();
        String text = "";
        String filename = "";

        switch(specificSoundState) {
            //------------Shotgun
            case "Fire":
                text = "fill in instruction text";
                filename = "shotgun-fire-instruction.svg";
                break;
            case "Dry Fire":
                text = "fill in instruction text";
                filename = "shotgun-dry-fire-instruction.svg";
                break;
            case "Pump":
                text = "fill in instruction text";
                filename = "shotgun-pump-instruction.svg";
                break;
            case "Empty Pump":
                text = "fill in instruction text";
                filename = "shotgun-empty-pump-instruction.svg";
                break;
            case "Ammo Refill":
                text = "fill in instruction text";
                filename = "shotgun-ammo-refill-instruction.svg";
                break;

            //------------Mario
            case "Jump":
                text = "fill in instruction text";
                filename = "mario-jump-instruction.svg";
                break;
            case "Pipe":
                text = "fill in instruction text";
                filename = "mario-pipe-instruction.svg";
                break;
            case "Fireball":
                text = "fill in instruction text";
                filename = "mario-fireball-instruction.svg";
                break;
        }
        runJavaScript("callbackSpecificSoundInfo(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + specificSoundState + "\"," + "\"" + text + "\"," + "\"" + filename + "\")");
        System.out.println("callbackSpecificSoundInfo(\"" + mainActivity.getCurrentPackage() + "\"," + "\"" + specificSoundState + "\"," + "\"" + text + "\"," + "\"" + filename + "\")");
        //calls the function callbackTimeFromAndroid("strDate") in JS
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
            case "Pulse":
                    //Måste fixas. Loopas för länge.
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
            case "Lasso spin":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LASSO_SPIN);
                break;
            case "Lasso spin two":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LASSO_SPIN_MORE);
                break;
            case "Lasso spin three":
                soundPlayer.playSpecificSound(SoundPlayer.SOUND_LASSO_SPIN_MOST);
                break;
            case "Lasso throw":
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
}