package com.christopherhield.locationexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

// Don't forget, you need manifest permission:
//  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>


// To properly set your location in emulator:
// Open maps and click on go to current location
//

public class MainActivity extends AppCompatActivity {

    private static int MY_LOCATION_REQUEST_CODE_ID = 111;
    private LocationManager locationManager;
    private Criteria criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        // use gps for location
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        // use network for location
        //criteria.setPowerRequirement(Criteria.POWER_LOW);
        //criteria.setAccuracy(Criteria.ACCURACY_MEDIUM);

        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    MY_LOCATION_REQUEST_CODE_ID);
        } else {
            setLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull
            String[] permissions, @NonNull
            int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_LOCATION_REQUEST_CODE_ID) {
            if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PERMISSION_GRANTED) {
                setLocation();
                return;
            }
        }
        ((TextView) findViewById(R.id.locText)).setText(R.string.no_perm);

    }

    @SuppressLint("MissingPermission")
    private void setLocation() {

        String bestProvider = locationManager.getBestProvider(criteria, true);
        ((TextView) findViewById(R.id.bestText)).setText(bestProvider);

        Location currentLocation = null;
        if (bestProvider != null) {
            currentLocation = locationManager.getLastKnownLocation(bestProvider);
        }
        if (currentLocation != null) {
            ((TextView) findViewById(R.id.locText)).setText(
                    String.format(Locale.getDefault(),
                            "%.4f, %.4f", currentLocation.getLatitude(), currentLocation.getLongitude()));
        } else {
            ((TextView) findViewById(R.id.locText)).setText(R.string.no_locs);
        }


    }

    public void recheckLocation(View v) {
        Toast.makeText(this, "Rechecking Location", Toast.LENGTH_SHORT).show();
        setLocation();
    }
}
