package com.example.christopher.sharedprefs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyProjectSharedPreference myPrefs;
    private EditText data1;
    private EditText data2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        data1 = findViewById(R.id.editData1);
        data2 = findViewById(R.id.editData2);

        myPrefs = new MyProjectSharedPreference(this);
        data1.setText(myPrefs.getValue(getString(R.string.data1Key)));
        data2.setText(myPrefs.getValue(getString(R.string.data2Key)));
    }

    public void saveAll(View v) {
        Log.d(TAG, "saveAll: ");
        save1(v);
        save2(v);
    }

    public void save1(View v) {
        Log.d(TAG, "save1: ");
        String d1 = data1.getText().toString();
        myPrefs.save(getString(R.string.data1Key), d1);
    }

    public void save2(View v) {
        Log.d(TAG, "save2: ");
        String d2 = data2.getText().toString();
        myPrefs.save(getString(R.string.data2Key), d2);
    }

    public void remove1(View v) {
        Log.d(TAG, "remove1: ");
        myPrefs.removeValue(getString(R.string.data1Key));
        data1.setText("");
    }

    public void remove2(View v) {
        Log.d(TAG, "remove2: ");
        myPrefs.removeValue(getString(R.string.data2Key));
        data2.setText("");
    }

    public void clearAll(View v) {
        Log.d(TAG, "clearAll: ");

        myPrefs.clearAll();
        data1.setText("");
        data2.setText("");

    }
}
