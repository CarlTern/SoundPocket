package com.dynamicdusk.soundpocket;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.*;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity{
    WebView webView;
    SoundPlayer soundPlayer;
    MyJavaScriptInterface jsHandler;
    AccelerometerManager accelerometerManager;

    private HashMap<String, AccelerometerListener> packages = new HashMap<String, AccelerometerListener>();



    protected void onCreate(Bundle savedInstanceState) {

        accelerometerManager = new AccelerometerManager();
        packages.put("Warcraft3", new Warcraft3());
        packages.put("Shotgun", new Shotgun());
        packages.put("Mario", new Mario());

        super.onCreate(savedInstanceState);
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


        this.soundPlayer = new SoundPlayer(this);
        this.jsHandler = new MyJavaScriptInterface(webView, this, soundPlayer, this);
        webView.addJavascriptInterface(jsHandler, "Android");
        webView.loadUrl("file:///android_asset/www/splash.html");
        final WebView webViewCallbackAccess = webView;

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
            AccelerometerManager.startListening(packages.get("Shotgun"));
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


    public void setPackage(String key){
        packages.get(key).setSoundPlayer(this.soundPlayer);
        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(packages.get(key));
        }

    }
}


