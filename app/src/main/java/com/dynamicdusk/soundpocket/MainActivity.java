package com.dynamicdusk.soundpocket;

import android.Manifest;
import android.annotation.SuppressLint;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
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
    private static final String KWS_SEARCH = "activate";

    /* Recognition object */
    private SpeechRecognizer recognizer;



    SoundPlayer soundPlayer;
    MyJavaScriptInterface jsHandler;
    AccelerometerManager accelerometerManager;
    private WebView webView;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;


    private HashMap<String, AccelerometerListener> packages = new HashMap<String, AccelerometerListener>();



    protected void onCreate(Bundle savedInstanceState) {
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        accelerometerManager = new AccelerometerManager();
        packages.put("Warcraft3", new Warcraft3());
        packages.put("Shotgun", new Shotgun());
        packages.put("Mario", new Mario());
        packages.put("MLG", new MLG());
        packages.put("LightSaber", new LightSaber());
        packages.put("Pistol", new Pistol());

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
    public void onDestroy() {
        super.onDestroy();
        recognizer.cancel();
        recognizer.shutdown();
    }


    public void setPackage(String key){
        packages.get(key).setSoundPlayer(this.soundPlayer);
        if (AccelerometerManager.isSupported(this)) {
            AccelerometerManager.startListening(packages.get(key));
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
                // Disable this line if you don't want recognizer to save raw
                // audio files to app's storage
                //.setRawLogDir(assetsDir)
                .getRecognizer();
        recognizer.addListener(this);
        // Create keyword-activation search.
       //recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);
        // Create your custom grammar-based search
        File menuGrammar = new File(assetsDir, "words.gram");
        recognizer.addKeywordSearch(KWS_SEARCH, menuGrammar);
    }


    public void onStop() {
        super.onStop();
        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }


    public void onPartialResult(Hypothesis hypothesis) {

        if (hypothesis == null)
            return;
        String text = hypothesis.getHypstr();
        recognizer.cancel();

      /*  if (text.equals(KEYPHRASE)) {

            setPackage("Mario");

       } else
         */
           if (text.equals("shotgun")) {
            setPackage("Shotgun");
        }else if (text.equals("mario")) {
            setPackage("Mario");
        }else if (text.equals("dab machine")) {
            setPackage("MLG");
        }else if (text.equals("warcraft")) {
            setPackage("Warcraft3");
        }else if (text.equals("pistol")) {
            setPackage("Pistol");
        } else if (text.equals("star wars")) {
            setPackage("LightSaber");
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


    private void callPopup() {

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = layoutInflater.inflate(R.layout.support_simple_spinner_dropdown_item, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT,
                true);

        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }



}