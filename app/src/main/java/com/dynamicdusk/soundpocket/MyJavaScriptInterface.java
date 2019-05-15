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


    public MyJavaScriptInterface(WebView w, Context context, SoundPlayer soundPlayer, MainActivity mainActivity) {
        this.webView = w;
        this.context = context;
        this.soundPlayer = soundPlayer;
        this.mainActivity = mainActivity;
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
        System.out.println("----------------------set package");
        mainActivity.setPackage(key);
    }

    @JavascriptInterface
    public void setPackage(String key) {
        if(key.equals("Mario")){
            soundPlayer.playSound(SoundPlayer.SOUND_ITS_A_ME);
        }
        System.out.println("----------------------set package");
        goBack();
        mainActivity.setPackage(key);
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
    public void goToPackageInstruction(String specificSound) {
        specificSoundState = specificSound;
        loadNewHTML("package_instructions.html");
        System.out.println("-------------------------------instructions");

        //runJavaScript("window.location = 'package_instructions_war.html';");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }

    @JavascriptInterface
    public void getSpecificSoundInfo() {
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
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_SHOT);
                break;
            case "Dry Fire":
                soundPlayer.playSound(SoundPlayer.SOUND_DRY_FIRE);
                break;
            case "Pump":
                soundPlayer.playSound(SoundPlayer.SOUND_SHOTGUN_RELOAD);
                break;
            case "Empty Pump":
                soundPlayer.playSound(SoundPlayer.SOUND_EMPTY_PUMP);
                break;
            case "Ammo Refill":
                soundPlayer.playSound(SoundPlayer.SOUND_AMMO_LOAD);
                break;

            //------------Mario
            case "Jump":
                soundPlayer.playSound(SoundPlayer.SOUND_COIN);
                break;
            case "Pipe":
                soundPlayer.playSound(SoundPlayer.SOUND_PIPE);
                break;
            case "Fireball":
                soundPlayer.playSound(SoundPlayer.SOUND_FIREBALL);
                break;
            case "Yahoo":
                soundPlayer.playSound(SoundPlayer.SOUND_YAHOO);
                break;
            case "MammaMia":
                soundPlayer.playSound(SoundPlayer.SOUND_MAMMA_MIA);
                break;
            case "Boing":
                soundPlayer.playSound(SoundPlayer.SOUND_BOING);
                break;
        }
    }

    @JavascriptInterface
    public void goToChoosePackage() {
        System.out.println("-------------------------------package");
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
