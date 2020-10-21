package com.sukanta.stockwatch;

public class NameDownloaderAsyncTask implements Runnable {

    private MainActivity mainActivity;

    public NameDownloaderAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        getData();
    }

    private void getData() {
        // TODO get data download here


        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO send data to main activity
                mainActivity.receiveNameDownloaderData(null);
            }
        });
    }
}
