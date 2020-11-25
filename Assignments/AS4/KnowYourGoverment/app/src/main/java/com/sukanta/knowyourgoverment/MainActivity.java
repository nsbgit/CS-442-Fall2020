package com.sukanta.knowyourgoverment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.text.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final ArrayList<Official> officialArrayList = new ArrayList<>();
    private ConstraintLayout constraintLayout;
    private RecyclerView recyclerView;
    private OfficialsAdapter officialsAdapter;
    private TextView tvLocation;
    private static int MY_LOCATION_REQUEST_CODE_ID = 111;
    private LocationManager locationManager;
    private Criteria criteria;
    private String latLong = "";
    private String zipCode = "";
    private static final String OFFICIAL_KEY = "OFFICIAL";
    private static final String LOCATION_KEY = "LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        tvLocation = findViewById(R.id.tvLocation);
        recyclerView = findViewById(R.id.rvRecycler);
        officialsAdapter = new OfficialsAdapter(officialArrayList, this);
        recyclerView.setAdapter(officialsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initializeLocationManager();
        //latLong ="41.8349, -87.6270";// TODO delete IITC
//        latLong = "";
        zipCode = getZipCodeFromLatLong(latLong);

        if (!zipCode.isEmpty() && isNetworkAvailable()) {
            downloadData(zipCode);
        }
        else {
            errorDialog(null);
        }
    }

    private String getZipCodeFromLatLong(String latLong) {
        String zip = "";
        if (latLong.isEmpty()) {
            Log.d(TAG, "getZipCodeFromLatLong: latlong is empty");
            return zip;
        }
        else if (!latLong.contains(",")) {
            Log.d(TAG, "getZipCodeFromLatLong: Invalid latlong value : " + latLong);
            return zip;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            String[] latLon = latLong.split(",");
            double lat = Double.parseDouble(latLon[0]);
            double lon = Double.parseDouble(latLon[1]);

            List<Address> addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses != null && addresses.size() > 0) {
                String postalCode = addresses.get(0).getPostalCode();
                zip = postalCode == null ? "" : postalCode;
                Log.d(TAG, "onCreate: address form latlong value: " + addresses.get(0));
            }
            else {
                Log.d(TAG, "getZipCodeFromLatLong: invalid address");
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return zip;
    }

    private void initializeLocationManager() {
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

        Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    private void setLocation() {

        String bestProvider = locationManager.getBestProvider(criteria, true);
//        ((TextView) findViewById(R.id.bestText)).setText(bestProvider);

        Location currentLocation = null;
        if (bestProvider != null) {
            currentLocation = locationManager.getLastKnownLocation(bestProvider);
        }
        if (currentLocation != null) {
            latLong = String.format(Locale.getDefault(),
                    "%.4f, %.4f", currentLocation.getLatitude(), currentLocation.getLongitude());
        } else {
            String latLong = "No Location";
        }


    }

    public void recheckLocation(View v) {
        Toast.makeText(this, "Rechecking Location", Toast.LENGTH_SHORT).show();
        setLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            Log.d(TAG, "onOptionsItemSelected: ");
            switch (item.getItemId()) {
                case R.id.mInfo:
                    goToAboutActivity();
                    return true;
                case R.id.mSearch:
                    Log.d(TAG, "onOptionsItemSelected: started");
                    if(!isNetworkAvailable()){
                        errorDialog(null);
                    }
                    else {
                        openDialogForSearch();
                    }
                    Log.d(TAG, "onOptionsItemSelected: end");
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e(TAG, "onOptionsItemSelected: ", e);
            return super.onOptionsItemSelected(item);
        }
    }

    private void goToAboutActivity() {
        try {
            Log.d(TAG, "goToAboutActivity: ");
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "goToAboutActivity: ", e);
        }
    }

    public void goToOfficialActivity(int position) {
        try {
            Log.d(TAG, "goToEditActivity: ");
            Official official;
            if (position < 0) {
                official = new Official();
            }
            else {
                official = officialArrayList.get(position);
            }

            Intent intent = new Intent(this, OfficialActivity.class);
            intent.putExtra(OFFICIAL_KEY, official);
//            intent.putExtra(POSITION_KEY, position);

            intent.putExtra(LOCATION_KEY, tvLocation.getText());
//            startActivityForResult(intent, NOTE_REQUEST);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "goToEditActivity: ", e);
        }
    }

    public void downloadData(String address) {
        new Thread(new DataDownloader(this, address)).start();
    }

    public void receiveDataDownloaderData(Office office, List<Official> officialList) {
        if (office == null || officialList == null) {
            officialArrayList.clear();
            officialsAdapter.notifyDataSetChanged();
            Log.d(TAG, "receiveDataDownloaderData: office" + office);
            Log.d(TAG, "receiveDataDownloaderData: officialList" + officialList);
        }
        else {
            Log.d(TAG, "receiveDataDownloaderData: officialList size" + officialList.size());
            String address = office.city + ", " + office.state + " " + office.zip;
            ((TextView) findViewById(R.id.tvLocation)).setText(address);
            officialArrayList.addAll(officialList);
            officialsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            Log.d(TAG, "onClick: ");
            int position = recyclerView.getChildLayoutPosition(v);
            goToOfficialActivity(position);
        } catch (Exception e) {
            Log.e(TAG, "onClick: ", e);
        }
    }

    @Override
    public boolean onLongClick(View v) {
//        Toast.makeText(getApplicationContext(),"On-long Clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "isNetworkAvailable: Cannot access ConnectivityManager");
            return false;
        }

        //noinspection deprecation
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected()) {
            constraintLayout.setBackgroundColor(getResources().getColor(R.color.purple_700, null));
            return true;
        } else {
            return false;
        }
    }

    private void errorDialog(String msg) {
        Log.d(TAG, "errorDialog: ");
        tvLocation.setText("No Data For Location");

        constraintLayout.setBackgroundColor(Color.WHITE);

        officialArrayList.clear();
        officialsAdapter.notifyDataSetChanged();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(msg == null)
            msg = "Data can not be accessed/ loaded without an internet connection.";
        builder.setTitle("No Network Connection");
        builder.setMessage(msg);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openDialogForSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a City, State or a Zip Code:");
//        builder.setMessage("Enter a City, State or a Zip Code:");
        builder.setCancelable(false);
        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT|TYPE_TEXT_FLAG_CAP_CHARACTERS);
        editText.setGravity(Gravity.CENTER_HORIZONTAL);
        editText.setLongClickable(false);
        builder.setView(editText);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!isNetworkAvailable()){
                    errorDialog(null);
                    return;
                }
                else if(editText.getText().toString().isEmpty()) {           //empty search
                    Toast.makeText(MainActivity.this, "Please Enter Valid Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    String address = editText.getText().toString();
                    ((TextView)findViewById(R.id.tvLocation)).setText(address);
                    downloadData(address);

                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(MainActivity.this, "You changed your mind!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}