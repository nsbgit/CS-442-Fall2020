package com.christopherhield.runnablesample;

import android.util.Log;
import java.util.Locale;

public class SampleRunnable implements Runnable {

    private static final String TAG = "SampleRunnable";
    private MainActivity mainActivity;
    private String data;

    SampleRunnable(MainActivity mainActivity, String data) {
        this.mainActivity = mainActivity;
        this.data = data;
    }


    // Sample sentence:
    // Astronomers have found a new stream of stars in our Milky Way galaxy, but they likely didn't originate there, according to a new study.
    //
    // The first systematically documented measurements of radiation on the moon were undertaken in January 2019 when China's Chang'e 4 robotic spacecraft mission landed on the far side of the Moon, according to a new study in the journal Science Advances.


    @Override
    public void run() {
        // This method runs in it's own thread
        Log.d(TAG, "run: STARTING");

        final StringBuilder sb = new StringBuilder();

        String[] words = data.split(" ");
        sb.append(String.format("Number of words: %s%n%n", words.length));

        String minWord = "", maxWord = "";
        int minSize = Integer.MAX_VALUE, maxSize = Integer.MIN_VALUE;
        int sizeSum = 0;

        for (String s : words) {
            int len = s.trim().length();
            sizeSum += len;
            if (len < minSize) {
                minSize = len;
                minWord= s;
            }
            if (len > maxSize) {
                maxSize = len;
                maxWord= s;
            }
        }
        sb.append(String.format(Locale.getDefault(), "Smallest word: '%s'%n", minWord));
        sb.append(String.format(Locale.getDefault(), "Largest word: '%s'%n", maxWord));
        sb.append(String.format(Locale.getDefault(), "Avg word length: '%d'%n", sizeSum/words.length));

        Log.d(TAG, "run: DONE");


        // Can't do something to modify the UI from a app-started thread
        // If you do, it will crash the app.
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.acceptResult(sb.toString());
            }
        });

    }
}
