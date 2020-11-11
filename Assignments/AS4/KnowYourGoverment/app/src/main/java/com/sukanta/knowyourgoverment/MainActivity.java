package com.sukanta.knowyourgoverment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final ArrayList<Official> officialArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //downloadData("Chicago");


        //Make some data - not always needed - just used to fill list
        for (int i = 0; i < 30; i++) {
            Official official = new Official();
            official.setOfficeName("OfficeName"+i);
            official.setOfficialName("OfficialName"+i);
            official.setParty("Party"+1);
            officialArrayList.add(official);
        }

        recyclerView = findViewById(R.id.rvRecycler);

        OfficialsAdapter officialsAdapter = new OfficialsAdapter(officialArrayList, this);
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
                    Toast.makeText(getApplicationContext(),"Search Clicked",Toast.LENGTH_SHORT).show();
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
        Log.d(TAG, "receiveDataDownloaderData: officialList size" + officialList.size());
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
}