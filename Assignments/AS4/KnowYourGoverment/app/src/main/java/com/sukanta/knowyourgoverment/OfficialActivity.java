package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class OfficialActivity extends AppCompatActivity {

    private static final String TAG = "OfficialActivity";
    private static final String OFFICIAL_KEY = "OFFICIAL";
    private static final String LOCATION_KEY = "LOCATION";
    private Official official;
    private String locationText = "";
    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official);

        initialize();
        getData();
        loadData();
    }

    private void initialize() {
        tvLocation = findViewById(R.id.tvLocation);
    }

    private void loadData() {
        tvLocation.setText(locationText);
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