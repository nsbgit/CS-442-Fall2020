package com.example.christopher.runnable;

import android.util.Log;
import java.util.Date;

public class MyRunnable implements Runnable {

    private static final String TAG = "MyRunnable";
    private MainActivity mainActivity;
    private long seconds;

    MyRunnable(MainActivity mainActivity, long seconds) {
        this.mainActivity = mainActivity;
        this.seconds = seconds;
    }

    @Override
    public void run() {

        Log.d(TAG, "run: Starting background execution");

        if (seconds == 0)
            return;

        for (int i = 0; i < seconds; i++) {

            try {
                Thread.sleep(1000); // 1 second in millis - pretend to do some work here
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            displayProgress(i + 1);

            Log.d(TAG, "run: Second = " + (i + 1));
        }

        Log.d(TAG, "doInBackground: Done - returning timestamp");

        // Don't try to update the UI from this thread - do
        // that on the UI thread
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.whenRunnableIsDone(MainActivity.formatter.format(new Date()));
            }
        });

    }

    private void displayProgress(final int value) {
        // Don't try to update the UI from this thread - do
        // that on the UI thread
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.updateProgressBar(value);
            }
        });
        Log.d(TAG, "publishProgress: " + value);
    }
}
