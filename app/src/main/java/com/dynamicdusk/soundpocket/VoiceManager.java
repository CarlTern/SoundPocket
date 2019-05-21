package com.dynamicdusk.soundpocket;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class VoiceManager implements RecognitionListener {
    private SpeechRecognizer recognizer;
    /* We only need the keyphrase to start recognition, one menu with list of choices,
   and one word that is required for method switchSearch - it will bring recognizer
   back to listening for the keyphrase*/
    private static final String KWS_SEARCH = "activate";
    private MainActivity mainActivity;
    private long timeStamp = 0;
    private SoundPlayer soundPlayer;

    public VoiceManager(SoundPlayer soundPlayer, MainActivity mainActivity) {
        this.soundPlayer = soundPlayer;
        this.mainActivity = mainActivity;
    }
    public void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {

            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(mainActivity);
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
    public void destroy(){
        recognizer.cancel();
        recognizer.shutdown();
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
            mainActivity.setPackageByVoice("Shotgun");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
               soundPlayer.playSound(SoundPlayer.SOUND_MENU_SHOTGUN);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }else if (text.equals("mario")) {
            mainActivity.setPackageByVoice("Mario");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_MARIO);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }else if (text.equals("air horn")) {
            mainActivity.setPackageByVoice("MLG");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_AIRHORN);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }else if (text.equals("warcraft")) {
            mainActivity.setPackageByVoice("Warcraft3");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_WARCRAFT);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }else if (text.equals("pistol")) {
            mainActivity.setPackageByVoice("Pistol");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_PISTOL);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        } else if (text.equals("star wars")) {
            mainActivity.setPackageByVoice("LightSaber");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_STARWARS);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        } else if (text.equals("fart prank")) {
            mainActivity.setPackageByVoice("FartPrank");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_FARTPRANK);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
        }else if (text.equals("drum kit")) {
            mainActivity.setPackageByVoice("DrumKit");
            if((Calendar.getInstance().getTimeInMillis() - timeStamp) > 2000) {
                soundPlayer.playSound(SoundPlayer.SOUND_MENU_DRUMKIT);
                timeStamp = Calendar.getInstance().getTimeInMillis();
            }
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
