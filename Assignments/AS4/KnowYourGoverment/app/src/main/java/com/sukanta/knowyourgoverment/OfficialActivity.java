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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView tvAddressLabel;
    private TextView tvAddressValue;
    private TextView tvPhoneLabel;
    private TextView tvPhoneValue;
    private TextView tvEmailLabel;
    private TextView tvEmailValue;
    private TextView tvWebsiteLabel;
    private TextView tvWebsiteValue;
    private ImageView ivOfficialPhoto;
    private ImageView ivPartyLogo;
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
        tvAddressValue = (TextView) findViewById(R.id.tvAddressValue);
        tvAddressLabel = (TextView) findViewById(R.id.tvAddressLabel);
        tvPhoneLabel = (TextView) findViewById(R.id.tvPhoneLabel);
        tvPhoneValue = (TextView) findViewById(R.id.tvPhoneValue);
        tvEmailLabel = (TextView) findViewById(R.id.tvEmailLabel);
        tvEmailValue = (TextView) findViewById(R.id.tvEmailValue);
        tvWebsiteLabel = (TextView) findViewById(R.id.tvWebsiteLabel);
        tvWebsiteValue = (TextView) findViewById(R.id.tvWebsiteValue);
        ivOfficialPhoto = (ImageView) findViewById(R.id.ivOfficialPhoto);
        ivPartyLogo = (ImageView) findViewById(R.id.ivPartyLogo);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
    }

    private void loadData() {
        tvLocation.setText(locationText);

        if (official.getParty().toUpperCase().contains("DEMOCRATIC")) {
            tvParty.setVisibility(View.VISIBLE);
            scrollView.setBackgroundColor(Color.BLUE);
            ivPartyLogo.setImageResource(R.drawable.dem_logo);
            ivPartyLogo.setVisibility(View.VISIBLE);
        }
        else if (official.getParty().toUpperCase().contains("REPUBLICAN")) {
            tvParty.setVisibility(View.VISIBLE);
            scrollView.setBackgroundColor(Color.RED);
            ivPartyLogo.setImageResource(R.drawable.rep_logo);
            ivPartyLogo.setVisibility(View.VISIBLE);
        }
        else {
            tvParty.setVisibility(View.INVISIBLE);
            scrollView.setBackgroundColor(Color.BLACK);
            ivPartyLogo.setVisibility(View.INVISIBLE);
        }

        tvOffice.setText(official.getOfficeName());
        tvName.setText(official.getOfficialName());
        tvParty.setText(String.format("(%s)", official.getParty()));

        loadOfficialPhoto(official.getPhotoUrl());

        String officialFullAddress = official.getOfficialFullAddress();
        tvAddressValue.setText(officialFullAddress);
        if (officialFullAddress != null && !officialFullAddress.isEmpty()) {
            tvAddressLabel.setVisibility(View.GONE);
            tvAddressValue.setVisibility(View.GONE);
        }
        else {
            tvAddressLabel.setVisibility(View.VISIBLE);
            tvAddressValue.setVisibility(View.VISIBLE);
        }

        String phone = official.getPhone();
        tvPhoneValue.setText(phone);
        if (phone != null && !phone.isEmpty()) {
            tvPhoneLabel.setVisibility(View.GONE);
            tvPhoneValue.setVisibility(View.GONE);
        }
        else {
            tvPhoneLabel.setVisibility(View.VISIBLE);
            tvPhoneValue.setVisibility(View.VISIBLE);
        }

        String email = official.getEmail();
        tvEmailValue.setText(email);
        if (email != null && !email.isEmpty()) {
            tvEmailLabel.setVisibility(View.GONE);
            tvEmailValue.setVisibility(View.GONE);
        }
        else {
            tvEmailLabel.setVisibility(View.VISIBLE);
            tvEmailValue.setVisibility(View.VISIBLE);
        }

        String website = official.getUrl();
        tvWebsiteValue.setText(website);
        if (website != null && !website.isEmpty()) {
            tvWebsiteLabel.setVisibility(View.GONE);
            tvWebsiteValue.setVisibility(View.GONE);
        }
        else {
            tvWebsiteLabel.setVisibility(View.VISIBLE);
            tvWebsiteValue.setVisibility(View.VISIBLE);
        }
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
}