package com.sukanta.knowyourgoverment;

import android.net.Uri;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataDownloader implements Runnable {

    private static final String TAG = "DataDownloader";
    private MainActivity mainActivity;
    private String address;
    private static final String URL = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyB0h2wQIi4sBHBFaeTYSqKVsKEv6K5qDJs&address=";
    private Office office = new Office();

    public DataDownloader(MainActivity mainActivity, String address) {
        this.mainActivity = mainActivity;
        this.address = address;
    }

    @Override
    public void run() {
        String jsonString = getData();
        office = getOffices(jsonString);
        List<Official> officialList = getOfficials(jsonString);
        callbackMainActivity(office, officialList);
    }

    private void callbackMainActivity(Office office, List<Official> officialList) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.receiveDataDownloaderData(office, officialList);
            }
        });
    }

    private String getData() {
        String API_URL = URL + address;
        Uri uri = Uri.parse(API_URL);
        String url_string = uri.toString();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            java.net.URL url = new URL(url_string);
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

    private Office getOffices(String s) {

        Office office = new Office();
        try {
            JSONObject jsonObject = new JSONObject(s);
            String normalizedInputJson = jsonObject.getString("normalizedInput");
            JSONObject normalizedInputJsonObject = new JSONObject(normalizedInputJson);
            String city = normalizedInputJsonObject.getString("city");
            office.city = city;
            String state = normalizedInputJsonObject.getString("state");
            office.state = state;
            String zip = normalizedInputJsonObject.getString("zip");
            office.zip = zip;


            String offices = jsonObject.getString("offices");
            JSONArray jsonArray = new JSONArray(offices);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonOfficeObject = (JSONObject) jsonArray.get(i);
                String officeName = jsonOfficeObject.getString("name");
                String officialIndicesJsonString = jsonOfficeObject.getString("officialIndices");
                JSONArray officialIndicesJsonArray = new JSONArray(officialIndicesJsonString);
                for (int j = 0; j < officialIndicesJsonArray.length(); j++) {
                    int index = officialIndicesJsonArray.getInt(j);
                    office.officesHashMap.put(index,officeName);
                }
            }

            return office;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Official> getOfficials(String s) {

        ArrayList<Official> officialList = new ArrayList<Official>();
        try {
            JSONObject jsonObject = new JSONObject(s);

            String officials = jsonObject.getString("officials");
            JSONArray jsonArray = new JSONArray(officials);
            for (int i = 0; i< jsonArray.length(); i++) {
                Official official = new Official();
                JSONObject jsonOfficialObject = (JSONObject) jsonArray.get(i);
                String officialName = "";
                String officialAddressJsonString = "";
                String party = "";
                String phones = "";
                String urls = "";
                String emails = "";
                String photoUrl = "";
                String channels = "";


                if (jsonOfficialObject.has("name")) {
                    officialName = jsonOfficialObject.getString("name");
                    official.setOfficialName(officialName);
                }

                if (jsonOfficialObject.has("address")) {
                    officialAddressJsonString = jsonOfficialObject.getString("address");

                    JSONArray officialAddressJsonArray = new JSONArray(officialAddressJsonString);
                    for (int j = 0; j < officialAddressJsonArray.length(); j++) {
                        JSONObject officialAddressJsonObject = (JSONObject) officialAddressJsonArray.get(j);
                        String line1 = "";
                        String line2 = "";
                        String line3 = "";
                        String city1 = "";
                        String state1 = "";
                        String zip1 = "";
                        if (officialAddressJsonObject.has("line1"))
                            line1 = officialAddressJsonObject.getString("line1");
                        if (officialAddressJsonObject.has("line2"))
                            line2 = officialAddressJsonObject.getString("line2");
                        if (officialAddressJsonObject.has("line3"))
                            line3 = officialAddressJsonObject.getString("line3");
                        if (officialAddressJsonObject.has("city"))
                            city1 = officialAddressJsonObject.getString("city");
                        if (officialAddressJsonObject.has("state"))
                            state1 = officialAddressJsonObject.getString("state");
                        if (officialAddressJsonObject.has("zip"))
                            zip1 = officialAddressJsonObject.getString("zip");

                        String addressLines = line1 + line2 + line3;
                        official.setOfficialAddress(addressLines);
                        official.setOfficialCity(city1);
                        official.setOfficialState(state1);
                        official.setOfficialZip(zip1);
                    }
                }

                if (jsonOfficialObject.has("party")) {
                    party = jsonOfficialObject.getString("party");
                }
                else {
                    party = "Unknown";
                }
                official.setParty(party);

                if (jsonOfficialObject.has("phones")) {
                    String phonesJsonString = jsonOfficialObject.getString("phones");
                    JSONArray phonesJsonArray = new JSONArray(phonesJsonString);
                    if (phonesJsonArray.length() > 0) {
                        phones = phonesJsonArray.getString(0);
                        official.setPhone(phones);
                    }
                }

                if (jsonOfficialObject.has("urls")) {
                    String urlsJsonString = jsonOfficialObject.getString("urls");
                    JSONArray urlsJsonArray = new JSONArray(urlsJsonString);
                    if (urlsJsonArray.length() > 0) {
                        urls = urlsJsonArray.getString(0);
                        official.setUrl(urls);
                    }
                }

                if (jsonOfficialObject.has("emails")) {
                    String emailsJsonString = jsonOfficialObject.getString("emails");
                    JSONArray emailsJsonArray = new JSONArray(emailsJsonString);
                    if (emailsJsonArray.length() > 0) {
                        emails = emailsJsonArray.getString(0);
                        official.setEmail(emails);
                    }
                }

                if (jsonOfficialObject.has("photoUrl")) {
                    photoUrl = jsonOfficialObject.getString("photoUrl");
                    official.setPhotoUrl(photoUrl);
                }

                if (jsonOfficialObject.has("channels")) {
                    String channelsJsonString = jsonOfficialObject.getString("channels");
                    JSONArray channelsJsonArray = new JSONArray(channelsJsonString);

                    for (int j = 0; j < channelsJsonArray.length(); j++) {
                        JSONObject officialAddressJsonObject = (JSONObject) channelsJsonArray.get(j);
                        String type = "";
                        String id = "";
                        String facebookId = "";
                        String twitterId = "";
                        String youTubeId = "";

                        if (officialAddressJsonObject.has("type"))
                            type = officialAddressJsonObject.getString("type");
                        if (officialAddressJsonObject.has("id"))
                            id = officialAddressJsonObject.getString("id");

                        if (type.equalsIgnoreCase("Facebook")) {
                            facebookId = id;
                            official.setFacebookId(facebookId);
                        }
                        else if (type.equalsIgnoreCase("Twitter")) {
                            twitterId = id;
                            official.setTwitterId(twitterId);
                        }
                        else if (type.equalsIgnoreCase("YouTube")) {
                            youTubeId = id;
                            official.setYouTubeId(youTubeId);
                        }
                    }
                }

                official.setOfficeName(searchOffice(i));
                officialList.add(official);
            }

            return officialList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String searchOffice(int index) {
        String officeName = "";
        Log.d(TAG, "searchStock: Search index = " + index);
        if (office != null) {
            if (office.officesHashMap != null && !office.officesHashMap.isEmpty()) {
                officeName = office.officesHashMap.getOrDefault(index, "");
            }
        }

        return officeName;
    }
}
