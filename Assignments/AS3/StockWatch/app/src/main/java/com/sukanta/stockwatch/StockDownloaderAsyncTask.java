package com.sukanta.stockwatch;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class StockDownloaderAsyncTask implements Runnable {

    private static final String DATAURL_1 = "https://cloud.iexapis.com/stable/stock/";
    private static final String DATAURL_2 = "/quote?token=pk_7919d4ba3718489f8d84fcb05e45e807";

    private MainActivity mainActivity;
    private String stockSymbol;

    public StockDownloaderAsyncTask(MainActivity mainActivity, String stockSymbol) {
        this.mainActivity = mainActivity;
        this.stockSymbol = stockSymbol;
    }

    @Override
    public void run() {
        String jsonString = getData();
        Stock stock = jsonToMap(jsonString);
        callbackMainActivity(stock);
    }

    private void callbackMainActivity(final Stock stock) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO send data to main activity
                mainActivity.receiveStockDownloaderData(stock);
            }
        });
    }

    private String getData() {
        String API_URL = DATAURL_1 + stockSymbol + DATAURL_2;
        Uri uri = Uri.parse(API_URL);
        String url_string = uri.toString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(url_string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            String line;
            while((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private Stock jsonToMap(String s) {
        Stock temp_stock = new Stock();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String symbol = jsonObject.getString("symbol");
            String name = jsonObject.getString("companyName");
            String price_str = jsonObject.getString("latestPrice");
            String priceChange_str = jsonObject.getString("change");
            String changePercentage_str = jsonObject.getString("changePercent");
            double price = 0.0;
            double priceChange = 0.0;
            double changePercentage = 0.0;
            try {
                price = Double.parseDouble(price_str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                priceChange = Double.parseDouble(priceChange_str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                changePercentage = Double.parseDouble(changePercentage_str);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


            temp_stock.setCompanyName(name);
            temp_stock.setStockSymbol(symbol);
            temp_stock.setPrice(price);
            temp_stock.setPriceChange(priceChange);
            temp_stock.setChangePercentage(changePercentage);
            return temp_stock;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
