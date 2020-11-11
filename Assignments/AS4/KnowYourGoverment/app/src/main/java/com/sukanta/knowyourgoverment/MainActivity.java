package com.sukanta.knowyourgoverment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.text.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final ArrayList<Official> officialArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OfficialsAdapter officialsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadData("Chicago");

        recyclerView = findViewById(R.id.rvRecycler);

        officialsAdapter = new OfficialsAdapter(officialArrayList, this);
        recyclerView.setAdapter(officialsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                    Toast.makeText(getApplicationContext(),"Info Clicked", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getApplicationContext(),"On Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getApplicationContext(),"On-long Clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            Toast.makeText(this, "Cannot access ConnectivityManager", Toast.LENGTH_SHORT).show();
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