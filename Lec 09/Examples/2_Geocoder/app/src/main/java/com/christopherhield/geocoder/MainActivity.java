package com.christopherhield.geocoder;

import android.location.Address;
import android.location.Geocoder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Loc 1 = 41.8788764,-87.6381036
    // Loc 2 = 41.8675726,-87.6162267
    // Loc 3 = 51.5081333,-0.1303418
    // Loc 4 = 41.8902102,12.4900422

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView) findViewById(R.id.textView)).setMovementMethod(new ScrollingMovementMethod());
    }

    public void doLatLon(View v) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses;

            String loc = ((EditText) findViewById(R.id.editText)).getText().toString();
            if (loc.trim().isEmpty()) {
                Toast.makeText(this, "Enter Lat & Lon coordinates first!", Toast.LENGTH_LONG).show();
                return;
            }
            String[] latLon = loc.split(",");
            double lat = Double.parseDouble(latLon[0]);
            double lon = Double.parseDouble(latLon[1]);

            addresses = geocoder.getFromLocation(lat, lon, 10);

            displayAddresses(addresses);
            Log.d(TAG, "onCreate: " + addresses.get(0));
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void doLocationName(View v) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses;

            String loc = ((EditText) findViewById(R.id.editText)).getText().toString();
            addresses = geocoder.getFromLocationName(loc, 10);
            displayAddresses(addresses);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void displayAddresses(List<Address> addresses) {
        StringBuilder sb = new StringBuilder();
        if (addresses.size() == 0) {
            ((TextView) findViewById(R.id.textView)).setText(R.string.nothing_found);
            return;
        }

        for (Address ad : addresses) {

            String a = String.format("%s %s %s %s %s %s",
                    (ad.getSubThoroughfare() == null ? "" : ad.getSubThoroughfare()),
                    (ad.getThoroughfare() == null ? "" : ad.getThoroughfare()),
                    (ad.getLocality() == null ? "" : ad.getLocality()),
                    (ad.getAdminArea() == null ? "" : ad.getAdminArea()),
                    (ad.getPostalCode() == null ? "" : ad.getPostalCode()),
                    (ad.getCountryName() == null ? "" : ad.getCountryName()));

            if (!a.trim().isEmpty())
                sb.append("* ").append(a.trim());

            sb.append("\n");
        }
        ((TextView) findViewById(R.id.textView)).setText(sb.toString());
    }
}
/*41.9484424, -87.6575214 Wrigley
  41.8788129, -87.6381175 Sears
  38.8892728, -77.0523647 Lincoln
  48.8606146, 2.3354553 Louvre
*/