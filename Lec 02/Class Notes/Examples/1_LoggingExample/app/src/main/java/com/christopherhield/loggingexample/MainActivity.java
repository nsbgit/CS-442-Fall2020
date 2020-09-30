package com.christopherhield.loggingexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // Type "logt" and then "<TAB>" to have the
    // log tag added automatically
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Type in "logd" and then "<TAB>" to have
        // an empty log statement added automatically
        Log.d(TAG, "onCreate: This is in onCreate!");

        String value = "Sukanta";
        ///UNcomment the below to see a NPE
        Log.d(TAG, "onCreate: value size: " + value.length());
    }
}
