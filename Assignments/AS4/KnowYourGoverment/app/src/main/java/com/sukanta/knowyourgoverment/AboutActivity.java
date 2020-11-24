package com.sukanta.knowyourgoverment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView tvGoogleCivicInformationApi = findViewById(R.id.tvGoogleCivicInformationApi);
        SpannableString content = new SpannableString("Google Civic Information API");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tvGoogleCivicInformationApi.setText(content);

        TextView tvCopyright = findViewById(R.id.tvCopyright);
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy");
        String year = ft.format(date);
        tvCopyright.setText("\u00a9 " + year + ", Sukanta Sharma");
    }

    public void gotoGoogleCivicInformationApi(View v) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developers.google.com/civic-information/"));
        startActivity(i);
    }
}