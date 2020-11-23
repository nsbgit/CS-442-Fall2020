package com.sukanta.stockwatch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static android.text.InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final String TAG = "MainActivity";
    private final List<Stock> stockList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StocksAdapter stocksAdapter;
    MySQLDatabase sqlDatabase;
    HashMap<String, String> symbolMap;

    private SwipeRefreshLayout refreshLayout;
    private int ctr = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeScreenItems();

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
    protected void onPostResume() {
        Log.d(TAG, "onPostResume: ");
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        stocksAdapter.notifyDataSetChanged();
    }

    private void initializeScreenItems() {
        Log.d(TAG, "initializeScreenItems: ");
        try {
            Log.d(TAG, "InitializeScreenItems: ");
            recyclerView = findViewById(R.id.rvRecycler);
            refreshLayout = findViewById(R.id.swipelayout);
            // Data to recyclerview adapter
            stocksAdapter = new StocksAdapter(stockList, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(stocksAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            // TODO color scheme
            refreshLayout.setProgressViewOffset(true, 0, 200);
            refreshLayout.setColorSchemeColors(
                    getResources().getColor(android.R.color.holo_blue_bright),
                    getResources().getColor(android.R.color.holo_green_light),
                    getResources().getColor(android.R.color.holo_orange_light),
                    getResources().getColor(android.R.color.holo_red_light)
            );

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.d(TAG, "onRefresh: ");
                    if (!isNetworkAvailable()) {
                        refreshLayout.setRefreshing(false);
                        errorDialog("Stocks cannot be updated without a Network Connection");
                    } else {
                        refreshData();
                    }
                }
            });

            Log.d(TAG, "initializeScreenItems: DB stared");
            sqlDatabase = new MySQLDatabase(this);
            downloadStockNames();
            ArrayList<Stock> tempList = sqlDatabase.loadStocks();
            if (!isNetworkAvailable()) {
                stockList.addAll(tempList);
                Collections.sort(stockList, new Comparator<Stock>() {
                    @Override
                    public int compare(Stock o1, Stock o2) {
                        return o1.getStockSymbol().compareTo(o2.getStockSymbol());
                    }
                });
                stocksAdapter.notifyDataSetChanged();
            } else {
                for (int i = 0; i < tempList.size(); i++) {
                    String symbol = tempList.get(i).getStockSymbol();
                    downloadStockDetails(symbol);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "InitializeScreenItems: ", e);
        }
    }

    private void refreshData() {
        refreshLayout.setRefreshing(false); // todo
        ArrayList<Stock> tempList = sqlDatabase.loadStocks();
        for (int i = 0; i < tempList.size(); i++) {
            String symbol = tempList.get(i).getStockSymbol();
            downloadStockDetails(symbol);
        }
        Log.d(TAG, "refreshData: Data Refreshed");
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
                    Log.d(TAG, "onOptionsItemSelected: started");
//                    Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();
                    if(!isNetworkAvailable()){
                        errorDialog(null);
                    }
                    else {
                        openDialogForStock();
                    }
                    Log.d(TAG, "onOptionsItemSelected: end");
                    return true;
                default:
                    Log.d(TAG, "onOptionsItemSelected: Default");
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return super.onOptionsItemSelected(item);
        }
    }

    private void openDialogForStock() {
        if(symbolMap == null){
            downloadStockNames();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stock Selection");
        builder.setMessage("Please Enter A Stock Symbol");
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
                    ArrayList<String> tempList = searchStock(editText.getText().toString());
                    if(!tempList.isEmpty()){
                        ArrayList<String> stockOptions = new ArrayList<>(tempList);
                        if(stockOptions.size() == 1){
                            if(isDuplicateStock(stockOptions.get(0))){
                                duplicateItemDialog(editText.getText().toString());
                            }
                            else {
                                saveStock(stockOptions.get(0));
                            }
                        }
                        else {
                            multipleStocksExistDialog(editText.getText().toString(),stockOptions,stockOptions.size());
                        }
                    }
                    else {
                        notSuchDataDialog(editText.getText().toString());
                    }
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

    private void performSwipeRefreshStuffHere() {
        // TODO swipe refresh work here
        if (!isNetworkAvailable()) {
            refreshLayout.setRefreshing(false);
            errorDialog(null);
        } else {
            refreshData();
        }
    }

    private void doneWithSwipeRefresh(long i) {
        // TODO stuff here
        refreshLayout.setRefreshing(false); // This stops the busy-circle
    }

    private void downloadStockNames() {
        Log.d(TAG, "downloadStockNames: ");
        new Thread(
                new NameDownloaderAsyncTask(this)
        ).start();
    }

    public void receiveNameDownloaderData(HashMap<String, String> hashMap) {
        if (hashMap != null && !hashMap.isEmpty()) {
            Log.d(TAG, "receiveNameDownloaderData: nameDownloader completed. Count " + hashMap.size());
            this.symbolMap = hashMap;
        }
        else
            Log.d(TAG, "receiveNameDownloaderData: Empty hash map");
    }

    private void downloadStockDetails(String stockSymbol) {
        Log.d(TAG, "downloadStockDetails: Symbol = " + stockSymbol);
        if (isNetworkAvailable()) {
            new Thread(
                    new StockDownloaderAsyncTask(this, stockSymbol)
            ).start();
        }
        else {
            String msg = "No network available";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, msg);
        }
    }

    public void receiveStockDownloaderData(Stock stock) {
        if (stock != null) {
            Log.d(TAG, "receiveStockDownloaderData: In Stock !=null condition");
            int index = stockList.indexOf(stock);
            Log.d(TAG, "receiveStockDownloaderData: The index "+ index);
            if (index > -1) {
                Log.d(TAG, "receiveStockDownloaderData: In Stock index");
                stockList.remove(index);
            }
            stockList.add(stock);
            Collections.sort(stockList, new Comparator<Stock>() {
                @Override
                public int compare(Stock o1, Stock o2) {
                    return o1.getStockSymbol().compareTo(o2.getStockSymbol());
                }
            });
            stocksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        int i = recyclerView.getChildLayoutPosition(view);
        String marketPlaceURL = "http://www.marketwatch.com/investing/stock/" + stockList.get(i).getStockSymbol();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(marketPlaceURL));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        sqlDatabase.shutDown();
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick: ");
        final int id = recyclerView.getChildLayoutPosition(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Stock");
        builder.setIcon(R.drawable.baseline_delete_forever_black_18);
        builder.setMessage("Delete Stock Symbol "+((TextView)view.findViewById(R.id.companysymbolTextView)).getText().toString()+" ?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ///TODO remove stock and notify all
                sqlDatabase.deleteStock(stockList.get(id).getStockSymbol());
                stockList.remove(id);
                stocksAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    private void errorDialog(String msg) {
        Log.d(TAG, "errorDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(msg == null)
            msg = "Stocks cannot be added without a Network Connection";
        builder.setTitle("Network Connection Error");
        builder.setMessage(msg);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private ArrayList<String> searchStock(String s) {
        Log.d(TAG, "searchStock: Search String = " + s);
        ArrayList<String> stockOption = new ArrayList<>();
        if(symbolMap != null && !symbolMap.isEmpty()) {
            for (String symbol : symbolMap.keySet()) {
                String name = symbolMap.get(symbol);
                if (symbol.toUpperCase().contains(s.toUpperCase())) {
                    stockOption.add(symbol + " - " + name);
                } else if (name.toUpperCase().contains(s.toUpperCase())) {
                    stockOption.add(symbol + " - " + name);
                }

            }
        }
        return stockOption;
    }

    private void notSuchDataDialog(String stockSymbol) {
        Log.d(TAG, "notSuchDataDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Symbol not found: " + stockSymbol);
        builder.setMessage("Data for symbol "+ stockSymbol +" not found, please enter valid inputs");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isDuplicateStock(String s) {
        Log.d(TAG, "isDuplicateStock: ");
        String symbol = s.split("-")[0].trim();
        Stock temp = new Stock();
        temp.setStockSymbol(symbol);
        return stockList.contains(temp);
    }

    private void duplicateItemDialog(String s) {
        Log.d(TAG, "duplicateItemDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.baseline_warning_black_18);
        builder.setTitle("Duplicate Stock");
        builder.setMessage("Stock symbol " + s.toUpperCase() + " is already displayed.");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveStock(String s) {
        Log.d(TAG, "saveStock: data = " + s);
        String symbol = s.split("-")[0].trim();
        downloadStockDetails(symbol);
        Stock temp_stock = new Stock();
        temp_stock.setStockSymbol(symbol);
        temp_stock.setCompanyName(symbolMap.get(symbol));
        sqlDatabase.addStock(temp_stock);

    }

    private void multipleStocksExistDialog(final String s, ArrayList<String> stockOptions, int size) {
        Log.d(TAG, "multipleStocksExistDialog: ");
        final String[] strings = new String[size];
        for(int i=0;i<strings.length;i++){
            strings[i]=stockOptions.get(i);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make a Selection");
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isDuplicateStock(strings[which])){
                    duplicateItemDialog(s);
                }
                else {
                    saveStock(strings[which]);
                }
            }
        });
        builder.setNegativeButton("Never mind", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}