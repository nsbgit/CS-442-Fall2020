package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    private ImageView imageView;
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
        imageView = (ImageView) findViewById(R.id.imageView);
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

        loadRemoteImage(official.getPhotoUrl());
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

    private void loadRemoteImage(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'

        final long start = System.currentTimeMillis(); // Used for timing

        Picasso.get().load(imageURL)
                .error(R.drawable.missing)
                .placeholder(R.drawable.placeholder)
                .into(imageView); // Use this if you don't want a callback
//                .into(imageView,
//                        new Callback() {
//                            @Override
//                            public void onSuccess() {
//                                Log.d(TAG, "onSuccess: Size: " +
//                                        ((BitmapDrawable) imageView.getDrawable()).getBitmap().getByteCount());
//                                long dur = System.currentTimeMillis() - start;
//                                Log.d(TAG, "onSuccess: Time: " + dur);
//                            }
//
//                            @Override
//                            public void onError(Exception e) {
//                                Log.d(TAG, "onError: " + e.getMessage());
//                            }
//                        });
    }
}