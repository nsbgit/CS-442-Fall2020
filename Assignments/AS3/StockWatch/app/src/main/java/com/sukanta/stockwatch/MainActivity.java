package com.sukanta.stockwatch;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private final List<Stock> stockList = new ArrayList<>();
    private RecyclerView recyclerView;

    private SwipeRefreshLayout swiper;
    private int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        downloadStockNames();

//        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //TODO do things here
//                performSwipeRefreshStuffHere();
//                //swiper.setRefreshing(false); // This stops the busy-circle
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.miAddStock:
                    Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onOptionsItemSelected(item);
        }
    }

    private void performSwipeRefreshStuffHere() {
        // TODO swipe refresh work here
        // run on ui thread and return value to doneWithSwipeRefresh()
    }

    private void doneWithSwipeRefresh(long i) {
        // TODO stuff here
        //swiper.setRefreshing(false); // This stops the busy-circle
    }

    private void downloadStockNames() {
        new Thread(
                new NameDownloaderAsyncTask(this)
        ).start();
    }

    public void receiveNameDownloaderData(HashMap<String, String> hashMap) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}