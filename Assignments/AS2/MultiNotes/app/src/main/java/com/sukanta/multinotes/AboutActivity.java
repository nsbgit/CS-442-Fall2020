package com.sukanta.multinotes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Objects.requireNonNull(getSupportActionBar()).hide();

        TextView tvCopyright = findViewById(R.id.tvCopyright);

        Date date = new Date();
        String format = "yyyy";
        DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        String year = dateFormat.format(date);
        String copyrightText = String.format(getString(R.string.copyright),year);

        tvCopyright.setText(copyrightText);
    }
}