package com.dynamicdusk.soundpocket;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.*;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity{

    SoundPlayer soundPlayer;
    MyJavaScriptInterface jsHandler;
    AccelerometerManager accelerometerManager;

    private HashMap<String, AccelerometerListener> packages = new HashMap<String, AccelerometerListener>();

    protected void onCreate(Bundle savedInstanceState) {

        accelerometerManager = new AccelerometerManager();
        packages.put("Warcraft3", new Warcraft3());
        packages.put("Shotgun", new Shotgun());
        packages.put("Mario", new Mario());
        packages.put("MLG", new MLG());
        packages.put("LightSaber", new LightSaber());
        packages.put("Pistol", new Pistol());

        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
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
        packages.get("Shotgun").setSoundPlayer(this.soundPlayer);
        if (AccelerometerManager.isSupported(this)) {
            accelerometerManager.startListening(packages.get("Shotgun"));
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

    public void setPackage(String key){
        AccelerometerListener al = packages.get(key);
        al.setSoundPlayer(this.soundPlayer);
        if (AccelerometerManager.isSupported(this)) {
            accelerometerManager.startListening(packages.get(key));
            float [] thresholds = al.getThresholds();
            accelerometerManager.changeThreshold(thresholds[0], thresholds[1], thresholds[2]);
        }
    }
}


