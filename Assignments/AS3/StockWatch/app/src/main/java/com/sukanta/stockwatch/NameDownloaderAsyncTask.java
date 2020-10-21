package com.sukanta.stockwatch;

import android.net.Uri;

import org.json.JSONArray;
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

public class NameDownloaderAsyncTask implements Runnable {

    private MainActivity mainActivity;
    private static final String URL = "https://api.iextrading.com/1.0/ref-data/symbols";

    public NameDownloaderAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
        String jsonString = getData();
        HashMap<String,String> hashMap = jsonToMap(jsonString);
        mainActivity.receiveNameDownloaderData(hashMap);
    }

    private String getData() {
        Uri uri = Uri.parse(URL);
        String url_string = uri.toString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(url_string);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line).append("\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();


//        mainActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO send data to main activity
//                mainActivity.receiveNameDownloaderData(null);
//            }
//        });
    }

    private HashMap<String, String> jsonToMap(String s) {
        HashMap<String,String> stringHashMap = new HashMap<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String symbol = jsonObject.getString("symbol");
                String name = jsonObject.getString("name");
                stringHashMap.put(symbol,name);
            }
            return stringHashMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
