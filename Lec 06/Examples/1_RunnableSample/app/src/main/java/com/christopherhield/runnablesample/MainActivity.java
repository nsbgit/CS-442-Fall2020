package com.christopherhield.runnablesample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void execRunnable(View v) {

        String data = editText.getText().toString();

        SampleRunnable sa = new SampleRunnable(this, data);
        new Thread(sa).start();

        Log.d(TAG, "execRunnable: CONTINUING after SampleRunnable Start");
    }

    public void acceptResult(String s) {
        ((TextView) findViewById(R.id.textView)).setText(s);
    }
}
