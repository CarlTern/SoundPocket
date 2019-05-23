package com.dynamicdusk.soundpocket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.AsyncTask;

import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends AppCompatActivity {




    private String currentPackage = "Shotgun";
    /* Recognition object */

    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    SoundPlayer soundPlayer;
    MyJavaScriptInterface jsHandler;
    WebView webView;
    AccelerometerManager manager = new AccelerometerManager();
    private long timeStamp = 0;
    private VoiceManager voice;
    private boolean isVoiceOn = false;
    private LightSaber saber = new LightSaber();


    private HashMap<String, AccelerometerListener> packages = new HashMap<String, AccelerometerListener>();



    protected void onCreate(Bundle savedInstanceState) {


        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        packages.put("Warcraft3", new Warcraft3());
        packages.put("Shotgun", new Shotgun());
        packages.put("Mario", new Mario());
        packages.put("Air horn", new MLG());
        packages.put("Star Wars", saber);
        packages.put("Pistol", new Pistol());
        packages.put("DrumKit", new DrumKit());
        packages.put("FartPrank", new FartPrank());
        packages.put("Lasso", new Lasso());

        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(Color.rgb(255, 200, 37));
        webView = new WebView(this);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        settings.setBuiltInZoomControls(false);
        webView.setWebChromeClient(new WebChromeClient()); //making js alerts work

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        this.soundPlayer = new SoundPlayer(this);
        this.jsHandler = new MyJavaScriptInterface(webView, this, soundPlayer, this);
        webView.addJavascriptInterface(jsHandler, "Android");
        webView.loadUrl("file:///android_asset/www/splash.html");
        final WebView webViewCallbackAccess = webView;

        voice = new VoiceManager(soundPlayer,this);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //voice.runRecognizerSetup(); // this activates voice at app start
        /*
        webViewCallbackAccess.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK && webViewCallbackAccess.canGoBack())
                {
                    System.out.println(webViewCallbackAccess.getUrl());
                    String temp = webViewCallbackAccess.getUrl();
                    if (temp == "file:///android_asset/www/index.html") {
                        System.out.println("-----temp equal");
                    } else {
                        System.out.println("-----temp not equal");
                    }

                    if(webViewCallbackAccess.getUrl() == "file:///android_asset/www/index.html") {
                        System.out.println("-----------on index, return");

                        return false;
                    }
                    webViewCallbackAccess.goBack();
                    webViewCallbackAccess.clearHistory();
                    return true;
                }
                return false;
            }
        } );

        */



        packages.get("Shotgun").setSoundPlayer(this.soundPlayer);
        if (AccelerometerManager.isSupported(this)) {
            manager.startListening(packages.get("Shotgun"));
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        webViewCallbackAccess.loadUrl("file:///android_asset/www/index.html");
                    }
                });
            }
        }, 500);

    }

    protected void onResume(){
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    public void onWindowFocusChanged(boolean bool){
        if (bool){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        }
    }
    public void onDestroy() {
        super.onDestroy();
        voice.destroy();

    }


    public String getCurrentPackage(){
        return currentPackage;
    }

    public String[] getCurrentPackageSoundList(){
        switch(currentPackage) {
            case "Shotgun":
                String[] listS = {
                        "Fire",
                        "Dry Fire",
                        "Pump",
                        "Empty Pump",
                        "Ammo Refill"
                };
                return listS;

            case "Mario":
                String[] listM = {
                        "Jump",
                        "Pipe",
                        "Fireball",
                        "Yahoo",
                        "MammaMia",
                        "Boing"
                };
                return listM;

            case "DrumKit":
                String[] listD = {
                        "Snare",
                        "Cymbal",
                        "Tom"
                };
                return listD;

            case "Star Wars":
                String[] listL = {
                        "Open",
                        "Close",
                        "Pulse",
                        "Hit",
                        "Swing One",
                        "Swing Two"
                };
                return listL;

            case "Warcraft3":
                String[] listW = {
                        "Work Work",
                        "Yes Mi Lord",
                        "Off I Go Then",
                };
                return listW;

            case "Pistol":
                String[] listP = {
                        "Shoot",
                        "Shoot Silenced",
                        "Screw On Silencer",
                        "Reload"
                };
                return listP;

                case "Lasso":
                String[] listLasso = {
                        "Lasso spin",
                        "Lasso spin two",
                        "Lasso spin three",
                        "Lasso throw",
                };
                return listLasso;

            case "Air horn":
                String[] listMLG = {
                        "Airhorn"
                };
                return listMLG;

            case "FartPrank":
                String[] listF = {
                        "Fart 1",
                        "Fart 2",
                        "Fart 3",
                        "Fart 4",
                        "Fart 5",
                        "Fart 6",
                        "Fart 7",

                };
                return listF;

            default:
                String[] empty = {};
                return empty;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        if(webView.getUrl().equals("file:///android_asset/www/index.html")) {
                            System.out.println("-----------on index, return");
                            finish();
                        }
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    public String[] getPackages(){
        ArrayList <String> list= new ArrayList<String>();
        for (String name : packages.keySet()){
            list.add(name);
        }
        list.add("NOT WORKING");
        String[] returnie = new String[list.size()];
        returnie = list.toArray(returnie);
        return returnie;
    }

    public void setPackageByVoice(String key) {
        setPackage(key);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                webView.reload(); //denna kraschar webbläsaren. Vad är syftet? Kan göras genom att man kallar på reload genom en tråd.
            }
        });

    }
    public void setPackage(String key){
        if(currentPackage.equals("Star Wars")&&saber.isOn()){
            packages.get(currentPackage).killLoop();
        }
        packages.get(key).setSoundPlayer(this.soundPlayer);
        currentPackage = key;
        float[] accThresh = packages.get(key).getAccThresholds();
        float[] gyroThresh = packages.get(key).getGyroThresholds();
        manager.changeAccThreshold(accThresh[0], accThresh[1], accThresh[2]);
        manager.changeGyroThreshold(gyroThresh[0], gyroThresh[1], gyroThresh[2]);
        if (manager.isSupported(this)) {
            manager.startListening(packages.get(key));
        }
    }
    @SuppressLint("StaticFieldLeak")

    public void onStop() {

        super.onStop();
        // Add below if you want voice recognition to end when minimizing app

        //voice.destroy();

    }

    public void startVoice(){
        voice.runRecognizerSetup();
        isVoiceOn = true;
    }

    public void stopVoice(){
        voice.destroy();
        isVoiceOn = false;
    }

    public boolean getVoiceStatus(){
        return isVoiceOn;
    }




}