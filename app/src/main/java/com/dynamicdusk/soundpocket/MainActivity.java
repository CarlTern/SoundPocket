package com.dynamicdusk.soundpocket;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.pm.PackageManager;
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
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class MainActivity extends AppCompatActivity implements
        RecognitionListener {
    /* We only need the keyphrase to start recognition, one menu with list of choices,
       and one word that is required for method switchSearch - it will bring recognizer
       back to listening for the keyphrase*/
    private static final String KWS_SEARCH = "activate package";
    private String currentPackage = "Shotgun";
    /* Recognition object */
    private SpeechRecognizer recognizer;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    SoundPlayer soundPlayer;
    MyJavaScriptInterface jsHandler;
    WebView webView;
    AccelerometerManager manager = new AccelerometerManager();


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
        webView = new WebView(this);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        runRecognizerSetup();

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
    public void onDestroy() {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
    }


    public String getCurrentPackage(){
        return currentPackage;
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
    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {

            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(MainActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }

                return null;
            }

            protected void onPostExecute(Exception result) {
                if (result != null) {
                    System.out.println(result.getMessage());
                } else {
                   switchSearch(KWS_SEARCH);
                }
            }

        }.execute();
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))
                //Set threshold for keyword
                .setKeywordThreshold((float)1e-5)
                .getRecognizer();
        recognizer.addListener(this);
        // Create your custom grammar-based search
        File menuGrammar = new File(assetsDir, "words.gram");
        recognizer.addKeywordSearch(KWS_SEARCH, menuGrammar);
    }


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


    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();
        System.out.println(text);
        recognizer.cancel();

      /*  if (text.equals(KEYPHRASE)) {

            setPackage("Mario");

       } else
         */
           if (text.equals("shotgun")) {
            setPackage("Shotgun");
            soundPlayer.playSound(SoundPlayer.SOUND_MENU_SHOTGUN);
        }else if (text.equals("mario")) {
            setPackage("Mario");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_MARIO);
        }else if (text.equals("dab machine")) {
            setPackage("MLG");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_AIRHORN);
        }else if (text.equals("warcraft")) {
            setPackage("Warcraft3");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_WARCRAFT);
        }else if (text.equals("pistol")) {
            this.setPackage("Pistol");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_PISTOL);
        } else if (text.equals("star wars")) {
            this.setPackage("LightSaber");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_STARWARS);
        } else if (text.equals("fart prank")) {
               this.setPackage("FartPrank");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_FARTPRANK);
           }else if (text.equals("drum kit")) {
               this.setPackage("DrumKit");
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_DRUMKIT);
           }


        switchSearch(KWS_SEARCH);

    }


    public void onResult(Hypothesis hypothesis) {
    }

    public void onBeginningOfSpeech() {
    }


    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
        switchSearch(KWS_SEARCH);
        //recognizer.stop();
        //recognizer.startListening(KEYPHRASE);
    }

    private void switchSearch(String searchName) {
        recognizer.cancel();
        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 100);

    }


    public void onError(Exception error) {

        System.out.println(error.getMessage());
    }


    public void onTimeout() {
       // switchSearch(KWS_SEARCH);
    }






}