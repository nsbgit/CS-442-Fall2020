package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends AppCompatActivity {
    private static final String TAG = "PhotoDetailActivity";
    private static final String OFFICIAL_KEY = "OFFICIAL";
    private static final String LOCATION_KEY = "LOCATION";
    private ConstraintLayout constraintLayout;
    private Official official;
    private String locationText = "";
    private TextView tvLocation;
    private TextView tvOffice;
    private TextView tvName;
    private ImageView ivOfficialPhoto;
    private ImageView ivPartyLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        initialize();
        getData();
        loadData();
    }

    private void initialize() {
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvOffice = (TextView) findViewById(R.id.tvOffice);
        tvName = (TextView) findViewById(R.id.tvName);
        ivOfficialPhoto = (ImageView) findViewById(R.id.ivOfficialPhoto);
        ivPartyLogo = (ImageView) findViewById(R.id.ivPartyLogo);
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

    private void loadData() {
        tvLocation.setText(locationText);

        if (official.getParty().toUpperCase().contains("DEMOCRATIC")) {
            constraintLayout.setBackgroundColor(Color.BLUE);
            ivPartyLogo.setImageResource(R.drawable.dem_logo);
            ivPartyLogo.setVisibility(View.VISIBLE);
        }
        else if (official.getParty().toUpperCase().contains("REPUBLICAN")) {
            constraintLayout.setBackgroundColor(Color.RED);
            ivPartyLogo.setImageResource(R.drawable.rep_logo);
            ivPartyLogo.setVisibility(View.VISIBLE);
        }
        else {
            constraintLayout.setBackgroundColor(Color.BLACK);
            ivPartyLogo.setVisibility(View.INVISIBLE);
        }

        tvOffice.setText(official.getOfficeName());
        tvName.setText(official.getOfficialName());

        loadOfficialPhoto(official.getPhotoUrl());
    }

    private void loadOfficialPhoto(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'
        if (imageURL == null || imageURL.isEmpty()) {
            Log.d(TAG, "loadOfficialPhoto: Empty Image URL");
            ivOfficialPhoto.setImageResource(R.drawable.missing);
            return;
        }

        if (!isNetworkAvailable()) {
            Log.d(TAG, "loadOfficialPhoto: No network available");
            ivOfficialPhoto.setImageResource(R.drawable.brokenimage);
            return;
        }

//        final long start = System.currentTimeMillis(); // Used for timing

        Picasso.get().load(imageURL)
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(ivOfficialPhoto); // Use this if you don't want a callback
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

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
//            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "isNetworkAvailable: Cannot access ConnectivityManager");
            return false;
        }

        //noinspection deprecation
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}