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
    public void goToPackageInstruction() {

        //get the state i.e. the currently chosen package
        String chosenPack = "";
        if (chosenPack.equals("")) {
            //put the checks here
        } else {

        }

        loadNewHTML("package_instructions_war.html");
        System.out.println("-------------------------------instructions");

        //runJavaScript("window.location = 'package_instructions_war.html';");
        //calls the function callbackTimeFromAndroid("strDate") in JS
    }

    @JavascriptInterface
    public void goToChoosePackage() {
        System.out.println("-------------------------------package");
        loadNewHTML("browse.html");
        //runJavaScript("window.location = 'package_instructions_war.html';");
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
}
