package com.sukanta.multinotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().hide();

        TextView tvCopyright = findViewById(R.id.tvCopyright);
        tvCopyright.setText("\u00a9" + "2020, Sukanta Sharma");
    }
}