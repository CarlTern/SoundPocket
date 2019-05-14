package com.dynamicdusk.soundpocket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;

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
        packages.put("MLG", new MLG());
        packages.put("LightSaber", new LightSaber());
        packages.put("Pistol", new Pistol());
        packages.put("DrumKit", new DrumKit());
        packages.put("FartPrank", new FartPrank());

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
        voice.runRecognizerSetup();
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
        }, 3000);

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
                        "Fireball"
                };
                return listM;

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
        String[] returnie = new String[list.size()];
        returnie = list.toArray(returnie);
        return returnie;
    }
    public void setPackage(String key){
        packages.get(key).setSoundPlayer(this.soundPlayer);
        currentPackage = key;
        if (manager.isSupported(this)) {
            manager.startListening(packages.get(key));
        }
    }
    @SuppressLint("StaticFieldLeak")




    public void onStop() {

        super.onStop();
        // Add below if you want voice recognition to end when minimizing app
        /*
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
        */
    }



}