package com.sukanta.multinotes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView tvCopyright = findViewById(R.id.tvCopyright);
        tvCopyright.setText(R.string.copyright);
    }
}