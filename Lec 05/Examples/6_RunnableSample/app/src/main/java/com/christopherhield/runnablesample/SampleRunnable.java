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
    // and
    // If we make it to Mars, we're not going to be able to bring everything humankind needs to stay for an extended period of time -- or to construct colonies on the red planet.

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

        // Find min and max sized word
        for (String s : words) {
            int len = s.trim().length();
            sizeSum += len;
            if (len < minSize) {
                minSize = len; // New min found
                minWord= s;
            }
            if (len > maxSize) {
                maxSize = len; // New max found
                maxWord= s;
            }
        }
        sb.append(String.format(Locale.getDefault(), "Smallest word: '%s'%n", minWord));
        sb.append(String.format(Locale.getDefault(), "Largest word: '%s'%n", maxWord));
        sb.append(String.format(Locale.getDefault(), "Avg word length: '%d'%n", sizeSum/words.length));

        Log.d(TAG, "run: DONE");


        // Note: Can't do something to modify the UI from an
        // app-started thread. If you do, it will crash the app.
        // Instead, you need to ask a context object (an activity)
        // to do that work on the UI thread
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.acceptResult(sb.toString());
            }
        });

    }
}
