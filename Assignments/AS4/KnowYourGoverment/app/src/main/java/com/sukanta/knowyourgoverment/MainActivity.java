package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadData("Chicago");
    }

    public void downloadData(String address) {
        new Thread(new DataDownloader(this, address)).start();
    }

    public void receiveDataDownloaderData(Office office, List<Official> officialList) {
        Log.d(TAG, "receiveDataDownloaderData: officialList size" + officialList.size());
    }
}