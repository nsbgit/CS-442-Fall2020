package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity {

    private static final String TAG = "OfficialActivity";
    private static final String OFFICIAL_KEY = "OFFICIAL";
    private static final String LOCATION_KEY = "LOCATION";
    private Official official;
    private String locationText = "";
    private TextView tvLocation;
    private TextView tvOffice;
    private TextView tvName;
    private TextView tvParty;
    private ConstraintLayout constraintLayout;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        initialize();
        getData();
        loadData();
    }

    private void initialize() {
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvOffice = (TextView) findViewById(R.id.tvOffice);
        tvName = (TextView) findViewById(R.id.tvName);
        tvParty = (TextView) findViewById(R.id.tvParty);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    private void loadData() {
        tvLocation.setText(locationText);

        if (official.getParty().toUpperCase().contains("DEMOCRATIC")) {
            scrollView.setBackgroundColor(Color.BLUE);
        }
        else if (official.getParty().toUpperCase().contains("REPUBLICAN")) {
            scrollView.setBackgroundColor(Color.RED);
        }
        else {
            scrollView.setBackgroundColor(Color.BLACK);
        }

        tvOffice.setText(official.getOfficeName());
        tvName.setText(official.getOfficialName());
        tvParty.setText(String.format("(%s)", official.getParty()));
    }

    private void getData() {
        Log.d(TAG, "getData: ");
        try {
            if (getIntent().hasExtra(OFFICIAL_KEY)) {
                official = (Official) getIntent().getSerializableExtra(OFFICIAL_KEY);
            } else {
                official = new Official();
            }
            if (getIntent().hasExtra(LOCATION_KEY)) {
                locationText = getIntent().getStringExtra(LOCATION_KEY);
            } else {
                locationText = "";
            }
        } catch (Exception e) {
            Log.e(TAG, "getData: ", e);
            official = new Official();
        }
    }
}