package com.sukanta.knowyourgoverment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private ImageView ivFacebook;
    private ImageView ivTwitter;
    private ImageView ivYouTube;
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
        ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ivYouTube = (ImageView) findViewById(R.id.ivYouTube);
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
            tvAddressLabel.setVisibility(View.VISIBLE);
            tvAddressValue.setVisibility(View.VISIBLE);
        }
        else {
            tvAddressLabel.setVisibility(View.INVISIBLE);
            tvAddressValue.setVisibility(View.INVISIBLE);
        }

        String phone = official.getPhone();
        tvPhoneValue.setText(phone);
        if (phone != null && !phone.isEmpty()) {
            tvPhoneLabel.setVisibility(View.VISIBLE);
            tvPhoneValue.setVisibility(View.VISIBLE);
        }
        else {
            tvPhoneLabel.setVisibility(View.INVISIBLE);
            tvPhoneValue.setVisibility(View.INVISIBLE);
        }

        String email = official.getEmail();
        tvEmailValue.setText(email);
        if (email != null && !email.isEmpty()) {
            tvEmailLabel.setVisibility(View.VISIBLE);
            tvEmailValue.setVisibility(View.VISIBLE);
        }
        else {
            tvEmailLabel.setVisibility(View.INVISIBLE);
            tvEmailValue.setVisibility(View.INVISIBLE);
        }

        String website = official.getUrl();
        tvWebsiteValue.setText(website);
        if (website != null && !website.isEmpty()) {
            tvWebsiteLabel.setVisibility(View.VISIBLE);
            tvWebsiteValue.setVisibility(View.VISIBLE);
        }
        else {
            tvWebsiteLabel.setVisibility(View.INVISIBLE);
            tvWebsiteValue.setVisibility(View.INVISIBLE);
        }

        String facebookId = official.getFacebookId();
        if (facebookId != null && !facebookId.isEmpty()) {
            ivFacebook.setVisibility(View.VISIBLE);
        }
        else {
            ivFacebook.setVisibility(View.GONE);
        }

        String twitterId = official.getTwitterId();
        if (twitterId != null && !twitterId.isEmpty()) {
            ivTwitter.setVisibility(View.VISIBLE);
        }
        else {
            ivTwitter.setVisibility(View.GONE);
        }

        String youTubeId = official.getYouTubeId();
        if (youTubeId != null && !youTubeId.isEmpty()) {
            ivYouTube.setVisibility(View.VISIBLE);
        }
        else {
            ivYouTube.setVisibility(View.GONE);
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

    private void errorDialog(String msg) {
        Log.d(TAG, "errorDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(msg == null)
            msg = "Data can not be downloaded without a Network Connection";
        builder.setTitle("Network Connection Error");
        builder.setMessage(msg);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clickMap(View v) {
        String address = official.getOfficialAddress();
        address = "Shedd Aquarium, 1200 S. Lake Shore Drive, Chicago, IL, 60605";
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));

        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        intent.setPackage("com.google.android.apps.maps");
//        intent.setPackage("com.google.android.gms.maps");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            errorDialog("No Application found that handles ACTION_VIEW (geo) intents");
        }
    }

    public void clickFacebook(View v) {
        String fbName = official.getFacebookId();
        String FACEBOOK_URL = "https://www.facebook.com/" + fbName;

        Intent intent;
        String urlToUse;
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);

            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                urlToUse = "fb://page/" + fbName;
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlToUse));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(FACEBOOK_URL));
        }

        startActivity(intent);
    }

    public void clickTwitter(View v) {
        String user = official.getTwitterId();
        String twitterAppUrl = "twitter://user?screen_name=" + user;
        String twitterWebUrl = "https://twitter.com/" + user;

        Intent intent;
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterAppUrl));
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterWebUrl));
        }
        startActivity(intent);
    }

    public void youTubeClicked(View v) {
        String name = official.getYouTubeId();
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }
}